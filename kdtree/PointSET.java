
/**
 * Write a description of class PointSET here.
 *
 * @author (Arjun Jauhari)
 * @version (1.0)
 */
import java.util.TreeSet;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
public class PointSET
{
    // instance variables - replace the example below with your own
    private TreeSet<Point2D> pSet;

    /**
     * Constructor for objects of class PointSET
     */
    public PointSET()
    {
        // initialise instance variables
        pSet = new TreeSet<Point2D>();
    }

    /**
     * To check whether SET is empty
     *
     * @return true is SET is empty otherwise false
     */
    public boolean isEmpty() {
        return pSet.isEmpty();
    }

    /**
     * To get the size of the SET
     *
     * @return size of the SET
     */
    public int size() {
        return pSet.size();
    }

    /**
     * To insert a point
     *
     * @param  p   point to be inserted
     */
    public void insert(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException();

        pSet.add(p);
    }

    /**
     * To check if point is already present in the SET
     *
     * @param  p   point to be checked
     * @return     true if point is present otherwise false
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException();

        return pSet.contains(p);
    }

    /**
     * Draw all the points to standard draw
     */
    public void draw() {
        for (Point2D p : pSet) {
            StdDraw.point(p.x(), p.y());
        }
    }

    /**
     * all points that are inside the rectangle
     *
     * @param  rect   rectangle within which points need to be searched
     * @return        Iterable on the points inside the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new java.lang.NullPointerException();

        TreeSet<Point2D> subset = new TreeSet<Point2D>();

        for (Point2D p : pSet) {
            if (rect.contains(p)) {
                subset.add(p);
            }
        }

        return subset;
    }

    /**
     * a nearest neighbour in the set to point p; null if the set is empty
     *
     * @param  p   point to which nearest neighbour is to be searched
     * @return     nearest neighbour
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException();

        Point2D champ = null;
        double minDist = 100;
        double tmp = 0;

        for (Point2D i : pSet) {
            tmp = p.distanceTo(i);
            if (tmp < minDist) {
                champ = i;
                minDist = tmp;
            }
        }

        return champ;
    }
}
