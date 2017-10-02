package craps;

public class Dice {
    Die die1, die2;

    public Dice() {
        die1 = new Die();
        die2 = new Die();
    }

    public void roll() {
        die1.roll();
        die2.roll();
    }

    public int getResult() {
        return die1.getLastRoll() + die2.getLastRoll();

    }
}
