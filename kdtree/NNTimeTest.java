
/**
 * Write a description of class NNTimeTest here.
 *
 * @author (Arjun Jauhari)
 * @version (1.0)
 */
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdRandom;
public class NNTimeTest
{
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        Point2D p;

        // initialize the two data structures with point from standard input
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }

        int iter = 2000;
        long startTime = System.nanoTime();
        for (int i = 0; i < iter; i++) {
            double x = StdRandom.uniform(0.0, 1.0);
            double y = StdRandom.uniform(0.0, 1.0);
            p = new Point2D(x, y);
            kdtree.nearest(p);
        }
        long endTimekd = System.nanoTime();
        for (int i = 0; i < iter; i++) {
            double x = StdRandom.uniform(0.0, 1.0);
            double y = StdRandom.uniform(0.0, 1.0);
            p = new Point2D(x, y);
            brute.nearest(p);
        }
        long endTimeBrute = System.nanoTime();

        double kdTime= (endTimekd - startTime)/1000000;
        double bruteTime= (endTimeBrute - endTimekd)/1000000;
        System.out.println("KD Time: " + kdTime + "ms");
        System.out.println("Brute Time: " + bruteTime + "ms");
    }
}
