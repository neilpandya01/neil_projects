import java.util.Scanner;
public class HmsToSeconds {
    public static void main () {
        loop();
    }
    public static void loop () {
        Scanner scan = new Scanner(System.in);
        System.out.println("Convert from  |mins,hours,sec|  to seconds");
        String unit = scan.nextLine();
        System.out.println("What number do you want to convert from " + unit + " to seconds?");
        double num = scan.nextDouble();
        double ans = 1;
        if(unit.contains("sec")) {
            ans = num;
        }
        if(unit.contains("min")) {
            ans = num * 60;
        }
        if(unit.contains("hour")) {
            ans = num * 3600;
        }
        System.out.println( num + " " + unit + " is equal to " + ans + " seconds.");
    }
}