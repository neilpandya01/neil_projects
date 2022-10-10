import java.util.Random;
import java.util.Scanner;

public class CastleGame {
    public static void main (String[] args) throws InterruptedException {
        game();
    }

    public static void game () throws InterruptedException {
        Scanner scan = new Scanner (System.in);
        System.out.println("Welcome to the Castle Game. ");
        System.out.println("Rules: whichever person deploys the most troops at a castle wins that castle. A castle's points range from 0 to the number of castles.");
        System.out.println("This version will be played user vs user. ");
        System.out.println("The computer will momentarily choose the number of castles (5-10) to attack and number of troops (50-100) for you to use for attack.");
        Thread.sleep(5000);
        int castles = pickRandom (5, 10);
        int troops = pickRandom (50, 100);
        System.out.println("The game will be played with " + castles + " castles and " + troops + " troops.");
        Thread.sleep(1000);
        System.out.println("We will start with player 1. Player 2, don't look. Player 1, take as much time as you need to answer the questions.");
        int[] playerOne = new int[castles];
        int[] playerTwo = new int[castles];
        while (true) {
            for (int i=castles; i>0; i--) {
                System.out.println("Player 1: Choose the number of troops you want to deploy to castle " + i);
                playerOne[i-1] = scan.nextInt();
            }
            int sum = 0;
            for (int a: playerOne) {
                sum += a;
            }
            if (sum == troops) {
                break;
            }
            else {
                System.out.println("Unfortunately, the number of troops you deployed does not add up to the given number of troops (" + troops + "). You will have to reenter your data.");
            }
        }
        System.out.println("Player 1's troops have sucessfully been deployed. Player 1, go get player 2 now. It is their turn. Player 1: Don't look!");
        while (true) {
            for (int i=castles; i>0; i--) {
                System.out.println("Player 2: Choose the number of troops you want to deploy to castle " + i);
                playerTwo[i-1] = scan.nextInt();
            }
            int sum = 0;
            for (int a: playerTwo) {
                sum += a;
            }
            if (sum == troops) {
                break;
            }
            else {
                System.out.println("Unfortunately, the number of troops you deployed does not add up to the given number of troops (" + troops + "). You will have to reenter your data.");
            }
        }
        System.out.println("Player 2's troops have sucessfully been deployed. Call player 1 back!");
        System.out.println("The computer will now go through the data and let you know who won!");
        Thread.sleep(1000);
        int user1Score = 0;
        int user2Score = 0;
        for (int i=0; i<castles; i++) {
            if (playerOne[i] > playerTwo[i]) {
                System.out.println("For castle " + (i+1) + ", player 1 won and earned " + (i+1) + " points.");
                user1Score += (i+1);
            }
            else if (playerTwo[i] > playerOne[i]) {
                System.out.println("For castle " + (i+1) + ", player 2 won and earned " + (i+1) + " points.");
                user2Score += (i+1);
            }
            else  {
                System.out.println("For castle " + (i+1) + "equal number of troops were deployed and so no points are gained.");
            }
            Thread.sleep(1000);
        }
        System.out.println("The final score is Player 1: " + user1Score + " and Player 2: " + user2Score);
        if (user1Score > user2Score) {
            System.out.println("Congrats to player 1!");
        }
        else if (user2Score > user1Score) {
            System.out.println("Congrats to player 2!");
        }
        else {
            System.out.println("Wow. That was a draw between both players.");
        }
        System.out.println("Type 'play' to play again.");
        if (scan.nextLine().equals("play")) {
            game();
        }
    }
    public static int pickRandom (int min, int max) {
        Random r = new Random();
        while (true) {
            int a = r.nextInt(max);
            if (a >= min) {
                return a;
            }            
        }
    }
}