import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
public class GoFish {
    public static int compScore;
    public static int userScore;

    public static void main (String[] args) {
        intructions();
    }

    public static void intructions () {
        Scanner scan = new Scanner (System.in);
        System.out.println("You are playing Go Fish against the computer. ");
        System.out.println("Here are the rules: When it is your turn, you can type 'queens', 'twos', 'threes', etc in the plural form to ask for a card.");
        System.out.println("You can only ask for a card if it is in your hand. Be truthful!");
        System.out.println("When it is the computer's turn, say 'yes' or 'no' if you have the card they ask for. Please be truthful!");
        System.out.println("Pairs will automatically form if you gain four of a kind. ");
        System.out.println("Ready to begin? Type yes to start.");
        if (scan.nextLine().equals("yes")) {
            game();
        }
    }

    public static void game () {
        Scanner scan = new Scanner (System.in);
        DeckOfCards a = new DeckOfCards ();
        a.initializeDeck();
        a.shuffleDeck();
        ArrayList userHand = a.dealCards(10);
        ArrayList compHand = a.dealCards(10);
        while (!userHand.isEmpty() && !compHand.isEmpty()) {
            System.out.println("It is your turn.");
            System.out.println("Here is your hand: " + userHand);
            //System.out.println("Comp Hand: " + compHand);
            String speech = scan.nextLine();
            if(speech.equals("twos")) {
                if (checkIfIn(compHand, "2")) {
                    System.out.println("The card you are looking for is in the Computer's hand. You will recieve those cards");
                    for (int i=0; i<compHand.size(); i++) {
                        if(compHand.get(i).equals("2")) {
                            userHand.add(compHand.get(i));
                            compHand.remove(i);
                        }
                    }
                }
                else {
                    System.out.println("2 was not found in Computer's hand. Go fish. You recieved a new card");
                    userHand.add(a.dealCards(1).get(0));
                }
            }
            else if(speech.equals("threes")) {
                if (checkIfIn(compHand, "3")) {
                    System.out.println("The card you are looking for is in the Computer's hand. You will recieve those cards");
                    for (int i=0; i<compHand.size(); i++) {
                        if(compHand.get(i).equals("3")) {
                            userHand.add(compHand.get(i));
                            compHand.remove(i);
                        }
                    }
                }
                else {
                    System.out.println("3 was not found in Computer's hand. Go fish. You recieved a new card");
                    userHand.add(a.dealCards(1).get(0));
                }
            }
            else if(speech.equals("fours")) {
                if (checkIfIn(compHand, "4")) {
                    System.out.println("The card you are looking for is in the Computer's hand. You will recieve those cards");
                    for (int i=0; i<compHand.size(); i++) {
                        if(compHand.get(i).equals("4")) {
                            userHand.add(compHand.get(i));
                            compHand.remove(i);
                        }
                    }
                }
                else {
                    System.out.println("4 was not found in Computer's hand. Go fish. You recieved a new card");
                    userHand.add(a.dealCards(1).get(0));
                }
            }
            else if(speech.equals("fives")) {
                if (checkIfIn(compHand, "5")) {
                    System.out.println("The card you are looking for is in the Computer's hand. You will recieve those cards");
                    for (int i=0; i<compHand.size(); i++) {
                        if(compHand.get(i).equals("5")) {
                            userHand.add(compHand.get(i));
                            compHand.remove(i);
                        }
                    }
                }
                else {
                    System.out.println("5 was not found in Computer's hand. Go fish. You recieved a new card");
                    userHand.add(a.dealCards(1).get(0));
                }
            }
            else if(speech.equals("sixes")) {
                if (checkIfIn(compHand, "6")) {
                    System.out.println("The card you are looking for is in the Computer's hand. You will recieve those cards");
                    for (int i=0; i<compHand.size(); i++) {
                        if(compHand.get(i).equals("6")) {
                            userHand.add(compHand.get(i));
                            compHand.remove(i);
                        }
                    }
                }
                else {
                    System.out.println("6 was not found in Computer's hand. Go fish. You recieved a new card");
                    userHand.add(a.dealCards(1).get(0));
                }
            }
            else if(speech.equals("sevens")) {
                if (checkIfIn(compHand, "7")) {
                    System.out.println("The card you are looking for is in the Computer's hand. You will recieve those cards");
                    for (int i=0; i<compHand.size(); i++) {
                        if(compHand.get(i).equals("7")) {
                            userHand.add(compHand.get(i));
                            compHand.remove(i);
                        }
                    }
                }
                else {
                    System.out.println("7 was not found in Computer's hand. Go fish. You recieved a new card");
                    userHand.add(a.dealCards(1).get(0));
                }
            }
            else if(speech.equals("eights")) {
                if (checkIfIn(compHand, "8")) {
                    System.out.println("The card you are looking for is in the Computer's hand. You will recieve those cards");
                    for (int i=0; i<compHand.size(); i++) {
                        if(compHand.get(i).equals("8")) {
                            userHand.add(compHand.get(i));
                            compHand.remove(i);
                        }
                    }
                }
                else {
                    System.out.println("8 was not found in Computer's hand. Go fish. You recieved a new card");
                    userHand.add(a.dealCards(1).get(0));
                }
            }
            else if(speech.equals("nines")) {
                if (checkIfIn(compHand, "9")) {
                    System.out.println("The card you are looking for is in the Computer's hand. You will recieve those cards");
                    for (int i=0; i<compHand.size(); i++) {
                        if(compHand.get(i).equals("9")) {
                            userHand.add(compHand.get(i));
                            compHand.remove(i);
                        }
                    }
                }
                else {
                    System.out.println("9 was not found in Computer's hand. Go fish. You recieved a new card");
                    userHand.add(a.dealCards(1).get(0));
                }
            }
            else if(speech.equals("tens")) {
                if (checkIfIn(compHand, "10")) {
                    System.out.println("The card you are looking for is in the Computer's hand. You will recieve those cards");
                    for (int i=0; i<compHand.size(); i++) {
                        if(compHand.get(i).equals("10")) {
                            userHand.add(compHand.get(i));
                            compHand.remove(i);
                        }
                    }
                }
                else {
                    System.out.println("10 was not found in Computer's hand. Go fish. You recieved a new card");
                    userHand.add(a.dealCards(1).get(0));
                }
            }
            else if(speech.equals("jacks")) {
                if (checkIfIn(compHand, "j")) {
                    System.out.println("The card you are looking for is in the Computer's hand. You will recieve those cards");
                    for (int i=0; i<compHand.size(); i++) {
                        if(compHand.get(i).equals("j")) {
                            userHand.add(compHand.get(i));
                            compHand.remove(i);
                        }
                    }
                }
                else {
                    System.out.println("Jacks were not found in Computer's hand. Go fish. You recieved a new card");
                    userHand.add(a.dealCards(1).get(0));
                }
            }
            else if(speech.equals("queens")) {
                if (checkIfIn(compHand, "q")) {
                    System.out.println("The card you are looking for is in the Computer's hand. You will recieve those cards");
                    for (int i=0; i<compHand.size(); i++) {
                        if(compHand.get(i).equals("q")) {
                            userHand.add(compHand.get(i));
                            compHand.remove(i);
                        }
                    }
                }
                else {
                    System.out.println("Queens were not found in Computer's hand. Go fish. You recieved a new card");
                    userHand.add(a.dealCards(1).get(0));
                }
            }
            else if(speech.equals("kings")) {
                if (checkIfIn(compHand, "k")) {
                    System.out.println("The card you are looking for is in the Computer's hand. You will recieve those cards");
                    for (int i=0; i<compHand.size(); i++) {
                        if(compHand.get(i).equals("k")) {
                            userHand.add(compHand.get(i));
                            compHand.remove(i);
                        }
                    }
                }
                else {
                    System.out.println("Kings were not found in Computer's hand. Go fish. You recieved a new card");
                    userHand.add(a.dealCards(1).get(0));
                }
            }
            else if(speech.equals("aces")) {
                if (checkIfIn(compHand, "a")) {
                    System.out.println("The card you are looking for is in the Computer's hand. You will recieve those cards");
                    for (int i=0; i<compHand.size(); i++) {
                        if(compHand.get(i).equals("a")) {
                            userHand.add(compHand.get(i));
                            compHand.remove(i);
                        }
                    }
                }
                else {
                    System.out.println("Aces were not found in Computer's hand. Go fish. You recieved a new card");
                    userHand.add(a.dealCards(1).get(0));
                }
            }
            checkForUserWin(userHand);
            System.out.println("Score update - computer: " + compScore + " user: " + userScore);
            System.out.println("Here is your new deck: " + userHand);
            //System.out.println("Comp deck: " + compHand);
            System.out.println("It is now the computer's turn.");
            String freq = mostFrequent(compHand);
            System.out.println("Do you have any " + freq + "?");
            if (scan.nextLine().equals("yes")) {
                for (int i=0; i<userHand.size(); i++) {
                    if (userHand.get(i).equals(freq)) {
                        compHand.add(userHand.get(i));
                        userHand.remove(i);
                    }
                }
            }
            else {
                compHand.add(a.dealCards(1).get(0));
            }
            checkForCompWin(compHand);
            System.out.println("Score update - computer: " + compScore + " user: " + userScore);
        }
        
        if(compHand.isEmpty()) {
            for (int i=0; i<a.deck().size(); i++) {
                compHand.add(a.deck().get(i));
            }
        }
        if(userHand.isEmpty()) {
            for (int i=0; i<a.deck().size(); i++) {
                userHand.add(a.deck().get(i));
            }
        }
        while (!userHand.isEmpty() && !compHand.isEmpty()) {
            System.out.println("It is your turn.");
            System.out.println("Here is your hand: " + userHand);
            String speech = scan.nextLine();
            if(speech.equals("twos")) {
                if (checkIfIn(compHand, "2")) {
                    System.out.println("The card you are looking for is in the Computer's hand. You will recieve those cards");
                    for (int i=0; i<compHand.size(); i++) {
                        if(compHand.get(i).equals("2")) {
                            userHand.add(compHand.get(i));
                            compHand.remove(i);
                        }
                    }
                }
                else {
                    System.out.println("2 was not found in Computer's hand. Go fish. You recieved a new card");
                    userHand.add(a.dealCards(1).get(0));
                }
            }
            else if(speech.equals("threes")) {
                if (checkIfIn(compHand, "3")) {
                    System.out.println("The card you are looking for is in the Computer's hand. You will recieve those cards");
                    for (int i=0; i<compHand.size(); i++) {
                        if(compHand.get(i).equals("3")) {
                            userHand.add(compHand.get(i));
                            compHand.remove(i);
                        }
                    }
                }
                else {
                    System.out.println("3 was not found in Computer's hand. Go fish. You recieved a new card");
                    userHand.add(a.dealCards(1).get(0));
                }
            }
            else if(speech.equals("fours")) {
                if (checkIfIn(compHand, "4")) {
                    System.out.println("The card you are looking for is in the Computer's hand. You will recieve those cards");
                    for (int i=0; i<compHand.size(); i++) {
                        if(compHand.get(i).equals("4")) {
                            userHand.add(compHand.get(i));
                            compHand.remove(i);
                        }
                    }
                }
                else {
                    System.out.println("4 was not found in Computer's hand. Go fish. You recieved a new card");
                    userHand.add(a.dealCards(1).get(0));
                }
            }
            else if(speech.equals("fives")) {
                if (checkIfIn(compHand, "5")) {
                    System.out.println("The card you are looking for is in the Computer's hand. You will recieve those cards");
                    for (int i=0; i<compHand.size(); i++) {
                        if(compHand.get(i).equals("5")) {
                            userHand.add(compHand.get(i));
                            compHand.remove(i);
                        }
                    }
                }
                else {
                    System.out.println("5 was not found in Computer's hand. Go fish. You recieved a new card");
                    userHand.add(a.dealCards(1).get(0));
                }
            }
            else if(speech.equals("sixes")) {
                if (checkIfIn(compHand, "6")) {
                    System.out.println("The card you are looking for is in the Computer's hand. You will recieve those cards");
                    for (int i=0; i<compHand.size(); i++) {
                        if(compHand.get(i).equals("6")) {
                            userHand.add(compHand.get(i));
                            compHand.remove(i);
                        }
                    }
                }
                else {
                    System.out.println("6 was not found in Computer's hand. Go fish. You recieved a new card");
                    userHand.add(a.dealCards(1).get(0));
                }
            }
            else if(speech.equals("sevens")) {
                if (checkIfIn(compHand, "7")) {
                    System.out.println("The card you are looking for is in the Computer's hand. You will recieve those cards");
                    for (int i=0; i<compHand.size(); i++) {
                        if(compHand.get(i).equals("7")) {
                            userHand.add(compHand.get(i));
                            compHand.remove(i);
                        }
                    }
                }
                else {
                    System.out.println("7 was not found in Computer's hand. Go fish. You recieved a new card");
                    userHand.add(a.dealCards(1).get(0));
                }
            }
            else if(speech.equals("eights")) {
                if (checkIfIn(compHand, "8")) {
                    System.out.println("The card you are looking for is in the Computer's hand. You will recieve those cards");
                    for (int i=0; i<compHand.size(); i++) {
                        if(compHand.get(i).equals("8")) {
                            userHand.add(compHand.get(i));
                            compHand.remove(i);
                        }
                    }
                }
                else {
                    System.out.println("8 was not found in Computer's hand. Go fish. You recieved a new card");
                    userHand.add(a.dealCards(1).get(0));
                }
            }
            else if(speech.equals("nines")) {
                if (checkIfIn(compHand, "9")) {
                    System.out.println("The card you are looking for is in the Computer's hand. You will recieve those cards");
                    for (int i=0; i<compHand.size(); i++) {
                        if(compHand.get(i).equals("9")) {
                            userHand.add(compHand.get(i));
                            compHand.remove(i);
                        }
                    }
                }
                else {
                    System.out.println("9 was not found in Computer's hand. Go fish. You recieved a new card");
                    userHand.add(a.dealCards(1).get(0));
                }
            }
            else if(speech.equals("tens")) {
                if (checkIfIn(compHand, "10")) {
                    System.out.println("The card you are looking for is in the Computer's hand. You will recieve those cards");
                    for (int i=0; i<compHand.size(); i++) {
                        if(compHand.get(i).equals("10")) {
                            userHand.add(compHand.get(i));
                            compHand.remove(i);
                        }
                    }
                }
                else {
                    System.out.println("10 was not found in Computer's hand. Go fish. You recieved a new card");
                    userHand.add(a.dealCards(1).get(0));
                }
            }
            else if(speech.equals("jacks")) {
                if (checkIfIn(compHand, "j")) {
                    System.out.println("The card you are looking for is in the Computer's hand. You will recieve those cards");
                    for (int i=0; i<compHand.size(); i++) {
                        if(compHand.get(i).equals("j")) {
                            userHand.add(compHand.get(i));
                            compHand.remove(i);
                        }
                    }
                }
                else {
                    System.out.println("Jacks were not found in Computer's hand. Go fish. You recieved a new card");
                    userHand.add(a.dealCards(1).get(0));
                }
            }
            else if(speech.equals("queens")) {
                if (checkIfIn(compHand, "q")) {
                    System.out.println("The card you are looking for is in the Computer's hand. You will recieve those cards");
                    for (int i=0; i<compHand.size(); i++) {
                        if(compHand.get(i).equals("q")) {
                            userHand.add(compHand.get(i));
                            compHand.remove(i);
                        }
                    }
                }
                else {
                    System.out.println("Queens were not found in Computer's hand. Go fish. You recieved a new card");
                    userHand.add(a.dealCards(1).get(0));
                }
            }
            else if(speech.equals("kings")) {
                if (checkIfIn(compHand, "k")) {
                    System.out.println("The card you are looking for is in the Computer's hand. You will recieve those cards");
                    for (int i=0; i<compHand.size(); i++) {
                        if(compHand.get(i).equals("k")) {
                            userHand.add(compHand.get(i));
                            compHand.remove(i);
                        }
                    }
                }
                else {
                    System.out.println("Kings were not found in Computer's hand. Go fish. You recieved a new card");
                    userHand.add(a.dealCards(1).get(0));
                }
            }
            else if(speech.equals("aces")) {
                if (checkIfIn(compHand, "a")) {
                    System.out.println("The card you are looking for is in the Computer's hand. You will recieve those cards");
                    for (int i=0; i<compHand.size(); i++) {
                        if(compHand.get(i).equals("a")) {
                            userHand.add(compHand.get(i));
                            compHand.remove(i);
                        }
                    }
                }
                else {
                    System.out.println("Aces were not found in Computer's hand. Go fish. You recieved a new card");
                    userHand.add(a.dealCards(1).get(0));
                }
            }
            checkForUserWin(userHand);
            System.out.println("Score update - computer: " + compScore + " user: " + userScore);
            System.out.println("Here is your new deck: " + userHand);
            System.out.println("It is now the computer's turn.");
            String freq = mostFrequent(compHand);
            System.out.println("Do you have any " + freq + "?");
            if (scan.nextLine().equals("yes")) {
                for (int i=0; i<userHand.size(); i++) {
                    if (userHand.get(i).equals(freq)) {
                        compHand.add(userHand.get(i));
                        userHand.remove(i);
                    }
                }
            }
            else {
                compHand.add(a.dealCards(1).get(0));
            }
            checkForCompWin(compHand);
            System.out.println("Score update - computer: " + compScore + " user: " + userScore);
        }
        System.out.println("Game is over. Final score computer: " + compScore + " user: " + userScore);
    }

