package backend.client;

import backend.network.NetworkConnection;

public class ClientConnection {

    private final NetworkConnection connection;

    public ClientConnection(String ip, int port, int timeout, ResponseLogic logic) {
        this.connection = new NetworkConnection(ip,  port, timeout, logic);
    }

    public void start(){
        this.connection.start();
    }

    public void sendMessage(String msg) {
         connection.send(msg);
    }

    public void stopConnection() {
        connection.close();
    }

    public NetworkConnection getConnection() {
        return connection;
    }
}
