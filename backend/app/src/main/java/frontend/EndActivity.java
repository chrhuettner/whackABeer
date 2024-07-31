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

import backend.object.Player;
import shared.Config;
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

        playersList = new ArrayList<>();
        fillPlayerList();

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

    private void fillPlayerList(){
        for(Player player: Config.players){
            HashMap<String, String> p = new HashMap<>();
            p.put("name", player.getName());
            p.put("punkte", ""+player.getPoints());
            playersList.add(p);
        }
    }
}