import java.util.Scanner;
public class DistForm {
    public static void main () {
        Scanner scan = new Scanner(System.in);
        System.out.println("I will calculate the distance from (X1,Y1) and (X2,Y2). Type point X1 below.");
        double x1 = scan.nextDouble();
        System.out.println("Type point Y1 below.");
        double y1 = scan.nextDouble();
        System.out.println("Type point X2 below.");
        double x2 = scan.nextDouble();
        System.out.println("Type point Y2 below.");
        double y2 = scan.nextDouble();
        double xdiff = x2 - x1;
        xdiff = xdiff * xdiff;
        double ydiff = y2 - y1;
        ydiff = ydiff * ydiff;
        double takesqrt = xdiff + ydiff;
        System.out.println("The distance between (" + x1 + "," + y1 + ") and (" + x2 + "," + y2 + ") is " + Math.sqrt(takesqrt));
    }
}