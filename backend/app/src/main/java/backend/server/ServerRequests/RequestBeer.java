package backend.server.ServerRequests;

import static backend.server.ServerRequestHandler.getCurrentBeer;
import static backend.server.ServerRequestHandler.isBeer_crushable;
import static backend.server.ServerRequestHandler.setBeer_crushable;

import android.util.Log;

import backend.object.Player;
import backend.server.ServerNetwork;
import shared.Config;
import shared.Constants;

public class RequestBeer implements ServerRequestInterface {
    @Override
    public void execute(ServerNetwork server, Object parameters) {
        String[] params = parameters.toString().split(";");
        int id = Integer.valueOf(params[0]);
        String clickedBeer = params[1];
        int points = Integer.valueOf(params[2]);
        Log.i("Comm", "ID "+id+", Beer: "+params[1]);

        if(getCurrentBeer().equals(clickedBeer)){
            Log.i("Comm", "beer clickable");
            if(isBeer_crushable()){
                Log.i("Comm", "beer crushable");
                int newPoints = setPlayerPoints(id, points);
                server.sendToClient(id, Constants.MAIN_ACTIVITY_TYPE, Constants.CLICKED_BEER, new String[]{clickedBeer+" SUCCESS!!!", ""+newPoints});
                setBeer_crushable(false);
            } else {
                Log.i("Comm", "too late to crush");
                int newPoints = setPlayerPoints(id, -1);
                server.sendToClient(id, Constants.MAIN_ACTIVITY_TYPE, Constants.CLICKED_BEER, new String[]{clickedBeer+" LATE!!!", ""+newPoints});
            }
        } else {
            Log.i("Comm", "misclicked");
            int newPoints = setPlayerPoints(id, -3);
            server.sendToClient(id, Constants.MAIN_ACTIVITY_TYPE, Constants.CLICKED_BEER, new String[]{clickedBeer+" MISCLICKED!!!", ""+newPoints});
        }
    }

    private int setPlayerPoints(int id, int points){
        for(Player player: Config.players){
            if(player.getId() == id){
                player.setPoints(player.getPoints()+points);
                return player.getPoints();
            }
        }
        return 0;
    }

}
