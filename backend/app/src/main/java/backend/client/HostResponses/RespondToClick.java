package backend.client.HostResponses;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import backend.server.ServerRequestHandler;
import shared.Config;
import shared.Constants;

public class RespondToClick implements ServerResponseInterface {

    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] msg = clientMessage.split(" ");
        Log.i("Comm", "Sending to server: "+ msg[1]);
        ServerRequestHandler.triggerAction(Constants.CLICKED_BEER, msg[1]);
    }
}
