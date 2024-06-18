package frontend;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import backend.client.ClientResponseHandler;
import backend.client.ResponseLogic;
import backend.network.NetworkConnection;
import backend.server.ServerNetwork;
import backend.server.ServerRequestHandler;
import shared.Config;
import shared.Constants;
import whack.beer.R;
import whack.beer.databinding.GameLayoutBinding;
import whack.beer.databinding.SinglePlayerLayoutBinding;

public class SinglePlayerActivity extends AppCompatActivity {

    public static ResponseLogic logic = new ResponseLogic(new HashMap<>(), new HashMap<>());
    private SinglePlayerLayoutBinding binding;
    public static boolean isConnected = false;
    public static GameActivity gameActivity;

    private NetworkConnection client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = SinglePlayerLayoutBinding.inflate(getLayoutInflater());
        View viewBinder = binding.getRoot();
        setContentView(viewBinder);

        initializeDisplay();

        isConnected = false;

        logic.registerActivity(Constants.MAIN_ACTIVITY_TYPE, this);

        Button playButton = binding.playButton;
        TextView playerName = binding.playerName;


        playButton.setOnClickListener(view -> {
            try {
                startServer();
                Intent intent = new Intent(this, GameActivity.class);
                intent.putExtra("playerName", playerName.getText().toString());
                intent.putExtra("preActivity", "SinglePlayer");
                startActivity(intent);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void startServer() throws InterruptedException {
        Log.i("analyze", "Starting the server");

        Config.role = Config.ROLE.SERVER;
        logic.registerServerResponse(Constants.MAIN_ACTIVITY_TYPE, this);
        ServerNetwork server = new ServerNetwork(this.getApplicationContext(), logic);
        server.start();
        Thread.sleep(100);

        NetworkConnection client = new NetworkConnection("localhost", server.getPort(), 1000, logic);
        ServerRequestHandler.setServer(server);
        client.start();
        Log.i("analyze", "Host is set as a client on this server");

        ClientResponseHandler.setClient(client);

        Toast.makeText(this, "Server started ",
                Toast.LENGTH_SHORT).show();

        //Toast.makeText(MainMenuActivity.this, "Server " + serverName + " started on " + currentTime, Toast.LENGTH_SHORT).show();

        Thread.sleep(100);

    }

    public void onCloseClicked(View view) {
        finish();
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
