package backend.client.ClientResponses;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import shared.Config;
import shared.Constants;
import whack.beer.R;

public class RespondToConfig implements ClientResponseInterface{

    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] params = clientMessage.split(" ");
        String newText = params[1];
        String specifiedParam = params[2];
        if (specifiedParam.equals(Constants.PLAYER_NAME)) {
            Config.PLAYER_NAME = newText;
            TextView textView = activity.findViewById(R.id.descriptionForGame);
            textView.setText(newText);
        } else if (specifiedParam.equals(Constants.SERVER_NAME)) {
            TextView textView = activity.findViewById(R.id.appName);
            textView.setText(newText);
        } else {
            Log.e("Comm","Parameter not specified (correctly)");
        }
        Log.i("Client", "RECEIVED " + clientMessage + " FROM activity = " + activity);
    }
}
