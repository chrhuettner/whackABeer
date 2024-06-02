package backend.server.ServerResponse;


import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import backend.server.Constants;


public class RespondToPing implements ServerResponseInterface {

    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] parameters = clientMessage.split(" ")[1].split(";");
        int id = Integer.parseInt(parameters[0]);

        Toast.makeText(activity, "Received Ping from Client with id " + id,
                Toast.LENGTH_SHORT).show();

    }

}
