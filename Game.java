import java.util.ArrayList;

public class Game {
    public static int pot = 0;
    private static Deck deck = new Deck();
    private  ArrayList<String> initial_hand = new ArrayList<>();

    public void updatePot(int n){
        pot += n;
    }
    public int getPot(){
        return pot;
    }
    public static Deck gimmeDeck(){
        return deck; 

    }

    
    //public void dealHand(){
        
        //initial_hand.add(deck.getCard());
        //initial_hand.add(deck.getCard());
        
    //}
}
