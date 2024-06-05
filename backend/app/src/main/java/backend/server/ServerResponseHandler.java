package backend.server;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import backend.server.ServerResponse.RespondToPing;
import backend.server.ServerResponse.ServerResponseInterface;


public class ServerResponseHandler extends Handler {


    private AppCompatActivity uiActivity;
    private static final HashMap<String, ArrayList<ServerResponseInterface>> actionMap = new HashMap<>();


    static {
        ArrayList<ServerResponseInterface> pingActions = new ArrayList<>();
        pingActions.add(new RespondToPing());
        actionMap.put(Constants.PING, pingActions);
    }

    public ServerResponseHandler(AppCompatActivity uiActivity) {
        this.uiActivity = uiActivity;

    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        String message = msg.getData().get("payload").toString();

        String[] actionSplit = message.split("[: ]");
        if (actionMap.containsKey(actionSplit[0])) {
            Log.i("INFO", "TRIGGERED " + actionSplit[0]);
            synchronized (uiActivity) {
                for (ServerResponseInterface clientAction : actionMap.get(actionSplit[0])) {
                    clientAction.execute(uiActivity, message);
                }
            }
            return;
        }
        Log.e("ERROR", actionSplit[0] + " NOT FOUND");
    }

    public AppCompatActivity getUiActivity() {
        return uiActivity;
    }

    public void replaceActivity(AppCompatActivity activity) {
        synchronized (uiActivity) {
            this.uiActivity = activity;
        }
    }

}
