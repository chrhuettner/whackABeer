package frontend;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import backend.client.ResponseLogic;
import backend.server.ServerRequestHandler;
import shared.Config;
import shared.Constants;
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

        binding.button3.setOnClickListener(view -> {
            if (Config.role == Config.ROLE.SERVER) {
                for (int i = 1; i <= Config.amountOfClients; i++) {
                    ServerRequestHandler.triggerAction(Constants.PING, i);
                }

            }
        });

    }
}
