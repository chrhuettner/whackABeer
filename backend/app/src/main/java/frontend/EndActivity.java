package frontend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;

import whack.beer.R;

//Mit ChatGPT erstellt und bearbeitet
public class EndActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private ArrayList<HashMap<String, String>> playersList;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_layout);

        Intent intent = getIntent();
        String palyerName = intent.getStringExtra("Name");
        String playerPoints = intent.getStringExtra("Punkte");

        playersList = new ArrayList<>();
        HashMap<String, String> player = new HashMap<>();
        player.put("name", palyerName);
        player.put("punkte", playerPoints);
        playersList.add(player);

        tableLayout = findViewById(R.id.tableLayout);
        button = findViewById(R.id.backButton);

        if (playersList != null) {
            populateTable(playersList);
        }

        button.setOnClickListener(v -> {
            Intent intent2 = new Intent(EndActivity.this, StartActivity.class);
            startActivity(intent2);
            finish();
        });
    }

    private void populateTable(ArrayList<HashMap<String, String>> playersList) {
        for (HashMap<String, String> player : playersList) {
            TableRow row = new TableRow(this);

            TextView nameTextView = new TextView(this);
            nameTextView.setText(player.get("name"));
            nameTextView.setPadding(16, 16, 16, 16);

            TextView pointsTextView = new TextView(this);
            pointsTextView.setText(player.get("punkte"));
            pointsTextView.setPadding(16, 16, 16, 16);

            row.addView(nameTextView);
            row.addView(pointsTextView);

            tableLayout.addView(row);
        }
    }
}