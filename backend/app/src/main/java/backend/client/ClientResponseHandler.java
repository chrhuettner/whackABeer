package backend.client;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import backend.client.ClientResponses.ActionPing;
import backend.client.ClientResponses.ClientActionInterface;
import shared.Constants;


public class ClientResponseHandler extends Handler {


    private AppCompatActivity uiActivity;
    private static final HashMap<String, ArrayList<ClientActionInterface>> actionMap = new HashMap<>();

    private static ClientConnection client;


    static {


        ArrayList<ClientActionInterface> pingActions = new ArrayList<>();
        pingActions.add(new ActionPing());
        actionMap.put(Constants.PING, pingActions);
    }

    public static void setClient(ClientConnection connection) {
        client = connection;
    }
    public static void sendMessageToServer(String activity, String prefix, String args) {
        client.sendMessage(activity + ":" + prefix + " " + args);
    }

    public ClientResponseHandler(AppCompatActivity uiActivity) {
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

    public AppCompatActivity getUiActivity() {
        return uiActivity;
    }

    public void replaceActivity(AppCompatActivity activity) {
        synchronized (uiActivity) {
            this.uiActivity = activity;
        }
    }

}
