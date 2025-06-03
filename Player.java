import java.util.ArrayList;
import java.util.Scanner;
public class Player {
    
    private int money = 100;
    private ArrayList<String> hand = new ArrayList<String>();
    private String name;
    private int minBet =0;
    private Boolean isInHand = true; 
     public Player(String n){

        name = n;    }
    
    private Scanner scanner = new Scanner(System.in);
    private Game game = new Game();
    public  Player(ArrayList<String> initial_hand){

       
    }
    public String getHand(){
        return hand.get(1) + hand.get(0);
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
}
