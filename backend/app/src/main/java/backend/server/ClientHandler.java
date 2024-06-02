package backend.server;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import backend.server.ClientAction.ActionPing;
import backend.server.ClientAction.ClientActionInterface;


public class ClientHandler extends Handler {


    private AppCompatActivity uiActivity;
    private static final HashMap<String, ArrayList<ClientActionInterface>> actionMap = new HashMap<>();

    static {


        ArrayList<ClientActionInterface> pingActions = new ArrayList<>();
        pingActions.add(new ActionPing());
        actionMap.put(Constants.PING, pingActions);
    }


    public static void sendMessageToServer(String activity, String prefix, String args) {
       //TODO
    }

    public ClientHandler(AppCompatActivity uiActivity) {
        this.uiActivity = uiActivity;

    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        String message = msg.getData().get("payload").toString();

        String[] actionSplit = message.split("[: ]");
        if (actionMap.containsKey(actionSplit[0])) {
            Log.i("INFO", "TRIGGERED " + actionSplit[0]);
            synchronized (uiActivity) {
                for (ClientActionInterface clientAction : actionMap.get(actionSplit[0])) {
                    clientAction.execute(uiActivity, message);
                }
            }
            return;
        }
        Log.e("ERROR", actionSplit[0] + " NOT FOUND");
    }

}