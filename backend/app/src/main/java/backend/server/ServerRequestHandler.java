package backend.server;


import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import backend.server.ServerRequests.RequestGameStart;
import shared.Constants;
import backend.server.ServerRequests.RequestPing;
import backend.server.ServerRequests.ServerRequestInterface;


public class ServerRequestHandler {
    public static final HashMap<String, ArrayList<ServerRequestInterface>> actionMap = new HashMap<>();

    private static ServerNetwork server;


    private ServerRequestHandler() {
        // no instantiation of class
    }

    static {
        ArrayList<ServerRequestInterface> pingActions = new ArrayList<>();
        pingActions.add(new RequestPing());
        actionMap.put(Constants.PING, pingActions);

        ArrayList<ServerRequestInterface> gameStartActions = new ArrayList<>();
        gameStartActions.add(new RequestGameStart());
        actionMap.put(Constants.GAME_START, gameStartActions);
    }

    public static void triggerAction(String name, Object parameters) {
        if (server == null) {
            Log.e(Constants.LOG_ERROR, "Server was not initialized!");
            return;
        }
        Log.i("Comm", "Service is registered? "+String.valueOf(actionMap.containsKey(name)));
        if (actionMap.containsKey(name)) {
            for (ServerRequestInterface serverAction : actionMap.get(name)) {
                Log.i("Comm", "Service gets executed: "+name);
                serverAction.execute(server, parameters);
            }
            return;
        }
        Log.e(Constants.LOG_ERROR, name + " not found in action map!");
        
    }

    public static void setServer(ServerNetwork server) {
        ServerRequestHandler.server = server;
    }

}
