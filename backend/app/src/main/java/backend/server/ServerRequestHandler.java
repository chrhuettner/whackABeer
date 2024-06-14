package backend.server;


import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import backend.server.ServerRequests.RequestBeer;
import backend.server.ServerRequests.RequestConfig;
import backend.server.ServerRequests.RequestGameStart;
import shared.Config;
import shared.Constants;
import backend.server.ServerRequests.RequestPing;
import backend.server.ServerRequests.ServerRequestInterface;


public class ServerRequestHandler {
    public static final HashMap<String, ArrayList<ServerRequestInterface>> actionMap = new HashMap<>();

    private static ServerNetwork server;
    private static Timer timer = new Timer();
    private static Random random = new Random();
    private static String currentBeer;

    private static boolean beer_crushable;

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

        ArrayList<ServerRequestInterface> beerActions = new ArrayList<>();
        beerActions.add(new RequestBeer());
        actionMap.put(Constants.CLICKED_BEER, beerActions);

        ArrayList<ServerRequestInterface> configActions = new ArrayList<>();
        configActions.add(new RequestConfig());
        actionMap.put(Constants.CONFIG, configActions);
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

    public static void startRandomBeerSelection() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                selectRandomBeer();
            }
        }, 5000, ((1 + random.nextInt(5)) * 1000));
    }

    private static void selectRandomBeer() {
        int beerNumber = 1 + random.nextInt(12);
        currentBeer = "beer" + beerNumber;
        Log.i("Game", "Selected beer: " + currentBeer);
        beer_crushable = true;
        server.broadcast(Constants.MAIN_ACTIVITY_TYPE, Constants.NEW_BEER, new String[]{currentBeer});
    }

    public static String getCurrentBeer() {
        return currentBeer;
    }

    public static boolean isBeer_crushable() {
        return beer_crushable;
    }

    public static void setBeer_crushable(boolean beer_crushable) {
        ServerRequestHandler.beer_crushable = beer_crushable;
    }

    public static void setServer(ServerNetwork server) {
        ServerRequestHandler.server = server;
    }

}
