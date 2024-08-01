package frontend;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import backend.client.ClientResponseHandler;
import backend.object.Player;
import backend.server.ServerRequestHandler;
import backend.database.DatabaseHelper;
import shared.Config;
import shared.Constants;
import whack.beer.R;
import whack.beer.databinding.GameLayoutBinding;


public class GameActivity extends AppCompatActivity implements ClickHandler {
    private GameLayoutBinding binding;
    private int[] beerIDs = new int[12];
    private int[] beerPoints = new int[12];
    private CountDownTimer countDownTimer;
    private DatabaseHelper db;
    private TextView timerTextView;
    private TextView pointsTextView;
    private int playerPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = GameLayoutBinding.inflate(getLayoutInflater());
        View viewBinder = binding.getRoot();
        setContentView(viewBinder);

        timerTextView = findViewById(R.id.timerTextView);
        pointsTextView = findViewById(R.id.points);
        initializeDisplay();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String playerName = (String) bundle.get("playerName");
        binding.descriptionForGame.setText(playerName);

        db = new DatabaseHelper(GameActivity.this);

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
            startTimer();
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

        beerPoints[0] = 4;
        beerPoints[1] = -1;
        beerPoints[2] = 1;
        beerPoints[3] = 4;
        beerPoints[4] = -5;
        beerPoints[5] = 1;
        beerPoints[6] = -2;
        beerPoints[7] = 5;
        beerPoints[8] = 3;
        beerPoints[9] = 2;
        beerPoints[10] = 3;
        beerPoints[11] = -3;
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

    private int getBeerPointsById(int id) {
        int points = 0;

        for (int i = 0; i < beerIDs.length; i++) {
            if(beerIDs[i] == id){
                points = beerPoints[i];
                i++;
                break;
            }
        }

        return points;
    }

    @Override
    public void onBeerClick(View view) {
        String beerName = getBeerNameById(view.getId());
        int points = getBeerPointsById(view.getId());
        if (beerName == null) {
            Toast.makeText(this, "Unknown beer clicked!", Toast.LENGTH_SHORT).show();
        }
        playerPoints += points;
        updatePointsTextView();

        Log.d("Taps", "Single Tap for " + beerName);
        ClientResponseHandler.sendMessageToServer(Constants.MAIN_ACTIVITY_TYPE, Constants.CLICKED_BEER, Config.clientID + ";" + beerName + ";" + points);
    }

    @Override
    public void onBeerDoubleClick(View view) {
        String beerName = getBeerNameById(view.getId());
        int points = getBeerPointsById(view.getId());

        if(beerName == null) {
            Toast.makeText(this, "Unknown beer clicked!", Toast.LENGTH_SHORT).show();
        }
        playerPoints += points;
        updatePointsTextView();

        Log.d("Taps","Double Tap for " + beerName);
        ClientResponseHandler.sendMessageToServer(Constants.MAIN_ACTIVITY_TYPE, Constants.CLICKED_BEER, Config.clientID+ ";"+beerName + ";" + (2*points));
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
    private void startTimer() {
        //Timer wird auf 60 Sekunden gesetzt (Spielzeit)
        countDownTimer = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Spielzeit: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timerTextView.setText("Ende");
                //Todo: Tatsächlichen Spielernamen und Punkte hinzufügen
                int p = 0;
                for(Player player : Config.players){
                    if(player.getId() == Config.clientID){
                        p = player.getPoints();
                    }
                }
                db.setHighscore(Config.PLAYER_NAME, p);
                Intent intent = new Intent(GameActivity.this, EndActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void updatePointsTextView() {
        pointsTextView.setText("Points: "+String.valueOf(playerPoints));
    }
}
