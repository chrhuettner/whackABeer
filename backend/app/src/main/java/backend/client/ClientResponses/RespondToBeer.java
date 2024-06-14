package backend.client.ClientResponses;

import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import whack.beer.R;

public class RespondToBeer implements ClientResponseInterface {

    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String beer = clientMessage.split(" ")[1];

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

        imageButton1.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.egger_deckel));
        imageButton2.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.goesser_deckel));
        imageButton3.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ottakringer_deckel));
        imageButton4.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.edelweiss_deckel));
        imageButton5.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.punti_deckel));
        imageButton6.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.stiegl_deckel));
        imageButton7.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.murauer_deckel));
        imageButton8.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.schwechater_deckel));
        imageButton9.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.wieselburger_deckel));
        imageButton10.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.zipfer_deckel));
        imageButton11.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.kaiser_deckel));
        imageButton12.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.villacher_deckel));

        switch (beer) {
            case "beer1":
                imageButton1.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.egger));
                break;
            case "beer2":
                imageButton2.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.gutbessergoesser));
                break;
            case "beer3":
                imageButton3.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ottakringer16erblech));
                break;
            case "beer4":
                imageButton4.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.edelweiss));
                break;
            case "beer5":
                imageButton5.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.punti));
                break;
            case "beer6":
                imageButton6.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.stiegl));
                break;
            case "beer7":
                imageButton7.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.murauer));
                break;
            case "beer8":
                imageButton8.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.schwechater));
                break;
            case "beer9":
                imageButton9.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.wieselburger));
                break;
            case "beer10":
                imageButton10.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.zipfer));
                break;
            case "beer11":
                imageButton11.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.kaiser));
                break;
            case "beer12":
                imageButton12.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.villacher));
                break;
            default:
                Toast.makeText(activity, "Unknown beer clicked!", Toast.LENGTH_SHORT).show();
                return;
        }

        Log.i("BEER_CLICK", "RECEIVED " + clientMessage + " FROM activity = " + activity);
    }

}
