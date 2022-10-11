public class Card {
    private int val;
    private String name;
    private String suit;
   
    // Pre Condition : 2 <= v <= 14
    public Card (int v, String s) {
        val = v;
        if (v == 11) {
            name = "j";
        }
        else if (v == 12) {
            name = "q";
        }
        else if (v == 13) {
            name = "k";
        }
        else if (v == 14) {
            name = "a";
        }
        else {
            name = "" + v;
        }
        suit = s;
    }
    public String getCard () {
        return name + suit;
    }
    public int getValue () {
        return val;
    }
    public String getSuit () {
        return suit;
    }
}
