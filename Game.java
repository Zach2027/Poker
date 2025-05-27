import java.util.ArrayList;

public class Game {
    private int pot = 0;
    private Deck deck = new Deck();

    public void updatePot(int n){
        pot += n;
    }
    public void dealHand(){
        ArrayList<Integer> initial_hand = new ArrayList<>();
        Player.initial_hand.add(deck.getCard());
        Player.initial_hand.add(deck.getCard());
    }
}
