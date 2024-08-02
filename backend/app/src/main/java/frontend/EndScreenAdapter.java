package frontend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import backend.object.Player;
import whack.beer.R;

public class EndScreenAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<Player> playersList;

        public EndScreenAdapter(Context context, ArrayList<Player> playersList) {
            this.context = context;
            this.playersList = playersList;
        }

        @Override
        public int getCount() {
            return playersList.size();
        }

        @Override
        public Object getItem(int position) {
            return playersList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.endscreen_row_layout, parent, false);
            }

            Player currentPlayer = (Player) getItem(position);

            TextView nameTextView = convertView.findViewById(R.id.nameTextView);
            TextView pointsTextView = convertView.findViewById(R.id.pointsTextView);

            nameTextView.setText(currentPlayer.getName());
            pointsTextView.setText(currentPlayer.getPoints()+"");

            return convertView;
        }
    }

