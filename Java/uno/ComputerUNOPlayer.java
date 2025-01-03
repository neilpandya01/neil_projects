import java.util.ArrayList;

public class ComputerUNOPlayer {
    private String playerName;
    private ArrayList myHand;
    public ComputerUNOPlayer (String n, ArrayList h) {
        playerName = n;
        myHand = h;
    }
    public String takeTurn (String currentCard) {
        String p = "";
        for (int i=0; i<myHand.size(); i++) {
            p = (String) myHand.get(i);
            if (p.substring(0,1).equalsIgnoreCase(currentCard.substring(0,1))) {
                myHand.remove(myHand.indexOf(p));
                return p;
            }
            else if (p.substring(1).equalsIgnoreCase(currentCard.substring(1))) {
                myHand.remove(myHand.indexOf(p));
                return p;
            }
        }
        return "need a card";
    }
    public ArrayList hand () {
        return myHand;
    }
    public String name () {
        return playerName;
    }
    public void addCard (String card) {
        myHand.add(card);
    }
    public void removeCard (String card) {
        myHand.remove(myHand.indexOf(card));
    }
}