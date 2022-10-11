import java.util.Collections;
import java.util.ArrayList;
import java.util.Random;

public class DeckOfCards {
    private int aces;
    private int twos;
    private int threes;
    private int fours;
    private int fives;
    private int sixes;
    private int sevens;
    private int eights;
    private int nines;
    private int tens;
    private int jacks;
    private int queens;
    private int kings;
    private ArrayList<Card> deck;

    public DeckOfCards () {
        aces = 4;
        twos = 4;
        threes = 4;
        fours = 4;
        fives = 4;
        sixes = 4;
        sevens = 4;
        eights = 4;
        nines = 4;
        tens = 4;
        jacks = 4;
        queens = 4;
        kings = 4;
        deck = new ArrayList();
        for (int i=2; i<=14; i++) {
            deck.add(new Card(i, "Spade"));
            deck.add(new Card(i, "Heart"));
            deck.add(new Card(i, "Diamond"));
            deck.add(new Card(i, "Club"));
        }
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    /*
    public String pickRandomCard() {
    Random y = new Random();
    return (String) deck.get(y.nextInt(deck.size()));
    }
     */

   
    public ArrayList<Card> dealCards (int cardsPerPlayer) {
        ArrayList hand = new ArrayList();
        for (int i=0; i<cardsPerPlayer; i++) {
            if(!deck.isEmpty()) {
                hand.add(deck.get(0));
                deck.remove(0);
            }
        }
        return hand;
    }

    public ArrayList<Card> giveBackToDeck (ArrayList<Card> hand) {
        for (int i=0; i<hand.size(); i++) {
            if(!hand.isEmpty()) {
                deck.add(hand.get(0));
                hand.remove(0);
            }
        }
        return hand; // Hand will always be empty
    }

    public ArrayList<Card> deck () {
        return deck;
    }

}