package craps;


public class Bet {
    private int amount;
    private String type;
    private double payout;
    static Bet PASSLINE = new Bet(BetType.PASS_LINE, 1);
    static Bet DONTPASS = new Bet(BetType.DONT_PASS, 1);
    static Bet COME = new Bet(BetType.COME, 1);
    static Bet DONTCOME = new Bet(BetType.DONT_COME, 1);
    static Bet PLACE4 = new Bet(BetType.PLACE_4_10, 39 / 20);
    static Bet PLACE5 = new Bet(BetType.PLACE_5_9, 7 / 5);
    static Bet PLACE6 = new Bet(BetType.PLACE_6, 7.0 / 6);
    static Bet PLACE8 = new Bet(BetType.PLACE_8, 7.0 / 6);
    static Bet PLACE9 = new Bet(BetType.PLACE_5_9, 7 / 5);
    static Bet PLACE10 = new Bet(BetType.PLACE_4_10, 39 / 20);

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public int pay() {
        return (int) (amount * payout);
    }

    private Bet(String type, double payout) {
        this.payout = payout;
        this.type = type;
    }

    public Bet with(int amount) {
        this.amount = amount;
        return this;
    }
}
