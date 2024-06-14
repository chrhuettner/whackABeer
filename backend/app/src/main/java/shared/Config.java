package shared;


import java.util.ArrayList;

public class Config {

    public static int clientID = -1;
    public static String PLAYER_NAME = "DEFAULT_USER";
    public static String SERVER_NAME = "DEFAULT_SERVER_NAME";
    public static String SERVER_PASSWORD = "DEFAULT_PASSWORD";
    public static int amountOfClients = 0;

    public static enum ROLE {CLIENT, SERVER};
    public static ROLE role = ROLE.CLIENT;

    public static int MAX_CLIENTS = 6;

    public static ArrayList<String> players = new ArrayList<>();


}
