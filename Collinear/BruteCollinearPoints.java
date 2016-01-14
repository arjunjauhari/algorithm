
/**
 * Write a description of class BruteCollinearPoints here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
public class BruteCollinearPoints
{
    // instance variables - replace the example below with your own
    private int N;
    private LineSegment[] lseg_arr;

    /**
     * Constructor for objects of class BruteCollinearPoints
     */
    public BruteCollinearPoints(Point[] points)
    {
        // initialise instance variables
        double s_itoj;
        double[] sf = new double[points.length];
        int lf = 0;
        lseg_arr = new LineSegment[points.length];
        Point[] ep = new Point[points.length];
        Point sp = null;
        if (points == null) throw new java.lang.NullPointerException();
        for (Point p : points)
            if (p == null) throw new java.lang.NullPointerException();
        // sort the input array
        Arrays.sort(points);
        for (int i = 0;i < points.length;i++) {
            for (int j = i+1;j < points.length;j++) {
                s_itoj = points[i].slopeTo(points[j]);
                for (int k = j+1;k < points.length;k++) {
                    if (s_itoj == points[i].slopeTo(points[k])) {
                        for (int l = k+1;l < points.length;l++) {
                            if (s_itoj == points[i].slopeTo(points[l])){
                                //line found
                                sp = points[i];
                                for (int m = 0;m < lf; m++) {
                                    if (s_itoj == sf[m]) {
                                        // duplicate line
                                        ep[m] = points[l];
                                        break;
                                    }
                                    // new line
                                    sf[lf] = s_itoj;
                                    ep[lf++] = points[l];
                                }
                                if (lf == 0) {
                                    // new line
                                    sf[lf] = s_itoj;
                                    ep[lf++] = points[l];
                                }

                                //lseg_arr[N++] = getLseg(points[i],
                                //        points[j], points[k], points[l]);
                            }

                        }
                    }
                }
            }
            // create line segment
            if (lf != 0) {
                for (int a = 0; a < lf; a++)
                    lseg_arr[N++] = new LineSegment(sp, ep[a]);
                lf = 0;
            }
        }
    }

    private LineSegment getLseg(Point a, Point b, Point c, Point d) {
        Point[] p = {a, b, c, d};
        Arrays.sort(p);
        return new LineSegment(p[0], p[3]);
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        StdOut.println("Num of Lines found " + collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}
