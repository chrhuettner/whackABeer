package backend.client.ClientResponses;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RespondToGameStart implements ClientResponseInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String parameter = clientMessage.split(" ")[1];

        Log.i("GAME_START", "RECEIVED " + clientMessage + " FROM activity = " + activity);

        Toast.makeText(activity, "GAME STARTS NOW", Toast.LENGTH_SHORT).show();
    }
}
