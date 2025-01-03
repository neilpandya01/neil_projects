import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
public class RPS2 {
    public static void main (String [] args) {
        System.out.println("You are playing rock, paper, scissors. Type quit to stop playing at anytime.");
        loop ();
    }

    public static void loop () {
        Scanner scan = new Scanner(System.in);
        int wins = 0;
        int losses = 0;
        int draws = 0;
        while(true) {
            String[] options = new String [] {"r","p","s"};
            String[][][] combos = {
                {{"r"},{"r"},{"draw"}},
                {{"r"},{"p"},{"win"}},
                {{"r"},{"s"},{"lose"}},
                {{"p"},{"r"},{"lose"}},
                {{"p"},{"p"},{"draw"}},
                {{"p"},{"s"},{"win"}},
                {{"s"},{"r"},{"win"}},
                {{"s"},{"p"},{"losr"}},
                {{"s"},{"s"},{"draw"}}
            };
            Random r = new Random();
            String compChoice = options [r.nextInt(3)];
            System.out.println("Type R for rock, P for paper, or S for scissors.");
            String userChoice = scan.nextLine().toLowerCase();
            if(userChoice.equals("quit")) {
                break;
            }
            if(userChoice.equals("r") || userChoice.equals("p") || userChoice.equals("s")) {
                System.out.println("The computer chose " + compChoice);
                for (int comp=0; comp<9; comp++) {
                    if (combos[comp][0][0].equals(compChoice) && combos[comp][1][0].equals(userChoice)) {
                        String result = combos[comp][2][0];
                        if (result.equals("draw")) {
                            draws++;
                            System.out.println("That is a draw!  :|");
                        }
                        else if (result.equals("lose")) {
                            losses++;
                            System.out.println("That is a loss for you!  :(");
                        }
                        else {
                            wins++;
                            System.out.println("That is a win for you!  :)");
                        }
                        break;
                    }
                }
                System.out.println("So far you have " + wins + " wins, " + losses + " losses, and " + draws + " draws.");
            }
            else {
                System.out.println("Please input r, p, or s.");
            }
        }
        System.out.println("Thanks for playing!");
    }
}
