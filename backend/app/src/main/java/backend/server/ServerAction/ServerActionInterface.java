package backend.server.ServerAction;

import backend.server.ServerNetwork;

public interface ServerActionInterface {

    public void execute(ServerNetwork server, Object parameters);
}
