import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Thread;
import java.util.Collections;

public class Uno2PlusPlayers {
    public static UnoCardDeck e = new UnoCardDeck();
    public static ArrayList userHand;
    public static ArrayList players = new ArrayList();
    public static ArrayList c;
    public static String currentCard;
    public static boolean gameOver = false;
    public static boolean repeat = false;
    public static String lastCardPlayed = "";
    public static Scanner scan = new Scanner(System.in);
    public static void main (String [] args) throws InterruptedException {
        System.out.println("You are about to play UNO against a certain number of computer players. Here are the rules: ");
        System.out.println("You and the other computer players will be drawn 7 cards to start out with.");
        System.out.println("When it is your turn, you need to match the current card by its color or number.");
        System.out.println("To place one of your cards when it is your turn, use the format: [first letter of colorType + number OR cardType]");
        System.out.println("For example, if I wanted to put a red five, I would type 'r5' and if I wanted to put a blue reverse card I would type 'bReverse'");
        System.out.println("If you don't have a card in your hand that fits the criteria, say 'need a card' to get a new card.");
        System.out.println("There are no wildcards in this version. Only reverse and skip cards.");
        int pyrs = getInt();
        scan.nextLine(); // to read the "new line"/<enter> character
        players.add("user");
        e.shuffleDeck();
        //System.out.println(e.deck().size());
        for (int i=1; i<=pyrs; i++) {
            System.out.println("Type a name for player " + i);
            players.add(new ComputerUNOPlayer(scan.nextLine(), e.dealCards(7)));
        }
        //System.out.println("Player named " + ((ComputerUNOPlayer) players.get(1)).name() + " has the following deck: " + ((ComputerUNOPlayer) players.get(1)).hand());
        //System.exit(0);

        System.out.println("Ready to play? Type 'yes' to begin!");
        if (!scan.nextLine().equalsIgnoreCase("yes")) {
            System.exit(0);
        }
        userHand = e.dealCards(7);
        c = e.dealCards(1);
        currentCard = (String) c.get(0);
        while (true) {
            for (int p=0; p<players.size(); p++) {
                if (players.get(p).equals("user")) {
                    System.out.println("Current card: " + currentCard);
                    System.out.println("Your hand: " + userHand);
                    System.out.println("It is your turn.");
                    String userInp = scan.nextLine();
                    String f = "";
                    if (!userInp.equalsIgnoreCase("need a card")) {
                        if (!userInp.equalsIgnoreCase("quit")) {
                            for (int i=0; i<userHand.size(); i++) {
                                if (userHand.get(i).equals(userInp)) {
                                    if (userInp.substring(0,1).equals(currentCard.substring(0,1)) || userInp.substring(1).equals(currentCard.substring(1))) {
                                        break;
                                    }
                                    else {
                                        System.out.println("The card you inputted is not playable. Try typing a new card or type 'need a card'.");
                                        Thread.sleep(1000);
                                        p--;
                                        break;
                                    }
                                }
                                else if (i == userHand.size()-1) {
                                    System.out.println("The card you inputted is not in your deck. Try typing a new card or type 'need a card'.");
                                    Thread.sleep(1000);
                                    p--;
                                    break;
                                }
                            }
                        }
                    }
                    if (p>=0) {
                        if (players.get(p).equals("user")) {
                            if (userInp.substring(0,1).equalsIgnoreCase(currentCard.substring(0,1))) {
                                //System.out.println("Your card is playable since it has the same color as the current card.");
                                System.out.println(userInp + " will be placed as the current card.");
                                userHand.remove(userHand.indexOf(userInp));
                                e.giveBackToDeck(userInp);
                                currentCard = userInp;
                                lastCardPlayed = currentCard;
                            }
                            else if (userInp.substring(1).equalsIgnoreCase(currentCard.substring(1))) {
                                //System.out.println("Your card is playable since it has the same number or type as the current card.");
                                System.out.println(userInp + " will be placed as the current card.");
                                userHand.remove(userHand.indexOf(userInp));
                                e.giveBackToDeck(userInp);
                                currentCard = userInp;
                                lastCardPlayed = currentCard;
                            }
                            else if (userInp.equalsIgnoreCase("need a card")) {
                                c = e.dealCards(1);
                                userHand.add((String) (c.get(0)));
                                System.out.println("A card has been added to your hand.");
                                lastCardPlayed = "";
                            }
                            else {
                                gameOver = true;
                            }
                            if (userHand.size() == 0) {
                                System.out.println("YOU WON THE GAME!!!");
                                gameOver = true;
                            }
                            if (gameOver) {
                                System.out.println("The game is over.");
                                System.exit(0);
                            }
                            else {
                                if (currentCard.contains("Skip") && lastCardPlayed.equals(currentCard)) {
                                    p++;
                                    if (p == players.size()) {
                                        p=0;
                                    }
                                }
                                if (currentCard.contains("Reverse") && lastCardPlayed.equals(currentCard)) {
                                    Collections.reverse(players);
                                    for (int i=0; i<players.size(); i++) {
                                        if (!(players.get(i) instanceof ComputerUNOPlayer)) {
                                            p = i;
                                            if (p == players.size()-1) {
                                                p = -1;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else {
                    System.out.println("It is now " + ((ComputerUNOPlayer) players.get(p)).name() + "'s turn.");
                    //System.out.println("Hand: " + ((ComputerUNOPlayer) players.get(p)).hand());
                    System.out.println(((ComputerUNOPlayer) players.get(p)).name() + " is thinking...");
                    Thread.sleep(5000);
                    String compInp = ((ComputerUNOPlayer) players.get(p)).takeTurn(currentCard);
                    String f = "";
                    if (compInp.equalsIgnoreCase("need a card")) {
                        c = e.dealCards(1);
                        ((ComputerUNOPlayer) players.get(p)).addCard((String) c.get(0));
                        System.out.println(((ComputerUNOPlayer) players.get(p)).name() + " did not have a match and recieved a new card. Now has " + ((ComputerUNOPlayer) players.get(p)).hand().size() + " cards left.");
                        lastCardPlayed = "";
                    }
                    else {
                        currentCard = compInp;
                        lastCardPlayed = currentCard;
                        System.out.println(((ComputerUNOPlayer) players.get(p)).name() + " played " + currentCard + " and now has " + ((ComputerUNOPlayer) players.get(p)).hand().size() + " cards left.");
                        e.giveBackToDeck(compInp);
                    }
                    if (((ComputerUNOPlayer) players.get(p)).hand().size() <= 0) {
                        gameOver = true;
                    }
                    if (gameOver) {
                        System.out.println(((ComputerUNOPlayer) players.get(p)).name() + " won. The game is over.");
                        System.exit(0);
                    }
                    else {
                        if (currentCard.contains("Skip") && lastCardPlayed.equals(currentCard)) {
                            p++;
                            if (p == players.size()) {
                                p=0;
                            }
                        }
                        if (currentCard.contains("Reverse") && lastCardPlayed.equals(currentCard)) {
                            String n = ((ComputerUNOPlayer) players.get(p)).name();
                            Collections.reverse(players);
                            for (int i=0; i<players.size(); i++) {
                                if (players.get(i) instanceof ComputerUNOPlayer) {
                                    if (n.equals(((ComputerUNOPlayer) players.get(i)).name())) {
                                        p = i;
                                        if (p == players.size()-1) {
                                            p = -1;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static int getInt() {
        int t;
        while (true) {
            System.out.println("Type the number of players you would like to play against. Has to be between 1 and 7.");
            t = scan.nextInt();
            if (t>0 && t<8) {
                return t;
            }
            else {
                System.out.println("That number is not valid.");
            }
        }
    }
}
