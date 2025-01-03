import java.util.ArrayList;
import java.util.Scanner;

public class PokerGame {
    public static int bet = 0;
    public static int betToMatch = 0;
    public static int dollars = 500;
    public static DeckOfCards a = new DeckOfCards();
    public static int numComp;
    public static ArrayList<ComputerPokerPlayer> comps = new ArrayList();
    public static Scanner scan = new Scanner (System.in);
    public static int poolMoney = 0;
    public static ArrayList<Card> middleCards = new ArrayList();
    public static ArrayList<Card> myHand;
    public static boolean folded = false;

    public static void main (String[] args) throws Exception {
        intro();
    }

    public static void intro () throws Exception {
        System.out.println("Welcome to Zynga Poker! You will play against a certain number of computer players.");
        System.out.println("Input the number of computer players you would like to play against. This game is user v. computers. The minimum number of computer players needed is 4. A dealer is automated.");
        System.out.println("$500 is the starting amount for each computer player and for you. Follow the classic rules from the zynga poker app.");
        System.out.println("When asked how much money you would like to bet, give a INTEGER amount. Do NOT say 'all in' or 'half in'. Type '0' for no bet or fold.");
        a.shuffleDeck();
        while (true) {
            try {
                scan = new Scanner(System.in);
                System.out.println("How many computer players? Value must be greater than 4.");
                numComp = scan.nextInt();
                if (numComp < 4) {
                    System.out.println("Invalid response. Try again.");
                    continue;
                }
                a.shuffleDeck();
                myHand = a.dealCards(2);
                for (int i=0; i<numComp; i++) {
                    comps.add(new ComputerPokerPlayer(a.dealCards(2)));
                }
                System.out.println("A new round will begin shortly.");
                Thread.sleep(1000);
                round();
            }
            catch (Exception e) {
                //e.printStackTrace();
                System.out.println("Invalid response. Try again.");
            }
        }
    }

    public static void round () throws Exception {
        for (int i=1; i<=4; i++) {
            if (!folded) {
                userTurn(i);
            }
            computerTurn();
            if (i==1) {
                ArrayList<Card> temp = a.dealCards(3);
                for (Card b: temp) {
                    middleCards.add(b);
                }
            }
            else if (i==2 || i==3) {
                middleCards.add(a.dealCards(1).get(0));
            }
        }
        calculateWinnerAndAward();
        resetRound();
        System.out.println("Play again? 'y' or 'n'");
        String inp = scan.nextLine();
        if (inp.equals("y")) {
            round();
        }
        else {
            System.out.println("Bye bye");
        }
    }

    public static void printHand (ArrayList<Card> c) {
        System.out.print("{");
        for (int i=0; i<c.size(); i++) {
            if (i == c.size()-1) {
                System.out.print(c.get(i).getCard());
            }
            else {
                System.out.print(c.get(i).getCard() + ", ");
            }
        }
        System.out.print("}");
        System.out.println();
    }

    public static void userTurn (int r) {
        while (true) {
            System.out.println("Shared Cards in the middle: ");
            printHand(middleCards);
            System.out.println("Your hand: ");
            printHand(myHand);
            System.out.println("Your turn. Betting round: " + r + ". Place your input. Money must be higher than or match: " + betToMatch + " or else you will fold. You have " + dollars + " dollars.");
            bet = scan.nextInt();
            if (bet >= betToMatch && bet <= dollars) {
                System.out.println("You are placing " + bet + " dollars.");
                dollars -= bet;
                poolMoney += bet;
                betToMatch = bet;
                break;
            }
            else {
                System.out.println("To confirm, do you want to fold from the game? 'y' or 'n'");
                scan = new Scanner(System.in);
                String x = scan.nextLine();
                if (x.equals("y")) {
                    folded = true;
                    break;
                }
                else {
                    continue;
                }
            }
        }
    }
    
    public static void computerTurn () throws Exception {
        for (ComputerPokerPlayer player: comps) {
            if (!player.isFolded()) {
                int b = player.getBet(middleCards, betToMatch);
                if (b > 0) {
                    System.out.println("Computer Player " + (comps.indexOf(player)+1) + " is betting " + b + " dollars and now has " + player.getMoney() + " dollars left.");
                    poolMoney += b;
                    betToMatch = b;
                }
                else {
                    System.out.println("Computer Player " + (comps.indexOf(player)+1) + " is folding from the round and still has " + player.getMoney() + " dollars remaining.");
                }
                Thread.sleep(1000);
            }
        }
    }
    
    public static void calculateWinnerAndAward () {
        ArrayList<Integer> highComps = new ArrayList();
        for (ComputerPokerPlayer player: comps) {
            highComps.add(player.checkForHand());
        }
        
        // CHECK FOR USER'S HIGHEST HAND HERE AND INPUT TO USERHIGH
        int userHigh = 9;
        
        int high = -1;
        for (int c: highComps) {
            high = Math.max(high, c);
        }
        high = Math.max(high, userHigh);
        ArrayList<ComputerPokerPlayer> winners = new ArrayList();
        for (ComputerPokerPlayer player: comps) {
            if (player.checkForHand() == high) {
                winners.add(player);
            }
        }
        int totalWinners = 0;
        boolean userWon = false;
        if (userHigh == high) {
            userWon = true;
            totalWinners = 1 + winners.size();
        }
        else {
            totalWinners = winners.size();
        }
        int moneyPerWinner = poolMoney / totalWinners;
        for (ComputerPokerPlayer player: winners) {
            System.out.println("Computer Player " + (comps.indexOf(player)+1) + " has been awarded " + moneyPerWinner);
            player.addMoney(moneyPerWinner);
        }
        if (userWon) {
            System.out.println("You will gain " + moneyPerWinner + " dollars since you won!");
            dollars += moneyPerWinner;
            System.out.println("You now have " + dollars + " dollars.");
        }
    }

    public static void resetRound () {
        betToMatch = 0;
        middleCards = a.giveBackToDeck(middleCards);
        myHand = a.giveBackToDeck(myHand);
        for (ComputerPokerPlayer player: comps) {
            a.giveBackToDeck(player.getHand());
        }
        a.shuffleDeck();
        for (ComputerPokerPlayer player: comps) {
            player.changeHand(a.dealCards(2));
            player.resetFold();
        }
        folded = false;
        myHand = a.dealCards(2);
        bet = 0;
        poolMoney = 0;
    }
}