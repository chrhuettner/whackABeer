package frontend;

import static backend.client.ClientResponseHandler.client;
import static frontend.SinglePlayerActivity.logic;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.HashMap;

import backend.client.ClientResponseHandler;
import backend.client.ResponseLogic;
import backend.network.NetworkConnection;
import backend.server.ServerRequestHandler;
import shared.Config;
import shared.Constants;
import whack.beer.R;
import whack.beer.databinding.GameLayoutBinding;


public class GameActivity extends AppCompatActivity {
    private GameLayoutBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = GameLayoutBinding.inflate(getLayoutInflater());
        View viewBinder = binding.getRoot();
        setContentView(viewBinder);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );




        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String playerName = (String) bundle.get("playerName");
        binding.descriptionForGame.setText(playerName);


//        logic.registerServerResponse(Constants.MAIN_ACTIVITY_TYPE, this);
        String preActivity = (String) bundle.get("preActivity");
        if(preActivity.equals("SinglePlayer")) {
            frontend.SinglePlayerActivity.logic.registerActivity(Constants.MAIN_ACTIVITY_TYPE, this);




        } else {
            frontend.TestMain.logic.registerActivity(Constants.MAIN_ACTIVITY_TYPE, this);

            // Only display server name in multiplayer mode
            ServerRequestHandler.triggerAction(Constants.CONFIG, Constants.SERVER_NAME);
        }

        ServerRequestHandler.triggerAction(Constants.CONFIG, playerName+":"+Constants.PLAYER_NAME);




        ServerRequestHandler.triggerAction(Constants.GAME_START, "P");


    }

    public void onCloseClicked(View view) {
        finish();
    }

    public void onBeerClick(View view) {
        int id = view.getId();
        String beerName = "";

        if (id == R.id.beer1) {
            beerName = "beer1";
        } else if (id == R.id.beer2) {
            beerName = "beer2";
        } else if (id == R.id.beer3) {
            beerName = "beer3";
        } else if (id == R.id.beer4) {
            beerName = "beer4";
        } else if (id == R.id.beer5) {
            beerName = "beer5";
        } else if (id == R.id.beer6) {
            beerName = "beer6";
        } else if (id == R.id.beer7) {
            beerName = "beer7";
        } else if (id == R.id.beer8) {
            beerName = "beer8";
        } else if (id == R.id.beer9) {
            beerName = "beer9";
        } else if (id == R.id.beer10) {
            beerName = "beer10";
        } else if (id == R.id.beer11) {
            beerName = "beer11";
        } else if (id == R.id.beer12) {
            beerName = "beer12";
        } else {
            Toast.makeText(this, "Unknown beer clicked!", Toast.LENGTH_SHORT).show();
            return;
        }
        callToServer(beerName);
    }

    public void callToServer(String beer) {
        Toast.makeText(this, beer + " clicked", Toast.LENGTH_SHORT).show();
        // Getting clientId via client object (needs to be public then) (for one user it is 0)
        ServerRequestHandler.triggerAction(Constants.CLICKED_BEER, client.getClientId()+ ":"+beer);
    }


}
