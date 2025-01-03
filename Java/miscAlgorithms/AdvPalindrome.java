import java.util.Scanner;
public class AdvPalindrome {
    public static void main (String [] args) {
        Scanner scan = new Scanner(System.in);
        while(true) {
            System.out.println("Input a string and I will tell you if that string is a palindrome");
            String inp = scan.nextLine().replaceAll("[^a-zA-Z0-9]", "");
            if (inp.equalsIgnoreCase("quit")) {
                break;
            }
            inp = inp.toLowerCase();
            String out = "";
            for(int i=inp.length()-1; i>=0; i--) {
                out = out + inp.charAt(i);
            }
            if(out.equals(inp)) {
                System.out.println("That is a palindrome.");
            }
            else {
                System.out.println("That isn't a palindrome.");
            }
        }
    }
}