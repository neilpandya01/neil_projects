import java.util.Scanner;
import java.util.Currency;
import java.util.Locale;
import java.text.NumberFormat;
public class CountChange {
    public static void main () {
        Scanner scan = new Scanner(System.in);
        System.out.println("I will count the total value of a given number of quarters, dimes, nickels, and pennies. Type number of quarters below. ");
        int quarters = scan.nextInt();
        System.out.println("Now type number of dimes.");
        int dimes = scan.nextInt();
        System.out.println("Now type number of nickels.");
        int nickels = scan.nextInt();
        System.out.println("Now type number of pennies.");
        int pennies = scan.nextInt();
        double change = (0.25 * quarters) + (0.1 * dimes) + (0.05 * nickels) + (0.01 * pennies);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        System.out.println("That would give you " + currencyFormatter.format(change));
    }
}