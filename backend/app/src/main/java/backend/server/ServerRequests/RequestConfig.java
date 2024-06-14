package backend.server.ServerRequests;

import android.util.Log;

import backend.server.ServerNetwork;
import shared.Config;
import shared.Constants;

public class RequestConfig implements ServerRequestInterface {
    @Override
    public void execute(ServerNetwork server, Object parameters) {
        if(!parameters.toString().contains(":")){
            // Only Request ServerName up to now
            server.sendToClient(Config.clientID, Constants.MAIN_ACTIVITY_TYPE, Constants.CONFIG, new String[]{Config.SERVER_NAME+" "+Constants.SERVER_NAME});
        }
        else {
            try {
                String[] params = parameters.toString().split(":");
                String newText = params[0];
                String specifiedParam = params[1];
                switch (specifiedParam) {
                    case "playerName":
                        Config.players.add(newText);
                        server.sendToClient(Config.clientID, Constants.MAIN_ACTIVITY_TYPE, Constants.CONFIG, new String[]{newText + " " + Constants.PLAYER_NAME});
                        break;
                    case Constants.SERVER_NAME:
                        Config.SERVER_NAME = newText;
                        break;
                    case Constants.SERVER_PASSWORD:
                        Config.SERVER_PASSWORD = newText;
                        break;
                    default:
                        Log.e("Comm", "Parameter not specified (correctly)");
                        break;
                }
                Log.i("Comm", Config.PLAYER_NAME);
            } catch (Exception e) {
                Log.e("Comm", "Not a String entered for player name, lobby name or password");
            }
        }
    }

}
