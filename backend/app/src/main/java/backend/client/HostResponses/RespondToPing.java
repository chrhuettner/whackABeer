package backend.client.HostResponses;


import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import shared.Constants;


public class RespondToPing implements ServerResponseInterface {

    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] parameters = clientMessage.split(" ")[1].split(";");
        int id = Integer.parseInt(parameters[0]);

        Log.i(Constants.LOG_MAIN, "RECEIVED PING " + clientMessage + "FROM activity = " + activity);

        Toast.makeText(activity, "Received Ping from Client with id " + id,
                Toast.LENGTH_SHORT).show();

    }

}
