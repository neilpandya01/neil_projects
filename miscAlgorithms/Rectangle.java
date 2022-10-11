public class Rectangle {
    private double length;
    private double width;
    public Rectangle () {
        length = 0;
        width = 0;
    }
    public Rectangle (double l, double w) {
        length = l;
        width = w;
    }
    public String toString () {
         return "Rectangle: length = " + length + ", width = " + width;
    }
    public double area () {
        return length * width;
    }
    public double perimeter () {
        return (2*length) + (2*width);
    }
    public boolean isSquare () {
        return length == width;
    }
    public void expandBy (double d) {
        length += d;
        width += d;
    }
    public boolean equals (Rectangle r) {
        return ((width == r.width) && (length == r.length));
    }
}