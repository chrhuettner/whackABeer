package backend.object;

public class Beer {

    private String name;
    private int value;

    public Beer(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
