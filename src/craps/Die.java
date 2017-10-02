package craps;

public class Die {
    private int lastRoll;

    public void roll() {
        lastRoll = (int) (Math.random() * 6) + 1;
    }

    public int getLastRoll() {
        return lastRoll;
    }
}
