import java.util.Scanner;
import java.util.Random;
public class GuessingGame {
    public static int number;
    public static int currentguess;
    public static int numberguesses;
    public static void main (String [] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("I'm going to think of a number between 1 and 100. Every time you guess a number I will let you know if your guess is too high or too low. Ready to play? Type yes to begin.");
        if(scan.nextLine().equals("yes")) {
            Random r = new Random();
            number = r.nextInt(100);
            System.out.println("I thought of a number. Try and guess it!");
            numberguesses = 0;
            calculate();
        } 
    }

    public static void calculate () {
        Scanner scan = new Scanner(System.in);
        currentguess = scan.nextInt();
        if(currentguess > number) {
            System.out.println("That's too high! Guess again.");
            numberguesses = (numberguesses + 1);
            calculate();
        }
        if(currentguess < number) {
            System.out.println("That's too low! Guess again.");
            numberguesses = (numberguesses + 1);
            calculate();
        }
        if(currentguess == number) {
            numberguesses = (numberguesses + 1);
            afterguessed ();
        }
    } 
    
    public static void afterguessed () {
        Scanner scan = new Scanner(System.in);
        System.out.println("You guessed it! My number was " + number + ". It took you " + numberguesses + " tries to guess my number. Do you want to play again? Type yes if you're ready. Type no to quit.");
        String s = scan.nextLine();
        if(s.equals("yes")) {
            Random r = new Random();
            number = r.nextInt(100);
            numberguesses = 0;
            System.out.println("I picked a number between 1 and 100. Guess what it is!");
            calculate();
        } 
        if(s.equals("no")) {
            System.out.println("Thanks for playing.");
            System.exit(0);
        }
    } 
}