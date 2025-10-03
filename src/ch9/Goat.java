package ch9;

public class Goat extends Animal implements Locomotion {

    public Goat(String name) {
        super("goat");
    }

    @Override
    protected String getSound() {
        return "bleat";
    }

    @Override
    public String getLocomotion() {
        return "walks";
    }

    @Override
    public String eats() {
        return "grass";
    }
}
