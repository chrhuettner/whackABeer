package backend.server.ServerRequests;

import android.util.Log;

import backend.server.ServerNetwork;
import shared.Constants;

public class RequestGameStart implements ServerRequestInterface {
    @Override
    public void execute(ServerNetwork server, Object parameters) {
        Log.i("Comm", "Sending to Client with " + Constants.GAME_START);
        server.sendToClient(1, Constants.MAIN_ACTIVITY_TYPE, Constants.GAME_START, new String[]{""+parameters});
    }

}
