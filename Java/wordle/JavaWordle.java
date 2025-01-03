import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class JavaWordle {
    public static void main (String[] args) throws InterruptedException {
        Scanner scan = new Scanner (System.in);
        ArrayList<String> WordleDictionary = new ArrayList();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Wordle Dictionary.txt"));
            for (String curWord = reader.readLine(); curWord != null; curWord = reader.readLine()) {
                WordleDictionary.add(curWord);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> GeneralDictionary = new ArrayList();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("dictionary.txt"));
            for (String curWord = reader.readLine(); curWord != null; curWord = reader.readLine()) {
                GeneralDictionary.add(curWord);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        int l = 5;
        String word = WordleDictionary.get((int) (Math.random()*WordleDictionary.size() + 1));
        System.out.println("Your " + l + " letter word has been chosen.");
        //System.out.println(word);
        for (int i=1; i<=6; i++) {
            System.out.println("It is your " + i + " turn.");
            System.out.println("Input a " + l + " letter guess.");
            String inp = scan.nextLine();
            if (inp.length() != l) {
                System.out.println("Your guess was not " + l + " letters long. Try again.");
                i--;
            }
            else if (!GeneralDictionary.contains(inp.toUpperCase())) {
                System.out.println("Your guess is not a valid word. Try again.");
                i--;
            }
            else {
                for (int j=0; j<5; j++) {
                    if (inp.substring(j, j+1).equalsIgnoreCase(word.substring(j, j+1))) {
                        System.out.print("G");
                    }
                    else if (word.contains(inp.substring(j, j+1))) {
                        System.out.print("Y");
                    }
                    else {
                        System.out.print("X");
                    }
                    Thread.sleep(1000);
                }
                System.out.println();
                if (inp.equalsIgnoreCase(word)) {
                    System.out.println("Nice work!");
                    break;
                }
                if (i == 6) {
                    System.out.println("The word was " + word + ". Better luck next time.");
                    break;
                }
            }
        }
    }
}