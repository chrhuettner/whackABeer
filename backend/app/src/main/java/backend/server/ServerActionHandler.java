package backend.server;


import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import shared.Constants;
import backend.server.ServerRequests.RequestPing;
import backend.server.ServerRequests.ServerRequestInterface;


public class ServerActionHandler {
    public static final HashMap<String, ArrayList<ServerRequestInterface>> actionMap = new HashMap<>();

    private static ServerNetwork server;


    private ServerActionHandler() {
        // no instantiation of class
    }

    static {
        ArrayList<ServerRequestInterface> pingActions = new ArrayList<>();
        pingActions.add(new RequestPing());
        actionMap.put(Constants.PING, pingActions);
    }

    public static void triggerAction(String name, Object parameters) {
        if (server == null) {
            Log.e(Constants.LOG_ERROR, "Server was not initialized!");
            return;
        }
        if (actionMap.containsKey(name)) {
            for (ServerRequestInterface serverAction : actionMap.get(name)) {
                serverAction.execute(server, parameters);
            }
            return;
        }
        Log.e(Constants.LOG_ERROR, name + " not found in action map!");
        
    }

    public static void setServer(ServerNetwork server) {
        ServerActionHandler.server = server;
    }

}
