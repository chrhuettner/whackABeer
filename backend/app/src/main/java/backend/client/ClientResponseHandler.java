package backend.client;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import backend.client.ClientResponses.RespondToBeer;
import backend.client.ClientResponses.RespondToClick;
import backend.client.ClientResponses.RespondToConfig;
import backend.client.ClientResponses.RespondToGameStart;
import backend.client.ClientResponses.RespondToPing;
import backend.client.ClientResponses.ClientResponseInterface;
import backend.network.NetworkConnection;
import shared.Constants;


public class ClientResponseHandler extends Handler {


    private AppCompatActivity uiActivity;
    private static final HashMap<String, ArrayList<ClientResponseInterface>> actionMap = new HashMap<>();

    public static NetworkConnection client;


    static {
        ArrayList<ClientResponseInterface> pingActions = new ArrayList<>();
        pingActions.add(new RespondToPing());
        actionMap.put(Constants.PING, pingActions);

        ArrayList<ClientResponseInterface> gameStartActions = new ArrayList<>();
        gameStartActions.add(new RespondToGameStart());
        actionMap.put(Constants.GAME_START, gameStartActions);

        ArrayList<ClientResponseInterface> configActions = new ArrayList<>();
        configActions.add(new RespondToConfig());
        actionMap.put(Constants.CONFIG, configActions);

        ArrayList<ClientResponseInterface> beerActions = new ArrayList<>();
        beerActions.add(new RespondToBeer());
        actionMap.put(Constants.NEW_BEER, beerActions);

        ArrayList<ClientResponseInterface> clickActions = new ArrayList<>();
        clickActions.add(new RespondToClick());
        actionMap.put(Constants.CLICKED_BEER, clickActions);
    }

    public static void setClient(NetworkConnection connection) {
        client = connection;
    }
    public static void sendMessageToServer(String activity, String prefix, String args) {
        client.send(activity + ":" + prefix + " " + args);
    }

    public static void sendMessageToServer(String activity, String prefix, Object[] args) {
        StringBuilder message = new StringBuilder();
        for (Object element : args) {
            message.append(element);
            if (Arrays.asList(args).indexOf(element) != args.length - 1) {
                message.append(";");
            }
        }
        sendMessageToServer(activity, prefix, message.toString());
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
                for (ClientResponseInterface clientAction : actionMap.get(actionSplit[0])) {
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
