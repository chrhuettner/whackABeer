package frontend;
import android.content.pm.ActivityInfo;
import android.net.nsd.NsdServiceInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import frontend.TestMain;
import whack.beer.R;

public class TestActivity extends AppCompatActivity {


        RecyclerView recyclerView;
        //HostAdapter adapter;
        private final ArrayList<NsdServiceInfo> hosts = new ArrayList<>();
        public Button joinButton;
        private NsdServiceInfo selection = null;
        private static final String SERVICE_NAME = "_delta-dkt";
        private static final String SERVICE_PROTOCOLL ="_tcp";
        private static final String SERVICE_TYPE = SERVICE_NAME+"."+SERVICE_PROTOCOLL;


        public void setSelectedHost(int hostId) {
            selection = hosts.get(hostId);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.testactivity_layout);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );

            //Button backButton = findViewById(R.id.backbtn);
            //joinButton = findViewById(R.id.joinbtn);
            //String newUser = getIntent().getStringExtra(INTENT_PARAMETER);


            TestMain.main(this);






            //ainActivity.subscribeToLogic(Constants.FIND_HOST_VIEW_ACTIVITY_TYPE, this);

        }

        public void onCloseClicked(View view) {
        finish();
        }


}

