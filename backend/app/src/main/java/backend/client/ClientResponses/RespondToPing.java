package backend.client.ClientResponses;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import backend.client.ClientResponseHandler;
import shared.Config;
import shared.Constants;


public class RespondToPing implements ClientResponseInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String parameter = clientMessage.split(" ")[1];
        int id = Integer.parseInt(parameter);
        Log.i("PING", "RECEIVED " + clientMessage + " FROM activity = " + activity);

        //No need to respond to the ping if this client is the host and therefore is the server
        if(Config.clientID != 1) {
            Toast.makeText(activity, "Received Ping request from Server with id " + id,
                    Toast.LENGTH_SHORT).show();

            ClientResponseHandler.sendMessageToServer(Constants.MAIN_ACTIVITY_TYPE, Constants.PING, new Object[]{Config.clientID});
        }

    }
}
