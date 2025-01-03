import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;

public class QuizletWrite {
    public static void main (String[] args) throws java.io.IOException {
        BufferedReader in = new BufferedReader (new InputStreamReader(System.in));
        System.out.println("Type the file name of the study set to practice (using a new line between terms, and ' - ' between term/definition.");
        String f = in.readLine();
        ArrayList<String> left = new ArrayList();
        ArrayList<String> terms = new ArrayList();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            for (String curWord = reader.readLine(); curWord != null; curWord = reader.readLine()) {
                terms.add(curWord);
            }
            System.out.println("File successfully read");
        }
        catch (Exception e) {
            System.out.println("File was not successfully read");
            e.printStackTrace();
        }
        // WORKS: System.out.println(terms);

        System.out.println("We will begin the quiz now.");

        while (true) {
            if (terms.size() == 0) {
                if (left.size() > 0) {
                    for (int i=0; i<left.size(); i++) {
                        terms.add(left.get(i));
                        left.remove(i);
                        i--;
                    }
                    System.out.println("Congrats on finishing the current round! The next round will begin soon. Get ready!");
                    continue;
                }
                else {
                    break;
                }
            }
            System.out.println(terms.size() + " terms left in this round.");
            Random x = new Random();
            int rand = x.nextInt(terms.size());
            String current = terms.get(rand);
            System.out.println("Word: '" + terms.get(rand).substring(terms.get(rand).indexOf(" - ")+3) + "'");
            String inp = in.readLine();
            if (inp.equalsIgnoreCase(terms.get(rand).substring(0, terms.get(rand).indexOf(" - ")))) {
                System.out.println("CORRECT! Override? type 'o' or press enter for no.");
                inp = in.readLine();
                if (inp.equalsIgnoreCase("o")) {
                    left.add(terms.get(rand));
                    terms.remove(rand);
                }
                else {
                    terms.remove(rand);
                }
            }
            else {
                System.out.println("That is INCORRECT! The answer is '" + terms.get(rand).substring(0, terms.get(rand).indexOf(" - ")) + "'. Override? type 'o' or press enter for no.");
                inp = in.readLine();
                if (inp.equalsIgnoreCase("o")) {
                    terms.remove(rand);
                }
                else {
                    left.add(terms.get(rand));
                    terms.remove(rand);
                }
            }
        }
        System.out.println("You mastered the set!");
    }
}