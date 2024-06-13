package frontend;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import backend.client.ResponseLogic;
import backend.server.ServerRequestHandler;
import shared.Config;
import shared.Constants;
import whack.beer.R;
import whack.beer.databinding.GameLayoutBinding;


public class GameActivity extends AppCompatActivity {
    private GameLayoutBinding binding;
    public static ResponseLogic logic = new ResponseLogic(new HashMap<>(), new HashMap<>());

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

        logic.registerServerResponse(Constants.MAIN_ACTIVITY_TYPE, this);

        ServerRequestHandler.triggerAction(Constants.GAME_START, "P");

        binding.beer1.setOnClickListener(view -> {
            if (Config.role == Config.ROLE.SERVER) {
                for (int i = 1; i <= Config.amountOfClients; i++) {
                    ServerRequestHandler.triggerAction(Constants.PING, i);
                }

            }
        });

    }

    public void onCloseClicked(View view) {
        finish();
    }

    public void onBeerClick(View view) {
        int id = view.getId();
        // Android Studio does need a static context for the R class, therefore switch case is not possible
        if (id == R.id.beer1) {
            Toast.makeText(this, "Egger clicked!", Toast.LENGTH_SHORT).show();
            callToServer("beer1");
        } else if (id == R.id.beer2) {
            Toast.makeText(this, "Gösser clicked!", Toast.LENGTH_SHORT).show();
            callToServer("beer2");
        } else if (id == R.id.beer3) {
            Toast.makeText(this, "Ottakringer clicked!", Toast.LENGTH_SHORT).show();
            callToServer("beer3");
        } else if (id == R.id.beer4) {
            Toast.makeText(this, "Edelweiss clicked!", Toast.LENGTH_SHORT).show();
            callToServer("beer4");
        } else if (id == R.id.beer5) {
            Toast.makeText(this, "Punti clicked!", Toast.LENGTH_SHORT).show();
            callToServer("beer5");
        } else if (id == R.id.beer6) {
            Toast.makeText(this, "Stiegl clicked!", Toast.LENGTH_SHORT).show();
            callToServer("beer6");
        } else if (id == R.id.beer7) {
            Toast.makeText(this, "Murauer clicked!", Toast.LENGTH_SHORT).show();
            callToServer("beer7");
        } else if (id == R.id.beer8) {
            Toast.makeText(this, "Schwechater clicked!", Toast.LENGTH_SHORT).show();
            callToServer("beer8");
        } else if (id == R.id.beer9) {
            Toast.makeText(this, "Wieselburger clicked!", Toast.LENGTH_SHORT).show();
            callToServer("beer9");
        } else if (id == R.id.beer10) {
            Toast.makeText(this, "Zipfer clicked!", Toast.LENGTH_SHORT).show();
            callToServer("beer10");
        } else if (id == R.id.beer11) {
            Toast.makeText(this, "Kaiser clicked!", Toast.LENGTH_SHORT).show();
            callToServer("beer11");
        } else if (id == R.id.beer12) {
            Toast.makeText(this, "Villacher clicked!", Toast.LENGTH_SHORT).show();
            callToServer("beer12");
        } else {
            Toast.makeText(this, "Unknown beer clicked!", Toast.LENGTH_SHORT).show();
        }
    }

    public void callToServer(String beer){

    }


}
