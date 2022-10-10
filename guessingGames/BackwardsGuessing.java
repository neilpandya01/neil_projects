import java.util.Scanner;
public class BackwardsGuessing {
    public static int guess;
    public static int factor;
    public static void main () {
        intro ();
    }
    public static void intro () {
        guess = 0;
        factor = 50;
        System.out.println("Think of a number between 1 and 100. I will do my best to guess that number within 7 tries. ");
        System.out.println("Type 'too high' or 'too low' based on my guess. Type 'yes' if my guess is correct.");
        afterIntro (50);
    }
    public static void afterIntro (int currentg) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Is it " + currentg + "?");
        guess++;
        factor = (factor/2);
        if (factor == 0) {
            factor = 1;
        }
        String answer = scan.nextLine();
        if(answer.equals("too high")) {
            afterIntro ((currentg - factor));
        }
        if(answer.equals("too low")) {
            afterIntro ((currentg + factor));
        }
        if(answer.equals("yes")) {
            System.out.println("Guessed it in " + guess + " tries! Ha!");
            System.out.println("Wanna play again? Type 'yes' to continue or 'no' to quit.");
            if(scan.nextLine().equals("yes")) {
                intro ();
            }
            else {
                scan.close();
            }
        }
    }
}