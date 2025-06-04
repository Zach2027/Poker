import java.util.ArrayList;
import java.util.Scanner;
public class Player {
    
    private int money = 100;
    private ArrayList<String> hand = new ArrayList<String>();
    private String name;
    public int minBet =0;
    private Boolean isInHand = true;
    private static Deck deck = new Deck();
     public Player(String n){

        name = n;    }
    
    private Scanner scanner = new Scanner(System.in);
    private Game game = new Game();
    
    public String showHand(){
        return hand.get(1) + " " + hand.get(0);
    }

    public String getName(){
        return name;
    }
    public int playerBet(){
        System.out.print("The pot is " + game.pot + " The current bet is " + minBet);
        System.out.println("How much do you want to bet(type 0 to fold)");
        int bet = scanner.nextInt();
            if (money-bet>=0 && bet >= minBet)
            {
                money -= bet;
                game.updatePot(bet);
                minBet = bet;
                System.out.print("You raised it to " + minBet);
            }
            else if(money-bet <= 0 ){
                System.out.print("You're all in!!");
                money = 0;
                game.updatePot(money);
            }
            else if(bet < minBet){
                System.out.println("You need to call the bet to stay in");
                playerBet();
            }
            else if(bet == 0 && minBet == 0){
                System.out.print("You check");
            }
            else if(bet == 0 && minBet > 0){
                System.out.println("You folded");
                isInHand = false;
            }
            System.out.print("The amount of chips you have is ");
            return money;
    }
    public void dealHand(){
        hand.add(deck.getCard());
        hand.add(deck.getCard());
    }
    public boolean getIsInHand(){
        return isInHand;

    }

}