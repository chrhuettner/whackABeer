package backend.network;

import static shared.Constants.LOG_NETWORK;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayDeque;

import backend.client.ResponseLogic;
import shared.Config;
import shared.Constants;


public class NetworkConnection extends Thread {

    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;

    private String lastMsgReceived;

    private static final String TAG = LOG_NETWORK + "-NC";

    private boolean isRunning;

    private final Object runningToken;
    private final ArrayDeque<String> outputBuffer;

    private String ip;
    private int port;
    private int timeout;

    private int clientId;
    private boolean closeHasBeenRequested;

    private Object closeRequestToken = false;

    private boolean isServerConnection;

    private ResponseLogic logic;


    public NetworkConnection(String ip, int port, int timeout, ResponseLogic logic) {
        this.isRunning = true;
        runningToken = "";
        outputBuffer = new ArrayDeque<>();

        this.ip = ip;
        this.port = port;
        this.timeout = timeout;
        closeHasBeenRequested = false;
        closeRequestToken = "";
        this.logic = logic;
    }

    public NetworkConnection(Socket socket, ResponseLogic logic) {
        this.isRunning = true;
        runningToken = "";
        outputBuffer = new ArrayDeque<>();

        this.socket = socket;
        closeHasBeenRequested = false;
        closeRequestToken = "";
        this.logic = logic;
    }

    @Override
    public void run() {
        try {
            if (socket == null) {
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), timeout);
            }
            reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));

            if (Config.role == Config.ROLE.CLIENT) {
                String clientID = reader.readLine();
                Log.i(Constants.LOG_MAIN, clientID);
                Config.clientID = Integer.parseInt(clientID.split(":")[1]);
                Log.i(LOG_NETWORK, "I RECEIVED MY ID FROM SERVER "+Config.clientID);
                this.clientId = Config.clientID;

                if (Config.clientID == -1) {
                    //TODO: Notify UI that the server rejected the client (server is full)
                    return;
                }
                //TODO: Notify server that this Client has joined the lobby

            } else {
                //Host always has ID 1
                Config.clientID = 1;
            }

            Log.d(TAG, "Waiting for incoming messages");
            while (true) {
                read:
                if (reader.ready()) {

                    if (hasCloseBeenRequested()) {
                        Log.e(TAG, "CONNECTION COULD NOT READ, CLOSE HAS ALREADY BEEN REQUESTED");
                        break read;
                    }

                    String msg = reader.readLine();
                    Log.d(TAG, "Incoming message " + msg);
                    this.lastMsgReceived = msg;
                    if (logic != null) {

                        String[] messageSplit = msg.split(":");
                        if (messageSplit.length >= 2) {
                            logic.sendHandle(messageSplit[1], messageSplit[0], isServerConnection);
                        }
                    }
                }

                sendThroughNetwork();

                synchronized (runningToken) {
                    if (!isRunning) {
                        break;
                    }
                }

            }

            synchronized (closeRequestToken) {
                if (hasCloseBeenRequested()) {
                    synchronized (outputBuffer) {
                        //Allow remaining messages to be sent to the server before closing the connection
                        if (outputBuffer.isEmpty()) {
                            synchronized (runningToken) {
                                isRunning = false;
                            }
                        }
                    }
                }
            }

            Thread.sleep(1);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());

        } catch (InterruptedException e) {
            Log.e(TAG, "Interrupted!" + e);

            Thread.currentThread().interrupt();
        } finally {
            try {
                this.shutdown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        synchronized (outputBuffer) {
            if (!outputBuffer.isEmpty()) {
                for (String message : outputBuffer) {
                    Log.e(TAG, "CONNECTION COULDNT SEND " + message + ". CONNECTION IS ALREADY CLOSED!");
                }
            }
        }
    }

    public void send(String message) {
        if (hasCloseBeenRequested()) {
            Log.e(TAG, "CONNECTION COULD NOT SEND " + message + ". CONNECTION HAS ALREADY BEEN REQUESTED TO CLOSE!");
            return;
        }

        synchronized (outputBuffer) {

            outputBuffer.add(message);

        }

    }

    private void sendThroughNetwork() {
        synchronized (outputBuffer) {
            while (!outputBuffer.isEmpty()) {
                try {
                    String message = outputBuffer.pop();
                    Log.d(TAG, "Sending following message to server: " + message);
                    writer.write(message);
                    writer.newLine();
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public String getLastMsgReceived() {
        if (this.lastMsgReceived == null) {
            return "";
        }
        return this.lastMsgReceived;
    }

    public void close() {
        synchronized (closeRequestToken) {
            closeHasBeenRequested = true;
        }

    }

    public boolean hasCloseBeenRequested() {
        synchronized (closeRequestToken) {
            return closeHasBeenRequested;
        }
    }

    private void shutdown() throws IOException {
        if (this.reader != null) {
            this.reader.close();
        }
        if (this.writer != null) {
            this.writer.close();
        }
        if (this.socket != null) {
            this.socket.close();
        }
        synchronized (runningToken) {
            isRunning = false;
        }
    }

    public int getClientId() {
        return clientId;
    }

    public boolean isServerConnection() {
        return isServerConnection;
    }

    public void setServerConnection(boolean serverConnection) {
        isServerConnection = serverConnection;
    }
}
