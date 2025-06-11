import java.util.ArrayList;
import java.util.Scanner;

public class Player {

    private int money = 100;
    private ArrayList<String> hand = new ArrayList<String>();
    private String name;
    private static ArrayList<String> communityCards = new ArrayList<>();
    public static int minBet = 0;
    private Boolean isInHand = true;
    public static int numPlayerInTheHand;
    public static Deck deck = new Deck();
    private int lastBet = -82737;
    public Player(String n) {

        name = n;
    }

    public void changeIsInHand(){
        isInHand = true;
    }

    private Scanner scanner = new Scanner(System.in);
    private Game game = new Game();

    public String showHand() {
        return hand.get(1) + " " + hand.get(0);
    }

    public String getName() {
        return name;
    }

    public int playerBet() {
        
        if (numPlayerInTheHand > 1 && isInHand) {
            System.out.println("The pot is " + game.pot + " The current bet is " + minBet + ".");
            System.out.println("How much do you want to bet(type 0 to fold)");
            int bet = scanner.nextInt();
            lastBet = bet;
            if (money - bet >= 0 && bet >= minBet && isInHand) {
                money -= bet;
                game.updatePot(bet);
                minBet = bet;
                System.out.println("You raised it to " + minBet);
            } else if (money - bet <= 0 && isInHand) {
                System.out.println("You're all in!!");
                money = 0;
                game.updatePot(money);
            } else if (bet < minBet && bet != 0 && isInHand) {
                System.out.println("You need to call the bet to stay in");
                playerBet();
            } else if (bet == 0 && minBet == 0 && isInHand) {
                System.out.println("You check");
            } else if (bet == 0 && minBet > 0 && isInHand) {
                System.out.println("You folded");
                isInHand = false;
                numPlayerInTheHand--;

            }
            System.out.print("The amount of chips you have is " + money);
        }
        return money;
    }
    public int getLastBet(){
        return lastBet;
    }

    public void dealHand() {
        hand.add(deck.getCard());
        hand.add(deck.getCard());
    }

    public static void dealFlop() {
        communityCards.clear(); 
        communityCards.add(deck.getCard());
        communityCards.add(deck.getCard());
        communityCards.add(deck.getCard());

        System.out.println(communityCards);
        
    }

    public ArrayList<String> getCommunityCards(){

        return communityCards;
    }

    public boolean getIsInHand() {
        return isInHand;

    }
    public void ante(){
        money = money - 5;
        game.updatePot(5);
    }
    public void collectPot(){
        money += Game.pot;
    }
    public void setMinBet(){
        minBet = 0;
    }
    public void resetLast(){
        lastBet = 0;
    }
}