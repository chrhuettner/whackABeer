package frontend;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import backend.object.Beer;
import whack.beer.R;

public class BierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beer_layout);

        ListView listView = findViewById(R.id.listView);

        List<Beer> beers = new ArrayList<>();
        beers.add(new Beer("Egger", 4));
        beers.add(new Beer("Gösser", -1));
        beers.add(new Beer("Ottakringer", 1));
        beers.add(new Beer("Edelweiss", 4));
        beers.add(new Beer("Puntigamer", -5));
        beers.add(new Beer("Stiegl", 1));
        beers.add(new Beer("Murauer", -2));
        beers.add(new Beer("Schwechater", 5));
        beers.add(new Beer("Wieselburger", 3));
        beers.add(new Beer("Zipfer", 2));
        beers.add(new Beer("Kaiser", 3));
        beers.add(new Beer("Villacher", -3));

        BeerAdapter adapter = new BeerAdapter(this, beers);
        listView.setAdapter(adapter);
    }

    public void backToStart(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}
