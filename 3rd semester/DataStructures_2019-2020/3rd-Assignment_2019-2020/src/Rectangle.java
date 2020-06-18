import org.jetbrains.annotations.NotNull;

/**
 * Rectangle class
 */
public class Rectangle {
    private int xMin, xMax, yMin, yMax; //Max and min points of the rectangle
    private Point min, max; //The points with coordinate min(xMin, yMin) max(xMax, yMax)

    /**
     * Rectangle constructor taking 4 coordinate (xMin, xMax, yMin, yMax)
     * @param xMin is the x coordinate of the minimum point of the rectangle
     * @param xMax is the x coordinate of the maximum point of the rectangle
     * @param yMin is the y coordinate of the minimum point of the rectangle
     * @param yMax is the y coordinate of the maximum point of the rectangle
     */
    public Rectangle(int xMin, int xMax, int yMin, int yMax) {
        this.yMax = yMax;
        this.yMin = yMin;
        this.xMax = xMax;
        this.xMin = xMin;
        this.min = new Point(this.xMin,this.yMin);
        this.max = new Point(this.xMax,this.yMax);
    }

    /**
     * Constructs a rectangle with two points
     * @param min the minimum point of the rectangle
     * @param max the maximum point of the rectangle
     */
    @SuppressWarnings("unused")
    public Rectangle(Point min, Point max){
        this.min = min;
        this.max = max;
        this.xMax = this.max.getX();
        this.xMin = this.min.getX();
        this.yMin = this.min.getY();
        this.yMax = this.max.getY();
    }

    /**
     * Returns the max x coordinate of the rectangle
     * @return the max x coordinate of the rectangle
     */
    public int getMaxx() {
        return this.max.getX();
    }

    /**
     * Returns the min y coordinate of the rectangle
     * @return the min y coordinate of the rectangle
     */
    public int getMinx() {
        return this.min.getX();
    }

    /**
     * Returns the max y coordinate of the rectangle
     * @return the max y coordinate of the rectangle
     */
    public int getMaxy() {
        return this.max.getY();
    }

    /**
     * Returns the min y coordinate of the rectangle
     * @return the min y coordinate of the rectangle
     */
    public int getMiny() {
        return this.min.getY();
    }

    /**
     * Returns if a point is on the boundaries of the rectangle or in the rectangle
     * @param p is a point given by the users
     * @return true if the point is in or on the boundaries of the rectangle, false otherwise
     */
    public boolean contains(@NotNull Point p){
        return p.getX()>=this.getMinx() && p.getY()>=this.getMiny() && p.getX()<=this.getMaxx() && p.getY()<=this.getMaxy();
    }

    /**
     * Returns if any point of the given rectangle is also a point of this rectangle
     * @param that is another given rectangle
     * @return true if the rectangle that instersects with this rectangle in any point, false otherwise
     */
    public boolean intersects(@NotNull Rectangle that){
        //Checks if one rectangle is over the other and vice versa or if one rectangle is on the left other other is vice versa
        return this.getMaxy() >= that.getMiny() && this.getMiny() <= that.getMaxy() && this.getMaxx() >= that.getMinx() && this.getMinx() <= that.getMaxx();
    }

    /**
     * Returns the distance from the closest point in the rectangle to a Point
     * @param p is a given point
     * @return the distance from the closest point in the rectangle to p
     */
    public double distanceTo(Point p){
        return Math.sqrt(squareDistanceTo(p));
    }

    /**
     * Returns the square distance from the closest point in the rectangle to a Point
     * @param p is given Point
     * @return the square of the Euclidean distance from p to the closest point in the rectangle
     */
    public int squareDistanceTo(Point p){
        //Upper left and lower right points of the rectangle
        Point upperLeft = new Point(getMinx(),getMaxy());
        Point lowerRight = new Point(getMaxx(), getMiny());

        //Checks whether p is in the rectangle or on the boundaries and returns 0
        if(this.contains(p)){
            return 0;
        }

        //Case for being left from the minX of the rectangle
        if(p.getX()<this.getMinx()){
            //Case for being lower that the minY of the rectangle
            if(p.getY()<=this.getMiny()){
                return getMinPoint().squareDistanceTo(p);
            }
            //Case for being lower than the maxY of the rectangle
            else if(p.getY()<this.getMaxy()){
                return p.squareDistanceTo(new Point(this.getMinx(),p.getY()));
            }
            //Case for being higher than the maxY of the rectangle
            return upperLeft.squareDistanceTo(p);
        }
        //Case for being right of the rectangle
        else if(p.getX()>this.getMaxx()){
            //Case for being lower that the minY of the rectangle
            if(p.getY()<=this.getMiny()){
                return lowerRight.squareDistanceTo(p);
            }
            //Case for being lower than the maxY of the rectangle
            else if(p.getY()<this.getMaxy()){
                return p.squareDistanceTo(new Point(this.getMaxx(),p.getY()));
            }
            //Case for being higher than the maxY of the rectangle
            return getMaxPoint().squareDistanceTo(p);
        }
        //Being between the extended boundaries of the maxx and minxx
        else if(p.getX()>=this.getMinx() && p.getX()<=this.getMaxx()){
            //Being lower that the lowest y value
            if(p.getY()<this.getMiny()){
                return p.squareDistanceTo(new Point(p.getX(),this.getMiny()));
            }
            //Being higher that the higher y value
            else if(p.getY()>this.getMaxy()){
                return p.squareDistanceTo(new Point(p.getX(),this.getMaxy()));
            }
        }
        //Default case
        return 0;
    }

    /**
     * Returns the string representation of the upper right and the lower left point of the Rectangle
     * @return a string in the format [xmin, xmax] x [ymin, ymax]
     */
    public String toString(){
        return "[" + this.getMinx() + ", " + this.getMaxx() + "] x [" + this.getMiny() + ", " + this.getMaxy() + "]";
    }

    public Point getMaxPoint() {
        return max;
    }

    public Point getMinPoint() {
        return min;
    }
}
