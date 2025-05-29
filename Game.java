import java.util.ArrayList;

public class Game {
    private int pot = 0;
    private Deck deck = new Deck();
    private  ArrayList<String> initial_hand = new ArrayList<>();

    public void updatePot(int n){
        pot += n;
    }
    public void dealHand(){
        
        initial_hand.add(deck.getCard());
        initial_hand.add(deck.getCard());
    }
}
