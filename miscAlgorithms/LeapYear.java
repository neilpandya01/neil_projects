import java.util.Scanner;
public class LeapYear {
    public static void main (String [] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Type -1 at any time to quit.");
        while(true) {
            System.out.println("Input a year after 1582 and I will tell you if that is a leap year.");
            int year = scan.nextInt();
            if(year == -1) {
                break;
            }
            if(year >= 1582) {
                if (year%4 == 0 && year%100 != 0) {
                    System.out.println("That is a leap year.");
                }
                else if (year%400 == 0) {
                    System.out.println("That is a leap year.");
                }
                else {
                    System.out.println("That is not a leap year.");
                }
            }
        }
        System.out.println("Bye bye");
    }
}