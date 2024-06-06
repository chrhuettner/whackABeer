package backend.server;

import static shared.Constants.LOG_ERROR;
import static shared.Constants.LOG_NETWORK;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import shared.Config;
import backend.network.NetworkConnection;
import backend.network.NetworkServiceFinder;
import frontend.TestMain;

public class ServerNetwork extends Thread {

    private ServerSocket serverSocket;

    private int port;

    private boolean serverInterrupted;

    private HashMap<Integer, NetworkConnection> clientConnections;
    private NetworkServiceFinder nsd;


    private Object syncTearDownToken;


    public ServerNetwork() {
        initProperties();
    }

    public ServerNetwork(Context context) {
        this.nsd = new NetworkServiceFinder(context);

        initProperties();
    }

    public String getIP() {
        return getIPAddress();
    }

    public static String getIPAddress() {

        List<NetworkInterface> interfaces = new ArrayList<>();
        try {
            interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
        } catch (SocketException e) {
            Log.e(LOG_ERROR, "Error while trying to create list of NetworkInterfaces: " + e);
        }
        for (NetworkInterface networkInterface : interfaces) {
            List<InetAddress> addresses = Collections.list(networkInterface.getInetAddresses());
            for (InetAddress address : addresses) {

                if (!address.isLoopbackAddress()) {
                    String ip = address.getHostAddress();
                    if (ip.indexOf(':') == -1) {
                        return ip;
                    }

                }
            }
        }
        Log.e(LOG_ERROR, "No IP address found!");
        return "";
    }

    private void initProperties() {
        this.port = 0;
        clientConnections = new HashMap<>();
        serverInterrupted = false;
        this.syncTearDownToken = "";
    }

    @Override
    public void run() {
        try {
            initializeServerSocket();

            if (nsd != null) {
                nsd.registerService(getPort());
                Log.d(LOG_NETWORK, "REGISTERED SERVICE");
            }

            Log.d(LOG_NETWORK, "Server started on port " + port);
            while (true) {

                synchronized (syncTearDownToken){
                    if(serverInterrupted){
                        break;
                    }
                }

                Socket socket = serverSocket.accept();
                NetworkConnection clientSocket = new NetworkConnection(socket, TestMain.logic);
                clientSocket.setServerConnection(true);
                clientConnections.put(Config.amountOfClients  + 1, clientSocket);
                clientSocket.start();



                if (Config.amountOfClients >= Config.MAX_CLIENTS) {
                    //Refuse Client
                    clientSocket.send("IPINNIT:-1");
                    continue;
                }


                int clientID = Config.amountOfClients  + 1;
                clientSocket.send("IPINNIT:" + clientID);
                Config.amountOfClients++;
               //TODO: Create a player object and add it to a game logic


            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                tearDown();
            } catch (IOException e) {
                Log.d(LOG_ERROR, "Error while tearing down server: " + e);
            }
        }
    }

    public void initializeServerSocket() throws IOException {
        this.serverSocket = new ServerSocket(this.port);
        this.port = serverSocket.getLocalPort();
    }

    public void sendToClient(int clientId, String message){
        synchronized (syncTearDownToken) {
            if(!hasClosed()) {
                    if(clientConnections.containsKey(clientId)) {
                        clientConnections.get(clientId).send(message);
                    }else{
                        Log.e(LOG_ERROR, "COULD NOT SEND "+ message+", CLIENT WITH ID "+clientId+" DOES NOT EXIST!");
                    }

            }else{
                Log.e(LOG_ERROR, "COULD NOT SEND "+ message+", SERVER ALREADY CLOSED!");
            }
        }
    }

    public void broadcast(String message) {
        synchronized (syncTearDownToken) {
            if(!hasClosed()) {
                for (NetworkConnection clientConnection : clientConnections.values()) {
                    clientConnection.send(message);
                }
            }else{
                Log.e(LOG_ERROR, "COULD NOT BROADCAST "+ message+", SERVER ALREADY CLOSED!");
            }
        }
    }

    public void broadcast(String activity, String prefix, String[] args) {
        this.broadcast(activity + ":" + prefix + " " + String.join(";", args));
    }

    public void sendToClient(int id, String activity, String prefix, String[] args) {
        this.sendToClient(id, activity + ":" + prefix + " " + String.join(";", args));
    }

    public void stopThread() {
        synchronized (syncTearDownToken) {
            this.serverInterrupted = true;
        }
    }

    public void tearDown() throws IOException {
        stopThread();
        for (NetworkConnection clientConn : clientConnections.values()) {
            clientConn.close();
            //clientConn.interrupt();
        }
        if (nsd != null) {
            nsd.tearDown();
        }
        clientConnections.clear();
        serverSocket.close();
    }

    public boolean hasClosed() {
        synchronized (syncTearDownToken) {
            return this.serverInterrupted;
        }
    }

    public int getPort() {
        return port;
    }

    public HashMap<Integer, NetworkConnection> getConnections() {
        return this.clientConnections;
    }
}



