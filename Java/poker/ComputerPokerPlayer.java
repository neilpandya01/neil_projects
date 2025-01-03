import java.util.ArrayList;
public class ComputerPokerPlayer {
    private int money;
    private ArrayList<Card> hand;
    private boolean folded;
    public ComputerPokerPlayer (ArrayList<Card> h) {
        money = 500;
        hand = h;
        folded = false;
    }

    public void addCards (ArrayList<Card> given) {
        for (Card a: given) {
            hand.add(a);
        }
    }

    public int getBet (ArrayList<Card> middleHand, int match) {
        /*
        ArrayList<Card> allCards = new ArrayList();
        for (Card a: hand) {
        allCards.add(a);
        }
        for (Card a: middleHand) {
        allCards.add(a);
        }
         */
        int v = (int) (Math.random()*(money+1));
        if (v >= match) {
            money -= v;
            if (money == 0) {
                folded = true;
            }
            return v;
        }
        
        // If the computer decides to fold:
        folded = true;
        return -1;
    }

    public boolean isFolded () {
        return folded;
    }
    
    public ArrayList<Card> getHand () {
        return hand;
    }
    
    public int getMoney () {
        return money;
    }
    
    public void addMoney (int m) {
        money += m;
    }
    
    /* Check for Hand method:
    return 9 for ROYAL FLUSH
    return 8 for STRAIGHT FLUSH
    return 7 for FOUR OF A KIND
    return 6 for FULL HOUSE
    return 5 for FLUSH
    return 4 for STRAIGHT
    return 3 for THREE OF A KIND
    return 2 for TWO PAIR
    return 1 for PAIR
    return 0 when nothing else works, i.e. HIGH CARD
     */
    
    public int checkForHand () {
        return 8;
    }
    
    // Highest Card method returns the highest card in the hand, returns any integer from 2 to 14
    public int highestCard () {
        int high = -1;
        for (Card a: hand) {
            high = Math.max(high, a.getValue());
        }
        return high;
    }
    
    public void changeHand (ArrayList<Card> h) {
        hand = h;
    }
    
    public void resetFold () {
        folded = false;
    }
}
