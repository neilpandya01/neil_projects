import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Thread;
public class Uno1v1 {
    public static UnoCardDeck e = new UnoCardDeck ();
    public static ArrayList userHand;
    public static ArrayList compHand;
    public static ArrayList c;
    public static String currentCard;
    public static Scanner scan = new Scanner (System.in);
    public static boolean gameOver = false;
    public static boolean repeat = false;
    public static String lastCardPlayed;
    public static void main (String [] args) throws InterruptedException {
        System.out.println("You are about to play UNO. Here are the rules: ");
        System.out.println("You will play 1v1 against the computer.");
        System.out.println("You will be drawn 7 cards to start out with.");
        System.out.println("When it is your turn, you need to match the current card by its color or number.");
        System.out.println("To place one of your cards when it is your turn, use the format: [first letter of colorType + number OR cardType]");
        System.out.println("For example, if I wanted to put a red five, I would type 'r5' and if I wanted to put a blue reverse card I would type 'bReverse'");
        System.out.println("If you don't have a card in your hand that fits the criteria, say 'need a card' to get a new card.");
        System.out.println("There are no wildcards in this version. Only reverse and skip cards.");
        System.out.println("Ready to play? Type 'yes' to begin. Type 'quit' at anytime to stop.");
        if (scan.nextLine().equalsIgnoreCase("yes")) {
            e.shuffleDeck();
            userHand = e.dealCards(7);
            compHand = e.dealCards(7);
            c = e.dealCards(1);
            currentCard = (String) c.get(0);
            userTurn();
        }
    }

    public static void userTurn () throws InterruptedException {
        System.out.println("Current card: " + currentCard);
        System.out.println("Your hand: " + userHand);
        //System.out.println("Comp hand: " + compHand);
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
                            userTurn();
                        }
                    }
                    else if (i == userHand.size()-1) {
                        System.out.println("The card you inputted is not in your deck. Try typing a new card or type 'need a card'.");
                        Thread.sleep(1000);
                        userTurn();
                    }
                }
            }
        }
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
            if (currentCard.contains("Skip") || currentCard.contains("Reverse")) {
                if (lastCardPlayed.equals(currentCard)) {
                    userTurn();
                }
                else {
                    compTurn();
                }
            }
            else {
                compTurn();
            }
        }
    }

    public static void compTurn () throws InterruptedException {
        System.out.println("It is now the computer's turn.");
        //System.out.println("Comp hand: " + compHand);
        System.out.println("Computer is thinking...");
        Thread.sleep(5000);
        String p = "";
        for (int i=0; i<compHand.size(); i++) {
            p = (String) compHand.get(i);
            if (p.substring(0,1).equalsIgnoreCase(currentCard.substring(0,1))) {
                currentCard = p;
                compHand.remove(i);
                System.out.println("The computer played " + currentCard + " and now has " + compHand.size() + " cards left.");
                e.giveBackToDeck(p);
                lastCardPlayed = currentCard;
                break;
            }
            else if (p.substring(1).equalsIgnoreCase(currentCard.substring(1))) {
                currentCard = p;
                compHand.remove(i);
                System.out.println("The computer played " + currentCard + " and now has " + compHand.size() + " cards left.");
                e.giveBackToDeck(p);
                lastCardPlayed = currentCard;
                break;
            }
            else if (i == compHand.size()-1) {
                c = e.dealCards(1);
                compHand.add((String) (c.get(0)));
                System.out.println("The computer did not have a match and recieved a new card. Now has " + compHand.size() + " cards left.");
                lastCardPlayed = "";
                break;
            }
        }
        if (compHand.size() <= 0) {
            gameOver = true;
        }
        if (gameOver) {
            System.out.println("The computer won. The game is over.");
            System.exit(0);
        }
        else {
            if (currentCard.contains("Skip") || currentCard.contains("Reverse")) {
                if (lastCardPlayed.equals(currentCard)) {
                    compTurn();
                }
                else {
                    userTurn();
                }
            }
            else {
                userTurn();
            }
        }
    }
}