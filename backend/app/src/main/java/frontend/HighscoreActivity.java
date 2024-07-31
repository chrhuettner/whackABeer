package frontend;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import whack.beer.R;

import android.widget.Button;
import android.widget.TextView;
import backend.database.DatabaseHelper;

public class HighscoreActivity extends AppCompatActivity {

    private TextView highscoreValue;
    private Button backToStartButton;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore_layout);

        initializeDisplay();

        db = new DatabaseHelper(HighscoreActivity.this);

        highscoreValue = findViewById(R.id.highscoreValue);
        backToStartButton = findViewById(R.id.backToStartButton);

        int highscore = db.getCurrentHighscore();
        highscoreValue.setText(String.valueOf(highscore));

        // Set click listener for the back to start button
        backToStartButton.setOnClickListener(v -> {
            Intent intent = new Intent(HighscoreActivity.this, StartActivity.class);
            startActivity(intent);
            finish();
        });
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
