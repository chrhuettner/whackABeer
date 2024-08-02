package frontend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import backend.object.Player;
import shared.Config;
import whack.beer.R;

//Mit ChatGPT erstellt und bearbeitet von Marko Mrsic
public class EndActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private Button button;

    private ListView listView;
    private EndScreenAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_layout);

        button = findViewById(R.id.backButton);

        listView = findViewById(R.id.list_view);
        adapter = new EndScreenAdapter(this, Config.players);
        listView.setAdapter(adapter);

        button.setOnClickListener(v -> {
            Intent intent2 = new Intent(EndActivity.this, StartActivity.class);
            startActivity(intent2);
            finish();
        });
    }

}