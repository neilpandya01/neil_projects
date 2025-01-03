import java.util.Scanner;
import java.util.Random;
public class RPS {
    public static void main (String [] args) {
        System.out.println("You are playing rock, paper, scissors. Type quit to stop playing at anytime.");
        Scanner scan = new Scanner(System.in);
        int wins = 0;
        int losses = 0;
        int draws = 0;
        while(true) {
            String[] options = new String [] {"r","p","s"};
            Random r = new Random();
            String compChoice = options [r.nextInt(3)];
            System.out.println("Type R for rock, P for paper, or S for scissors.");
            String userChoice = scan.nextLine().toLowerCase();
            if(userChoice.equals("quit")) {
                break;
            }
            if(userChoice.equals("r") || userChoice.equals("p") || userChoice.equals("s")) {
                System.out.println("The computer chose " + compChoice);
                if(userChoice.equals(compChoice)) {
                    draws++;
                    System.out.println("That is a draw!  :|");
                }
                else if(userChoice.equals("r") || compChoice.equals("r")) {
                    if(userChoice.equals("r")) {
                        if(compChoice.equals("p")) {
                            losses++;
                            System.out.println("That is a loss for you!  :(");
                        }
                        else {
                            wins++;
                            System.out.println("That is a win for you!  :)");
                        }
                    }
                    else {
                        if(userChoice.equals("p")) {
                            wins++;
                            System.out.println("That is a win for you!  :)");
                        }
                        else {
                            losses++;
                            System.out.println("That is a loss for you!  :(");
                        }
                    }
                }
                else if(userChoice.equals("p") || compChoice.equals("p")) {
                    if(userChoice.equals("p")) {
                        if(compChoice.equals("s")) {
                            losses++;
                            System.out.println("That is a loss for you!  :(");
                        }
                        else {
                            wins++;
                            System.out.println("That is a win for you!  :)");
                        }
                    }
                    else {
                        if(userChoice.equals("s")) {
                            wins++;
                            System.out.println("That is a win for you!  :)");
                        }
                        else {
                            losses++;
                            System.out.println("That is a loss for you!  :(");
                        }
                    }
                }
            }
            else {
                System.out.println("Please input r, p, or s.");
            }
            System.out.println("So far you have " + wins + " wins, " + losses + " losses, and " + draws + " draws.");
        }
        System.out.println("Thanks for playing!");
    }
}