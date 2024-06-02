package backend.server.ServerAction;


import backend.server.Constants;


public class RequestPing implements ServerActionInterface {
    @Override
    public void execute(Object parameters) {
        int id = (int) parameters;
    }

}