    public static boolean checkIfIn (ArrayList x, String identify) {
        for (int i=0; i<x.size(); i++) {
            if (x.get(i).equals(identify)) {
                return true;
            }
        }
        return false;
    }

    public static String mostFrequent (ArrayList y) {
        int numa = Collections.frequency(y, "a");
        int numtwo = Collections.frequency(y, "2");
        int numthree = Collections.frequency(y, "3");
        int numfour = Collections.frequency(y, "4");
        int numfive = Collections.frequency(y, "5");
        int numsix = Collections.frequency(y, "6");
        int numseven = Collections.frequency(y, "7");
        int numeight = Collections.frequency(y, "8");
        int numnine = Collections.frequency(y, "9");
        int numten = Collections.frequency(y, "10");
        int numj = Collections.frequency(y, "j");
        int numq = Collections.frequency(y, "q");
        int numk = Collections.frequency(y, "k");
        int[] sort = {numa, numtwo, numthree, numfour, numfive, numsix, numseven, numeight, numnine, numten, numj, numq, numk};
        Arrays.sort(sort);
        if(sort[sort.length-1] == numa) {
            return "a";
        }
        if(sort[sort.length-1] == numtwo) {
            return "2";
        }
        if(sort[sort.length-1] == numthree) {
            return "3";
        }
        if(sort[sort.length-1] == numfour) {
            return "4";
        }
        if(sort[sort.length-1] == numfive) {
            return "5";
        }
        if(sort[sort.length-1] == numsix) {
            return "6";
        }
        if(sort[sort.length-1] == numseven) {
            return "7";
        }
        if(sort[sort.length-1] == numeight) {
            return "8";
        }
        if(sort[sort.length-1] == numnine) {
            return "9";
        }
        if(sort[sort.length-1] == numten) {
            return "10";
        }
        if(sort[sort.length-1] == numj) {
            return "j";
        }
        if(sort[sort.length-1] == numq) {
            return "q";
        }
        if(sort[sort.length-1] == numk) {
            return "k";
        }
        return "k";
    }

