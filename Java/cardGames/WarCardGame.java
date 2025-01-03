import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Thread;

public class WarCardGame {
    public static void main (String [] args) throws InterruptedException {
        Scanner scan = new Scanner (System.in);
        System.out.println("You are playing War, the card game. Type play to start the game.");
        if (scan.nextLine().equals("play")) {
            DeckOfCards a = new DeckOfCards ();
            a.initializeDeck();
            a.shuffleDeck();
            ArrayList compHand = a.dealCards(26);
            ArrayList userHand = a.dealCards(26);
            int compScore = 0;
            int userScore = 0;
            ArrayList toIntCompHand = new ArrayList();
            ArrayList toIntUserHand = new ArrayList();
            for (int i=0; i<compHand.size(); i++) {
                if (compHand.get(i).equals("j")) {
                    toIntCompHand.add(11);
                }
                else if (compHand.get(i).equals("q")) {
                    toIntCompHand.add(12);
                }
                else if (compHand.get(i).equals("k")) {
                    toIntCompHand.add(13);
                }
                else if (compHand.get(i).equals("a")) {
                    toIntCompHand.add(14);
                }
                else {
                    toIntCompHand.add(new Integer((String)compHand.get(i)).intValue());
                }
            }
            for (int i=0; i<userHand.size(); i++) {
                if (userHand.get(i).equals("j")) {
                    toIntUserHand.add(11);
                }
                else if (userHand.get(i).equals("q")) {
                    toIntUserHand.add(12);
                }
                else if (userHand.get(i).equals("k")) {
                    toIntUserHand.add(13);
                }
                else if (userHand.get(i).equals("a")) {
                    toIntUserHand.add(14);
                }
                else {
                    toIntUserHand.add(new Integer((String)userHand.get(i)).intValue());
                }
            }
            int round = 1;
            while (!compHand.isEmpty() && !userHand.isEmpty()) {
                System.out.println("We will now play Round " + round + ". The current score is computer: " + compScore + " and user: " + userScore + ". Type play to start the round");
                if(scan.nextLine().equals("play")) {
                    System.out.println("Computer played " + compHand.get(0));
                    Thread.sleep(1000);
                    System.out.println("You played " + userHand.get(0));
                    Thread.sleep(1000);
                    if((int) toIntUserHand.get(0) > (int) toIntCompHand.get(0)) {
                        System.out.println("You won this round!");
                        userScore++;
                        userHand.add(compHand.get(0));
                        userHand.add(userHand.get(0));
                        userHand.remove(0);
                        compHand.remove(0);
                        toIntUserHand.add(toIntCompHand.get(0));
                        toIntUserHand.add(toIntUserHand.get(0));
                        toIntUserHand.remove(0);
                        toIntCompHand.remove(0);
                    }
                    else if((int) toIntUserHand.get(0) < (int) toIntCompHand.get(0)) {
                        System.out.println("You lost this round!");
                        compScore++;
                        compHand.add(compHand.get(0));
                        compHand.add(userHand.get(0));
                        compHand.remove(0);
                        userHand.remove(0);
                        toIntCompHand.add(toIntCompHand.get(0));
                        toIntCompHand.add(toIntUserHand.get(0));
                        toIntCompHand.remove(0);
                        toIntUserHand.remove(0);
                    }
                    else {
                        System.out.println("This round is a draw");
                        userHand.add(userHand.get(0));
                        userHand.remove(0);
                        toIntUserHand.add(toIntUserHand.get(0));
                        toIntUserHand.remove(0);
                        compHand.add(compHand.get(0));
                        compHand.remove(0);
                        toIntCompHand.add(toIntCompHand.get(0));
                        toIntCompHand.remove(0);
                    }
                    round++;
                }
                else {
                    break;
                }
            }
            System.out.println("The game is over. The final score is computer: " + compScore + " and user: " + userScore);
            System.out.println("Computer's hand is: " + compHand);
            System.out.println("Your hand is: " + userHand);
        }
    }
}