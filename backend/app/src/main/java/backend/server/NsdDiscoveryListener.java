package backend.server;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


public class NsdDiscoveryListener implements NsdManager.DiscoveryListener{
    private final AppCompatActivity activity;
    private NsdManager manager;

    public NsdDiscoveryListener(AppCompatActivity activity){
        this.activity = activity;
    }

    public void setManager(NsdManager manager){
        this.manager = manager;
    }

    private void printErrorMessage(String message){
        this.activity.runOnUiThread(() -> {
           //TODO: Display message on ui element
        });
    }

    private void printStatusMessage(String message){
        this.activity.runOnUiThread(() -> {
            //TODO: Display message on ui element
        });
    }

    @Override
    public void onStartDiscoveryFailed(String s, int i) {
        printErrorMessage(s);
    }

    @Override
    public void onStopDiscoveryFailed(String s, int i) {
        printErrorMessage(s);
    }

    @Override
    public void onDiscoveryStarted(String s) {
        printStatusMessage(s);
    }

    @Override
    public void onDiscoveryStopped(String s) {
        printStatusMessage(s);
    }

    @Override
    public void onServiceFound(NsdServiceInfo nsdServiceInfo) {
        this.manager.resolveService(nsdServiceInfo, new NsdManager.ResolveListener() {
            @Override
            public void onResolveFailed(NsdServiceInfo nsdServiceInfo, int i) {
                printErrorMessage("Failed to resolve service!");
            }

            @Override
            public void onServiceResolved(NsdServiceInfo nsdServiceInfo) {
                printStatusMessage("Successfully resolved service!");
                Log.d("Game-", nsdServiceInfo.toString());
            }
        });
        printStatusMessage("A new service has been found!");
        Log.d("Client-HostList", "I have found a new service!");
    }

    @Override
    public void onServiceLost(NsdServiceInfo nsdServiceInfo) {
        Log.d("Client-HostList", "I have lost a service!");
        printStatusMessage("A new service has been lost!");

    }
}
