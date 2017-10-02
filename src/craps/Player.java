package craps;

import java.util.LinkedList;
import java.util.List;

import static craps.Bet.DONTPASS;
import static craps.Bet.PLACE6;
import static craps.Bet.PLACE8;

public class Player {
    int bankroll;
    int onRackMoney;
    int profit;
    int stopLoss;
    double stopWin;
    //    Strategy strategy;
    List<Turn> turns;
    boolean isShooting;
    boolean isBusted;
    boolean sessionWon;
    Dice dice;
    //    List<Bet> bets;
    Bet bet;

    private int[] betSeries2 = {2, 3, 5, 8, 12, 17, 24, 34, 47, 65, 90};
//    private int[] betSeries68 = {6, 12, 18, 24, 30,36, 42, 48, 54, 60};
//    private int[] betSeries68 = {6, 12, 18, 24, 36, 48, 66, 90};
//    private int[] betSeries68 = {6, 12, 18, 24, 30, 36, 42, 48, 54, 60,66,72,78,84,90,96};
    private int[] betSeries68 = {6, 12, 18, 24, 30, 36};
    private int[] betSeries5 = {5, 8, 12, 17, 24, 34, 47, 65, 90};
    private int[] betSeries10 = {5, 8, 12, 17, 24, 34, 47, 65, 90};
    int[] betSeries;
    int betLevel;


    public Player(int bankroll) {
        this.bankroll = bankroll;
        turns = new LinkedList<>();
        onRackMoney = bankroll;
        profit = 0;
        betLevel = 0;
        betSeries = betSeries68;
        stopWin = 100000;
//        bets = new ArrayList<>();
    }

    public int netWin() {
        return profit;
    }

    public void shootDice() {
        dice.roll();
    }

    public void makeNewBet() {
        if (!isBusted && !sessionWon) {
            int betAmount = betSeries[betLevel];
//            System.out.println("Betting Amount " + betAmount);
//            bet = PASSLINE.with(betAmount);
//            bet = DONTPASS.with(betAmount);
//            bet = PLACE6.with(betAmount);
            bet = PLACE8.with(betAmount);
            onRackMoney -= betAmount;
        } else
            System.out.println("No more bets");
    }

    public void passedDice() {
        isShooting = false;
    }

    public void receiveDice(Dice dice) {
        this.dice = dice;
        isShooting = true;
    }

    public void won() {
        onRackMoney += bet.pay() + bet.getAmount();
        profit += bet.pay();
        if (profit >= bankroll * stopWin)
            sessionWon = true;
        if (betLevel > 0)
            betLevel--;
    }

    public void lost() {
        profit -= bet.getAmount();
        if (betLevel < betSeries.length - 1)
            betLevel++;
        else {
            isShooting = false;
            isBusted = true;
            System.out.println("Player BUSTED!!");
        }
    }
}
