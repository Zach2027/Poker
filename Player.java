import java.util.ArrayList;
import java.util.Scanner;
public class Player {
    
    private int money = 100;
    private ArrayList<String> hand = new ArrayList<String>();
    
    private Scanner scanner = new Scanner(System.in);
    private Game game = new Game();
    public  Player(ArrayList<String> initial_hand){
       
    }
    public String getHand(){
        return hand.get(1) + hand.get(0);
    }
    public int playerBet(){
        System.out.println("How much do you want to print");
        int bet = scanner.nextInt();
        money -= bet;
        game.updatePot(bet);
        return bet;
    }
}
