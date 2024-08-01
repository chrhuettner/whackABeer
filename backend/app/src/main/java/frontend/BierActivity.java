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
        beers.add(new Beer("Egger", getResources().getInteger(R.integer.Egger)));
        beers.add(new Beer("Gösser", getResources().getInteger(R.integer.Gösser)));
        beers.add(new Beer("Ottakringer", getResources().getInteger(R.integer.Ottakringer)));
        beers.add(new Beer("Edelweiss", getResources().getInteger(R.integer.Edelweiss)));
        beers.add(new Beer("Puntigamer", getResources().getInteger(R.integer.Puntigamer)));
        beers.add(new Beer("Stiegl", getResources().getInteger(R.integer.Stiegl)));
        beers.add(new Beer("Murauer", getResources().getInteger(R.integer.Murauer)));
        beers.add(new Beer("Schwechater", getResources().getInteger(R.integer.Schwechater)));
        beers.add(new Beer("Weiselburger", getResources().getInteger(R.integer.Weiselburger)));
        beers.add(new Beer("Zipfer", getResources().getInteger(R.integer.Zipfer)));
        beers.add(new Beer("Kaiser", getResources().getInteger(R.integer.Kaiser)));
        beers.add(new Beer("Villacher", getResources().getInteger(R.integer.Villacher)));

        BeerAdapter adapter = new BeerAdapter(this, beers);
        listView.setAdapter(adapter);
    }

    public void backToStart(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}
