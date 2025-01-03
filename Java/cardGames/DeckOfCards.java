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
    private ArrayList deck = new ArrayList();
    public DeckOfCards (int a, int two, int three, int four, int five, int six, int seven, int eight, int nine, int ten, int j, int q, int k) {
        aces = a;
        twos = two;
        threes = three;
        fours = four;
        fives = five;
        sixes = six;
        sevens = seven;
        eights = eight;
        nines = nine;
        tens = ten;
        jacks = j;
        queens = q;
        kings = k;
    }

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
    }

    public void initializeDeck () {
        for (int i=0; i<aces; i++) {
            deck.add("a");
        }
        for (int i=0; i<twos; i++) {
            deck.add("2");
        }
        for (int i=0; i<threes; i++) {
            deck.add("3");
        }
        for (int i=0; i<fours; i++) {
            deck.add("4");
        }
        for (int i=0; i<fives; i++) {
            deck.add("5");
        }
        for (int i=0; i<sixes; i++) {
            deck.add("6");
        }
        for (int i=0; i<sevens; i++) {
            deck.add("7");
        }
        for (int i=0; i<eights; i++) {
            deck.add("8");
        }
        for (int i=0; i<nines; i++) {
            deck.add("9");
        }
        for (int i=0; i<tens; i++) {
            deck.add("10");
        }
        for (int i=0; i<jacks; i++) {
            deck.add("j");
        }
        for (int i=0; i<queens; i++) {
            deck.add("q");
        }
        for (int i=0; i<kings; i++) {
            deck.add("k");
        }
    }

    public void shuffleDeck() {
        Random x = new Random();
        for (int i=0; i<deck.size(); i++) {
            Collections.swap(deck, i, x.nextInt(deck.size()));
        }
    }

    public String pickRandomCard() {
        Random y = new Random();
        return (String) deck.get(y.nextInt(deck.size()));
    }

    public ArrayList dealCards (int cardsPerPlayer) {
        ArrayList hand = new ArrayList();
        for (int i=0; i<cardsPerPlayer; i++) {
            if(!deck.isEmpty()) {
                hand.add(deck.get(0));
                deck.remove(0);
            }
        }
        return hand;
    }

    public ArrayList deck () {
        return deck;
    }

}
