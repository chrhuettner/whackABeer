package backend.server;


public class Config {

    public static int clientID = -1;
    public static int amountOfClients = 0;

    public static enum ROLE {CLIENT, SERVER};
    public static ROLE role = ROLE.CLIENT;

    public static int MAX_CLIENTS = 6;


}
