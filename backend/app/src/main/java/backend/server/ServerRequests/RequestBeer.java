package backend.server.ServerRequests;

import android.util.Log;

import backend.server.ServerNetwork;
import shared.Constants;

public class RequestBeer implements ServerRequestInterface {
    @Override
    public void execute(ServerNetwork server, Object parameters) {
        String[] params = parameters.toString().split(":");
        int id = Integer.valueOf(params[0]);
        Log.i("Comm", ""+id+"Beer: "+params[1]);
        server.sendToClient(id, Constants.MAIN_ACTIVITY_TYPE, Constants.CLICKED_BEER, new String[]{""+params[1]});
    }

}
