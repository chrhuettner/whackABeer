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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        tableLayout = findViewById(R.id.tableLayout);

        playersList = (ArrayList<HashMap<String, int>>) getIntent().getSerializableExtra("playersList");

        if (playersList != null) {
            populateTable(playersList);
        }
    }

    private void populateTable(ArrayList<HashMap<String, String>> playersList) {
        for (HashMap<String, String> player : playersList) {
            TableRow row = new TableRow(this);

            TextView nameTextView = new TextView(this);
            nameTextView.setText(player.get("name"));
            nameTextView.setPadding(16, 16, 16, 16);

            TextView pointsTextView = new TextView(this);
            pointsTextView.setText(player.get("points"));
            pointsTextView.setPadding(16, 16, 16, 16);

            row.addView(nameTextView);
            row.addView(pointsTextView);

            tableLayout.addView(row);
        }
    }
}