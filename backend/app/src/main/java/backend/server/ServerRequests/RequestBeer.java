package backend.server.ServerRequests;

import static backend.client.ClientResponseHandler.client;
import static backend.server.ServerRequestHandler.getCurrentBeer;
import static backend.server.ServerRequestHandler.isBeer_crushable;
import static backend.server.ServerRequestHandler.setBeer_crushable;

import android.util.Log;

import backend.server.ServerNetwork;
import shared.Constants;

public class RequestBeer implements ServerRequestInterface {
    @Override
    public void execute(ServerNetwork server, Object parameters) {
        String[] params = parameters.toString().split(";");
        int id = Integer.valueOf(params[0]);
        String clickedBeer = params[1];
        Log.i("Comm", "ID "+id+", Beer: "+params[1]);

        if(getCurrentBeer().equals(clickedBeer)){
            Log.i("Comm", "beer clickable");
            if(isBeer_crushable()){
                Log.i("Comm", "beer crushable");
                server.sendToClient(id, Constants.MAIN_ACTIVITY_TYPE, Constants.CLICKED_BEER, new String[]{clickedBeer+" SUCCESS!!!"});
                setBeer_crushable(false);
            } else {
                Log.i("Comm", "too late to crush");
                server.sendToClient(id, Constants.MAIN_ACTIVITY_TYPE, Constants.CLICKED_BEER, new String[]{clickedBeer+" LATE!!!"});
            }
        } else {
            Log.i("Comm", "misclicked");
            server.sendToClient(id, Constants.MAIN_ACTIVITY_TYPE, Constants.CLICKED_BEER, new String[]{clickedBeer+" MISCLICKED!!!"});
        }
    }

}