    public static void checkForUserWin (ArrayList y) {
        int numa = Collections.frequency(y, "a");
        int numtwo = Collections.frequency(y, "2");
        int numthree = Collections.frequency(y, "3");
        int numfour = Collections.frequency(y, "4");
        int numfive = Collections.frequency(y, "5");
        int numsix = Collections.frequency(y, "6");
        int numseven = Collections.frequency(y, "7");
        int numeight = Collections.frequency(y, "8");
        int numnine = Collections.frequency(y, "9");
        int numten = Collections.frequency(y, "10");
        int numj = Collections.frequency(y, "j");
        int numq = Collections.frequency(y, "q");
        int numk = Collections.frequency(y, "k");
        if(Collections.frequency(y, "a") == 4) {
            y.removeAll(Collections.singleton("a"));
            userScore++;
        }
        if(Collections.frequency(y, "2") == 4) {
            y.removeAll(Collections.singleton("2"));
            userScore++;
        }
        if(Collections.frequency(y, "3") == 4) {
            y.removeAll(Collections.singleton("3"));
            userScore++;
        }
        if(Collections.frequency(y, "4") == 4) {
            y.removeAll(Collections.singleton("4"));
            userScore++;
        }
        if(Collections.frequency(y, "5") == 4) {
            y.removeAll(Collections.singleton("5"));
            userScore++;
        }
        if(Collections.frequency(y, "6") == 4) {
            y.removeAll(Collections.singleton("6"));
            userScore++;
        }
        if(Collections.frequency(y, "7") == 4) {
            y.removeAll(Collections.singleton("7"));
            userScore++;
        }
        if(Collections.frequency(y, "8") == 4) {
            y.removeAll(Collections.singleton("8"));
            userScore++;
        }
        if(Collections.frequency(y, "9") == 4) {
            y.removeAll(Collections.singleton("9"));
            userScore++;
        }
        if(Collections.frequency(y, "10") == 4) {
            y.removeAll(Collections.singleton("10"));
            userScore++;
        }
        if(Collections.frequency(y, "j") == 4) {
            y.removeAll(Collections.singleton("j"));
            userScore++;
        }
        if(Collections.frequency(y, "q") == 4) {
            y.removeAll(Collections.singleton("q"));
            userScore++;
        }
        if(Collections.frequency(y, "k") == 4) {
            y.removeAll(Collections.singleton("k"));
            userScore++;
        }
    }

