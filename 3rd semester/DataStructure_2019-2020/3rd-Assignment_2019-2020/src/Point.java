import org.jetbrains.annotations.NotNull;

/**
 * Point Class
 */
public class Point {
    private int x, y; //x and y coordinates

    /**
     * Pointer constructor
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x coordinate of the point
     * @return x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y coordinate of the point
     * @return y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the Euclidean distance between two points
     * @param z is another point
     * @return the distance between this point and point z
     */
    public double distanceTo(Point z) {
        return Math.sqrt(squareDistanceTo(z));
    }

    /**
     * Returns the square of the Euclidean distance between two points
     * @param z is another point
     * @return the distance between this point and point z
     */
    public int squareDistanceTo(@NotNull Point z){
        return (int) (Math.pow(this.x - z.x, 2) + Math.pow(this.y - z.y, 2));
    }

    /**
     * Returns a string with the coordinates in (x, y) format
     * @return the String representation of the coordinates in (x, y) format
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Check whether a point p == a point po
     * @param o another point
     * @return if points are equal
     */
    public boolean equals(Point o) {
        if (this == o) return true;
        if (o == null) return false;
        return getX() ==  o.getX() &&
                getY() == o.getY();
    }
}


