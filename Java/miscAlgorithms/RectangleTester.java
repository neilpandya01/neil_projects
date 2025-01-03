import java.util.Scanner;
public class RectangleTester {
    public static void main (String [] args) {
        Scanner scan = new Scanner (System.in);
        System.out.println("Input the length of a rectangle");
        double l = scan.nextDouble();
        System.out.println("Input the width of a rectangle");
        double w = scan.nextDouble();
        Rectangle e = new Rectangle (l, w);
        System.out.println(e.toString());
        System.out.println("Input the length of a second rectangle");
        double l2 = scan.nextDouble();
        System.out.println("Input the width of a second rectangle");
        double w2 = scan.nextDouble();
        Rectangle r = new Rectangle (l2, w2);
        System.out.println("Is equal to first rectangle: " + e.equals(r));
        System.out.println("Area of first: " + e.area());
        System.out.println("Perimeter of first: " + e.perimeter());
        System.out.println("Is a square of first: " + e.isSquare());
        e.expandBy(4);
        System.out.println("Expanded by 4 of first gives you " + e.toString());
    }
}