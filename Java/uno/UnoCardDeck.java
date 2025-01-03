import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class UnoCardDeck {
    private ArrayList deck = new ArrayList();
    public UnoCardDeck () {
        for (int color=0; color<4; color++) {
            for (int num=0; num<=9; num++) {
                deck.add(makeCard(color, num, true, ""));
            }
        }
        for (int i=0; i<2; i++) {
            for (int color=0; color<4; color++) {
                deck.add(makeCard(color, 10, false, "Reverse"));
            }
            for (int color=0; color<4; color++) {
                deck.add(makeCard(color, 10, false, "Skip"));
            }
        }
    }

    public void shuffleDeck () {
        Random x = new Random();
        for (int i=0; i<deck.size(); i++) {
            Collections.swap(deck, i, x.nextInt(deck.size()));
        }
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
   
    public void giveBackToDeck (String card) {
        deck.add(card);
    }
   
    public ArrayList deck () {
        return deck;
    }
   
    public static String makeCard (int c, int n, boolean isNum, String replace) {
        String colorType = "";
        int num = 0;        
        if (c==0) {
            colorType = "r";
        }
        else if (c==1) {
            colorType = "b";
        }
        else if (c==2) {
            colorType = "y";
        }
        else if (c==3) {
            colorType = "g";
        }
        if (n>=0 && n<=9) {
            num = n;
        }
        else {
            num = -1;
        }
        if(!isNum) {
            return colorType + replace;
        }
        else {
            return colorType + num;
        }
    }
}