package backend.server;

public class ClientConnection {

    private final NetworkConnection connection;

    public ClientConnection(String ip, int port, int timeout, ClientLogic logic) {
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
