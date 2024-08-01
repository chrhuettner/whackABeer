package frontend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import backend.object.Beer;
import whack.beer.R;

//Von ChatGpt erstellt
public class BeerAdapter extends BaseAdapter {

    private Context context;
    private List<Beer> beers;
    private LayoutInflater inflater;

    public BeerAdapter(Context context, List<Beer> beers) {
        this.context = context;
        this.beers = beers;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return beers.size();
    }

    @Override
    public Object getItem(int position) {
        return beers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.beer_item, parent, false);
        }

        TextView itemName = convertView.findViewById(R.id.item_name);
        TextView itemValue = convertView.findViewById(R.id.item_value);

        Beer beer = beers.get(position);
        itemName.setText(beer.getName());
        itemValue.setText(String.valueOf(beer.getValue()));

        return convertView;
    }
}
