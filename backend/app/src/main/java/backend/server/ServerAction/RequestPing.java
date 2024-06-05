package backend.server.ServerAction;


import backend.server.Constants;
import backend.server.ServerNetwork;


public class RequestPing implements ServerActionInterface {
    @Override
    public void execute(ServerNetwork server, Object parameters) {
        int id = (int) parameters;
        server.sendToClient(id, Constants.MAIN_ACTIVITY_TYPE, Constants.PING, new String[]{""+id});
    }

}
