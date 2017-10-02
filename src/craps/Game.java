package craps;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import static craps.BetType.*;
import static java.util.stream.Collectors.toList;

public class Game {
    Dice dice;
    int numSevenOuts;
    List<Player> players;
    boolean isComeOutRoll;
    int point;
    List<Bet> bets;
    int rollCount;


    private Game() {
        this.dice = new Dice();
        players = new LinkedList<>();
        isComeOutRoll = true;
    }

    void includePlayers(int numPlayers, int... bankrolls) {
        players.addAll(IntStream.range(0, numPlayers)
                .boxed()
                .map(i -> new Player(bankrolls[i]))
                .collect(toList()));
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.includePlayers(1, 300);
        game.start(10000000);
    }

    public void start(int maxSevenOuts) {
        while (maxSevenOuts > numSevenOuts) {

            Player player = players.get(numSevenOuts % players.size());
            if (player.isBusted) {
                System.out.println("Player BUSTED!!!");
                break;
            }
            if (player.sessionWon) {
                System.out.println("Player Session WON!!!");
                break;
            }
            player.makeNewBet();
            player.receiveDice(dice);

            while (player.isShooting) {
//                announce();
                player.shootDice();
                rollCount++;

                if (isNatural()) {
//                    System.out.println(dice.getResult() + " Natural!");
                    switch (player.bet.getType()) {
                        case PASS_LINE:
                            winner(player);
                            break;
                        case DONT_PASS:
                            loser(player);
                            break;
                        case PLACE_6:
                            if (dice.getResult() == 7)
                                loser(player);
                            break;
                        case PLACE_8:
                            if (dice.getResult() == 7)
                                loser(player);
                            break;
                    }
                } else if (isCraps()) {
//                    System.out.println(dice.getResult() + " Craps!");
                    switch (player.bet.getType()) {
                        case PASS_LINE:
                            loser(player);
                            break;
                        case DONT_PASS:
                            if (is23Craps())
                                winner(player);
                            break;
                    }
                } else if (isComeOutRoll) {
                    isComeOutRoll = false;
                    point = dice.getResult();
//                    System.out.println("Point is " + point);
                    switch (player.bet.getType()) {
                        case PLACE_6:
                            winner(player);
                            break;
                        case PLACE_8:
                            winner(player);
                            break;
                    }
                } else if (isSevenOut()) {
                    System.out.println("****** SEVEN OUT!!! ******");
                    switch (player.bet.getType()) {
                        case PASS_LINE:
                            loser(player);
                            break;
                        case DONT_PASS:
                            winner(player);
                            break;
                        case PLACE_6:
                            loser(player);
                            break;
                        case PLACE_8:
                            loser(player);
                            break;
                    }
                    player.passedDice();
                    isComeOutRoll = true;
                    numSevenOuts++;
                } else if (hasMadePoint()) {
                    isComeOutRoll = true;
//                    System.out.println(String.format("Shooter shoot %d (%d,%d). Pass line WINNER!", dice.getResult(), dice.die1.getLastRoll(), dice.die2.getLastRoll()));
                    switch (player.bet.getType()) {
                        case PASS_LINE:
                            winner(player);
                            break;
                        case DONT_PASS:
                            loser(player);
                            break;
                        case PLACE_6:
                            if (dice.getResult() == 6)
                                winner(player);
                            break;
                        case PLACE_8:
                            if (dice.getResult() == 8)
                                winner(player);
                            break;
                    }
                } else {
//                    System.out.println(String.format("Shooter shoot %d (%d,%d)", dice.getResult(), dice.die1.getLastRoll(), dice.die2.getLastRoll()));
                    switch (player.bet.getType()) {
                        case PLACE_6:
                            if (dice.getResult() == 6)
                                winner(player);
                            break;
                        case PLACE_8:
                            if (dice.getResult() == 8)
                                winner(player);
                            break;
                    }
                }

            }
        }
        System.out.println("Total num seven outs - " + numSevenOuts);
        System.out.println("Total roll count - " + rollCount);
    }

    private boolean hasMadePoint() {
        return !isComeOutRoll && dice.getResult() == point;
    }

    private boolean isSevenOut() {
        return !isComeOutRoll && dice.getResult() == 7;
    }

    private boolean isCraps() {
        return isComeOutRoll && (dice.getResult() == 2 || dice.getResult() == 3 || dice.getResult() == 12);
    }

    private boolean is23Craps() {
        return isComeOutRoll && (dice.getResult() == 2 || dice.getResult() == 3);
    }

    private boolean isNatural() {
        return isComeOutRoll && (dice.getResult() == 7 || dice.getResult() == 11);
    }

    private void loser(Player player) {
        player.lost();
        System.out.println("Player LOST " + player.bet.getAmount() + " :: netWin " + player.netWin());
        player.makeNewBet();
    }

    private void winner(Player player) {
        player.won();
        System.out.println("Player WON " + player.bet.pay() + " :: netWin " + player.netWin());
        player.makeNewBet();
    }

    private void announce() {
        if (isComeOutRoll) {
            System.out.print("Come out roll... ");
        } else {
            System.out.print("Shooting for " + point + "... ");
        }
    }
}
