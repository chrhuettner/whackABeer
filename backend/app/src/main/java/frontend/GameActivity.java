package frontend;

import static backend.client.ClientResponseHandler.client;
import static frontend.SinglePlayerActivity.logic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
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


public class GameActivity extends AppCompatActivity implements ClickHandler {
    private GameLayoutBinding binding;
    private int[] beerIDs = new int[12];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = GameLayoutBinding.inflate(getLayoutInflater());
        View viewBinder = binding.getRoot();
        setContentView(viewBinder);

        initializeDisplay();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String playerName = (String) bundle.get("playerName");
        binding.descriptionForGame.setText(playerName);

        String preActivity = (String) bundle.get("preActivity");
        if(preActivity.equals("SinglePlayer")) {
            frontend.SinglePlayerActivity.logic.registerServerResponse(Constants.MAIN_ACTIVITY_TYPE, this);
            frontend.SinglePlayerActivity.logic.registerActivity(Constants.MAIN_ACTIVITY_TYPE, this);
        } else {
            frontend.TestMain.logic.registerServerResponse(Constants.MAIN_ACTIVITY_TYPE, this);
            frontend.TestMain.logic.registerActivity(Constants.MAIN_ACTIVITY_TYPE, this);

            // Only display server name in multiplayer mode
            ClientResponseHandler.sendMessageToServer(Constants.MAIN_ACTIVITY_TYPE, Constants.CONFIG, Constants.SERVER_NAME);
        }

        // Initialize Player Name
        ClientResponseHandler.sendMessageToServer(Constants.MAIN_ACTIVITY_TYPE, Constants.CONFIG, playerName+";"+Constants.PLAYER_NAME);

        // Only the host starts the game
        if(Config.role == Config.ROLE.SERVER) {
            ServerRequestHandler.triggerAction(Constants.GAME_START, "P");
        }

        beerIDs[0] = R.id.beer1;
        beerIDs[1] = R.id.beer2;
        beerIDs[2] = R.id.beer3;
        beerIDs[3] = R.id.beer4;
        beerIDs[4] = R.id.beer5;
        beerIDs[5] = R.id.beer6;
        beerIDs[6] = R.id.beer7;
        beerIDs[7] = R.id.beer8;
        beerIDs[8] = R.id.beer9;
        beerIDs[9] = R.id.beer10;
        beerIDs[10] = R.id.beer11;
        beerIDs[11] = R.id.beer12;

        for (int i = 0; i < beerIDs.length; i++) {
            setupGestureDetector(beerIDs[i]);
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private void setupGestureDetector(int viewId) {
        Log.i("Taps", ""+viewId);
        ImageButton button = findViewById(viewId);
        GestureListener listener = new GestureListener(button, this);
        GestureDetector gestureDetector = new GestureDetector(this, listener);

        button.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
    }

    public void onCloseClicked(View view) {
        finish();
    }

    private String getBeerNameById(int id) {
        String beerName = "";

        for (int i = 0; i < beerIDs.length; i++) {
            if(beerIDs[i] == id){
                i++;
                beerName = "beer"+i;
                break;
            }
        }
        if(beerName.equals("")){
            return null;
        }
        return beerName;
    }
    @Override
    public void onBeerClick(View view) {
        String beerName = getBeerNameById(view.getId());
        if (beerName == null) {
            Toast.makeText(this, "Unknown beer clicked!", Toast.LENGTH_SHORT).show();
        }

        Log.d("Taps", "Single Tap for " + beerName);
        ClientResponseHandler.sendMessageToServer(Constants.MAIN_ACTIVITY_TYPE, Constants.CLICKED_BEER, Config.clientID + ";" + beerName);
    }

    @Override
    public void onBeerDoubleClick(View view) {
        String beerName = getBeerNameById(view.getId());
        if(beerName == null) {
            Toast.makeText(this, "Unknown beer clicked!", Toast.LENGTH_SHORT).show();
        }

        Log.d("Taps","Double Tap for " + beerName);
        ClientResponseHandler.sendMessageToServer(Constants.MAIN_ACTIVITY_TYPE, Constants.CLICKED_BEER, Config.clientID+ ";"+beerName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeDisplay();
    }
    public void initializeDisplay() {
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
    }

}
