package backend.client.HostResponses;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import backend.server.ServerRequestHandler;
import shared.Config;
import shared.Constants;

public class RespondToConfig implements ServerResponseInterface {

    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        Log.i("Comm", "Sending to server: "+ clientMessage);
        String[] msg = clientMessage.split(" ");
        ServerRequestHandler.triggerAction(Constants.CONFIG, Config.clientID+"/"+msg[1]);
    }
}
