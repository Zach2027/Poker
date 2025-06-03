import java.util.ArrayList;
import java.util.Scanner;
public class Player {
    
    private int money = 100;
    private ArrayList<String> hand = new ArrayList<String>();
    private String name;
    private int minBet =0;
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
        System.out.println("How much do you want to bet");
        int bet = scanner.nextInt();
            if (money-bet>=0 && bet >= minBet)
            {
                money -= bet;
                game.updatePot(bet);
                minBet = bet;
            }
            else if(money-bet <= 0 ){
                System.out.print("You're all in!!");
                money = 0;
                game.updatePot(money);
            }
        return money;
    }
    public void dealHand(){
        hand.add(deck.getCard());
        hand.add(deck.getCard());
    }


}