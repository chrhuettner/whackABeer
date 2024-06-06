package backend.server;

import static backend.server.NetworkServiceFinder.SERVICE_TYPE;

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

public class TestMain {

    public static ArrayList<NsdServiceInfo> hosts = new ArrayList<>();

    public static ClientLogic logic = new ClientLogic(new HashMap<>(), new HashMap<>());

    public static AppCompatActivity testActivity;

    public static void main() {

        TestMain.testActivity = testActivity;
        Log.i("analyze","registerActivity");
        logic.registerActivity(Constants.MAIN_ACTIVITY_TYPE, testActivity);

    }

    public static void discoverServer(){
        Config.role = Config.ROLE.CLIENT;

        NetworkServiceDiscoveryClient discoveryClient = new NetworkServiceDiscoveryClient(testActivity, SERVICE_TYPE);

        NsdDiscoveryListener listener = new NsdDiscoveryListener(testActivity);

        discoveryClient.startDiscovery(listener);
    }

    public static void pingAllClients(){
        if(Config.role == Config.ROLE.SERVER){
            for(int i = 1; i<=Config.amountOfClients;i++){
                ServerActionHandler.triggerAction(Constants.PING, i);
            }
        }
    }

    public static void addHost(NsdServiceInfo host) {
        hosts.add(host);
        Log.d(Constants.LOG_MAIN, "Found host " + host.getHost());

        Toast.makeText(testActivity,"Found host " + host.getHost(),
                Toast.LENGTH_SHORT).show();

        ClientConnection client = new ClientConnection(host.getHost().getHostAddress(), host.getPort(), 1000, logic);
        client.start();
        ClientHandler.setClient(client);
    }

    public static void removeHost(NsdServiceInfo host) {
        hosts.remove(host);

        Toast.makeText(testActivity,"Lost host " + host.getHost(),
                Toast.LENGTH_SHORT).show();

        Log.d(Constants.LOG_MAIN, "Lost host " + host.getHost());

    }

    public static void startServer() throws InterruptedException {
        Log.i("analyze","Starting the server");

        Config.role = Config.ROLE.SERVER;
        logic.registerServerResponse(Constants.MAIN_ACTIVITY_TYPE, testActivity);
        ServerNetwork server = new ServerNetwork(testActivity.getApplicationContext());
        server.start();
        Thread.sleep(100);

        ClientConnection client = new ClientConnection("localhost", server.getPort(), 1000, logic);
        ServerActionHandler.setServer(server);
        client.start();
        Log.i("analyze","Host is set as a client on this server");

        ClientHandler.setClient(client);

        Toast.makeText(testActivity,"Server started ",
                Toast.LENGTH_SHORT).show();

        Thread.sleep(100);
    }

}
