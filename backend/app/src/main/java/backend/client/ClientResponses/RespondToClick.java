package backend.client.ClientResponses;

import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import whack.beer.R;

public class RespondToClick implements ClientResponseInterface {

    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] params = clientMessage.split(" ");
        String beer = params[1];
        String message = params[2];
        Log.i("Comm","RECEIVED "+beer +" " +message);

        TextView gotPoint = activity.findViewById(R.id.gotPoint);
        gotPoint.setText(message);
        if(!message.equals("SUCCESS!!!")){
            int red = ContextCompat.getColor(activity, R.color.red_600);
            gotPoint.setBackgroundColor(red);
        } else {
            int green = ContextCompat.getColor(activity, R.color.green_600);
            gotPoint.setBackgroundColor(green);
            ImageButton imageButton1 = activity.findViewById(R.id.beer1);
            ImageButton imageButton2 = activity.findViewById(R.id.beer2);
            ImageButton imageButton3 = activity.findViewById(R.id.beer3);
            ImageButton imageButton4 = activity.findViewById(R.id.beer4);
            ImageButton imageButton5 = activity.findViewById(R.id.beer5);
            ImageButton imageButton6 = activity.findViewById(R.id.beer6);
            ImageButton imageButton7 = activity.findViewById(R.id.beer7);
            ImageButton imageButton8 = activity.findViewById(R.id.beer8);
            ImageButton imageButton9 = activity.findViewById(R.id.beer9);
            ImageButton imageButton10 = activity.findViewById(R.id.beer10);
            ImageButton imageButton11 = activity.findViewById(R.id.beer11);
            ImageButton imageButton12 = activity.findViewById(R.id.beer12);

            switch (beer) {
                case "beer1":
                    imageButton1.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.crushed_above));
                    break;
                case "beer2":
                    imageButton2.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.crushed_above));
                    break;
                case "beer3":
                    imageButton3.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.crushed_above));
                    break;
                case "beer4":
                    imageButton4.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.crushed_above));
                    break;
                case "beer5":
                    imageButton5.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.crushed_above));
                    break;
                case "beer6":
                    imageButton6.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.crushed_above));
                    break;
                case "beer7":
                    imageButton7.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.crushed_above));
                    break;
                case "beer8":
                    imageButton8.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.crushed_above));
                    break;
                case "beer9":
                    imageButton9.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.crushed_above));
                    break;
                case "beer10":
                    imageButton10.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.crushed_above));
                    break;
                case "beer11":
                    imageButton11.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.crushed_above));
                    break;
                case "beer12":
                    imageButton12.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.crushed_above));
                    break;
                default:
                    Toast.makeText(activity, "Unknown beer clicked!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }
}