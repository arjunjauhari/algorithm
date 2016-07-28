
/**
 * Write a description of class FastCollinearPoints here.
 *
 * @author (Arjun Jauhari)
 * @version (1.0)
 */
import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
public class FastCollinearPoints
{
    // instance variables - replace the example below with your own
    private int N = 0;
    private LineSegment[] lseg_arr;

    /**
     * Constructor for objects of class FastCollinearPoints
     */
    public FastCollinearPoints(Point[] points)
    {
        if (points == null) throw new java.lang.NullPointerException();
        for (Point p : points)
            if (p == null) throw new java.lang.NullPointerException();

        // initialise instance variables
        lseg_arr = new LineSegment[points.length];
        //Point[] aux_points = Arrays.copyOf(points, points.length);
        Point[] aux_points = points.clone();    // 8N +24 bytes

        // sort points by slopeorder
        for (Point p : points) {
            Arrays.sort(aux_points, p.slopeOrder());    // N^2lgN
            find_adjacent(aux_points, p);
        }
    }

    private void find_adjacent(Point[] points, Point p) {
        int k = 0;
        for (int i = 0; i < points.length; i = k) {
            k = i + 1;
            while (k < points.length && (p.slopeTo(points[i]) == p.slopeTo(points[k]))) {
                k++;
            }
            if ((k - i) >= 3) {
                // line found
                Point[] pp = new Point[k-i+1];
                pp[0] = p;
                for (int a = i; a < k; a++)
                    pp[a-i+1] = points[a];
                Arrays.sort(pp);
                if (!duplicate(pp[0], pp[k-i]))
                    lseg_arr[N++] = new LineSegment(pp[0], pp[k-i]);
            }
        }
    }

    private boolean duplicate(Point p, Point q) {
        for (int i = 0; i < N; i++) {
            if (lseg_arr[i].toString().equals(p + " -> " + q))
                return true;
        }
        return false;
    }

    public int numberOfSegments() {
        // the number of line segments
        return N;
    }

    public LineSegment[] segments() {
        // the line segments
        return Arrays.copyOf(lseg_arr, N);
    }

    public static void main(String[] args) {

        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        //BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        StdOut.println("Num of Lines found " + collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}
