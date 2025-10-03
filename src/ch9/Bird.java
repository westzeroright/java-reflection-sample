package ch9;

public class Bird extends Animal {
    private boolean walks;

    public Bird() {
        super("bird");
    }

    public Bird(String name, boolean walks) {
        super(name);
        setWalks(walks);
    }

    public Bird(String name) {
        super(name);
    }

    public boolean walks() {
        return walks;
    }

    public void setWalks(boolean walks) {
        this.walks = walks;
    }

    @Override
    protected String getSound() {
        return "chirp";
    }

    @Override
    public String eats() {
        return "worms";
    }
}