    public static void checkForCompWin (ArrayList y) {
        int numa = Collections.frequency(y, "a");
        int numtwo = Collections.frequency(y, "2");
        int numthree = Collections.frequency(y, "3");
        int numfour = Collections.frequency(y, "4");
        int numfive = Collections.frequency(y, "5");
        int numsix = Collections.frequency(y, "6");
        int numseven = Collections.frequency(y, "7");
        int numeight = Collections.frequency(y, "8");
        int numnine = Collections.frequency(y, "9");
        int numten = Collections.frequency(y, "10");
        int numj = Collections.frequency(y, "j");
        int numq = Collections.frequency(y, "q");
        int numk = Collections.frequency(y, "k");
        if(Collections.frequency(y, "a") == 4) {
            y.removeAll(Collections.singleton("a"));
            compScore++;
        }
        if(Collections.frequency(y, "2") == 4) {
            y.removeAll(Collections.singleton("2"));
            compScore++;
        }
        if(Collections.frequency(y, "3") == 4) {
            y.removeAll(Collections.singleton("3"));
            compScore++;
        }
        if(Collections.frequency(y, "4") == 4) {
            y.removeAll(Collections.singleton("4"));
            compScore++;
        }
        if(Collections.frequency(y, "5") == 4) {
            y.removeAll(Collections.singleton("5"));
            compScore++;
        }
        if(Collections.frequency(y, "6") == 4) {
            y.removeAll(Collections.singleton("6"));
            compScore++;
        }
        if(Collections.frequency(y, "7") == 4) {
            y.removeAll(Collections.singleton("7"));
            compScore++;
        }
        if(Collections.frequency(y, "8") == 4) {
            y.removeAll(Collections.singleton("8"));
            compScore++;
        }
        if(Collections.frequency(y, "9") == 4) {
            y.removeAll(Collections.singleton("9"));
            compScore++;
        }
        if(Collections.frequency(y, "10") == 4) {
            y.removeAll(Collections.singleton("10"));
            compScore++;
        }
        if(Collections.frequency(y, "j") == 4) {
            y.removeAll(Collections.singleton("j"));
            compScore++;
        }
        if(Collections.frequency(y, "q") == 4) {
            y.removeAll(Collections.singleton("q"));
            compScore++;
        }
        if(Collections.frequency(y, "k") == 4) {
            y.removeAll(Collections.singleton("k"));
            compScore++;
        }
    }
}