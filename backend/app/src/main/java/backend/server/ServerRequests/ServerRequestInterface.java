package backend.server.ServerRequests;

import backend.server.ServerNetwork;

public interface ServerRequestInterface {

    public void execute(ServerNetwork server, Object parameters);
}
