package backend.server.ClientAction;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import backend.server.Config;
import backend.server.Constants;


public class ActionPing implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String parameter = clientMessage.split(" ")[1];
        int id = Integer.parseInt(parameter);
        Log.i("PING", "RECEIVED " + clientMessage);

        Toast.makeText(activity, "Received Ping request from Server with id " + id,
                Toast.LENGTH_SHORT).show();


    }
}
