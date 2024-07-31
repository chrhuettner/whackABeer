package frontend;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;

//Mit ChatGPT erstellt und bearbeitet
public class EndActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private ArrayList<HashMap<String, int>> playersList;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        Intent intent = getIntent();
        String palyerName = intent.getStringExtra("Name");
        int playerPoints = intent.getIntExtra("Punkte");

        playersList = new ArrayList<>();
        HashMap<String, int> player = new HashMap<>();
        player.put("name", palyerName);
        player.put("punkte", playerPoints);
        playersList.add(player);

        tableLayout = findViewById(R.id.tableLayout);
        backButton = findViewById(R.id.backButton);

        if (playersList != null) {
            populateTable(playersList);
        }

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(EndActivity.this, StartActivity.class);
            startActivity(intent);
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