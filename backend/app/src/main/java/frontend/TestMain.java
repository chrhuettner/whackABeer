package frontend;

import static backend.network.NetworkServiceFinder.SERVICE_NAME;
import static backend.network.NetworkServiceFinder.SERVICE_TYPE;

import android.content.Intent;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;

import backend.client.ClientResponseHandler;
import backend.client.ResponseLogic;
import backend.network.NetworkConnection;
import backend.network.NetworkServiceDiscoveryClient;
import backend.network.NetworkServiceDiscoveryListener;
import backend.network.NetworkServiceFinder;
import backend.server.ServerNetwork;
import backend.server.ServerRequestHandler;
import frontend.TestActivity;
import shared.Config;
import shared.Constants;
import whack.beer.R;

public class TestMain {

    public static ArrayList<NsdServiceInfo> hosts = new ArrayList<>();

    public static ResponseLogic logic = new ResponseLogic(new HashMap<>(), new HashMap<>());

    public static TestActivity testActivity;

    public static boolean isConnected = false;

    public static void main(TestActivity testActivity) {
        isConnected = false;

        TestMain.testActivity = testActivity;
        Log.i("analyze", "registerActivity");
        logic.registerActivity(Constants.MAIN_ACTIVITY_TYPE, testActivity);

        Button hostButton = testActivity.findViewById(R.id.host_Button);
        Button joinButton = testActivity.findViewById(R.id.join_button);
        Button pingButton = testActivity.findViewById(R.id.button_Ping);

        hostButton.setOnClickListener(view -> {
            try {
                startServer();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        joinButton.setOnClickListener(view -> {
            Config.role = Config.ROLE.CLIENT;

            NetworkServiceDiscoveryClient discoveryClient = new NetworkServiceDiscoveryClient(testActivity, SERVICE_TYPE);

            NetworkServiceDiscoveryListener listener = new NetworkServiceDiscoveryListener(testActivity);

            discoveryClient.startDiscovery(listener);
        });

        pingButton.setOnClickListener(view -> {
            if (Config.role == Config.ROLE.SERVER) {
                for (int i = 1; i <= Config.amountOfClients; i++) {
                    ServerRequestHandler.triggerAction(Constants.PING, i);
                }
            }
        });


    }

    public static void addHost(NsdServiceInfo host) {
        hosts.add(host);
        Log.d(Constants.LOG_MAIN, "Found host " + host.getHost());

        Toast.makeText(testActivity, "Found host " + host.getHost(),
                Toast.LENGTH_SHORT).show();

        if (host.getServiceType().equals("."+SERVICE_TYPE) && host.getServiceName().equals(SERVICE_NAME) && !isConnected) {
            Log.d(Constants.LOG_MAIN, "Connecting to host " + host.getHost());

            NetworkConnection client = new NetworkConnection(host.getHost().getHostAddress(), host.getPort(), 1000, logic);
            client.start();
            ClientResponseHandler.setClient(client);
            isConnected = true;
        }
    }

    public static void removeHost(NsdServiceInfo host) {
        hosts.remove(host);

        Toast.makeText(testActivity, "Lost host " + host.getHost(),
                Toast.LENGTH_SHORT).show();

        Log.d(Constants.LOG_MAIN, "Lost host " + host.getHost());

    }

    public static void startServer() throws InterruptedException {
        Log.i("analyze", "Starting the server");

        Config.role = Config.ROLE.SERVER;
        logic.registerServerResponse(Constants.MAIN_ACTIVITY_TYPE, testActivity);
        ServerNetwork server = new ServerNetwork(testActivity.getApplicationContext());
        server.start();
        Thread.sleep(100);

        NetworkConnection client = new NetworkConnection("localhost", server.getPort(), 1000, logic);
        ServerRequestHandler.setServer(server);
        client.start();
        Log.i("analyze", "Host is set as a client on this server");

        ClientResponseHandler.setClient(client);

        Toast.makeText(testActivity, "Server started ",
                Toast.LENGTH_SHORT).show();

        //Toast.makeText(MainMenuActivity.this, "Server " + serverName + " started on " + currentTime, Toast.LENGTH_SHORT).show();

        Thread.sleep(100);

    }


}
