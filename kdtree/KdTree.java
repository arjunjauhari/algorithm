
/**
 * Write a description of class PointSET here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.TreeSet;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
public class KdTree
{
    private static final boolean X = true;
    private static final boolean Y = false;

    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;
        private int N;  // FIXME not required
        private boolean orien;

        private Node(Point2D p, int N, boolean orien,
                double xmin, double xmax, double ymin, double ymax) {
            this.p = p;
            this.N = N;
            this.orien = orien;
            this.rect = new RectHV(xmin, ymin, xmax, ymax);
            this.lb = null;
            this.rt = null;
        }
    }

    // instance variables - replace the example below with your own
    Node root;
    /**
     * Constructor for objects of class PointSET
     */
    public KdTree()
    {
        // initialise instance variables
        root = null;
    }

    /**
     * To check whether tree is empty
     *
     * @return true if tree is empty otherwise false
     */
    public boolean isEmpty() {
        return (root == null);
    }

    /**
     * To get the size of the tree
     * 
     * @return size of the tree 
     */
    public int size() {
        return size(root);
    }

    private int size(Node root) {
        if (root == null) return 0;
        else return root.N;
    }

    /**
     * To insert a point
     * 
     * @param  p   point to be inserted
     */
    public void insert(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException();

        root = insert(root, p, X, 0, 1, 0, 1);
    }

    private Node insert(Node root, Point2D p, boolean orien,
            double xmin, double xmax, double ymin, double ymax) {
        if (root == null) {
            return new Node(p, 1, orien, xmin, xmax, ymin, ymax);
        }

        if (root.p.equals(p)) return root;

        if (orien == X) {
            if (p.x() < root.p.x()) {   // go left
                root.lb = insert(root.lb, p, Y,
                        root.rect.xmin(), root.p.x(),
                        root.rect.ymin(), root.rect.ymax());
            } else {
                root.rt = insert(root.rt, p, Y,
                        root.p.x(), root.rect.xmax(),
                        root.rect.ymin(), root.rect.ymax());
            }
        } else {
            if (p.y() < root.p.y()) {
                root.lb = insert(root.lb, p, X,
                        root.rect.xmin(), root.rect.xmax(),
                        root.rect.ymin(), root.p.y());
            } else {
                root.rt = insert(root.rt, p, X,
                        root.rect.xmin(), root.rect.xmax(),
                        root.p.y(), root.rect.ymax());
            }
        }

        root.N = 1 + size(root.lb) + size(root.rt);
        return root;
    }

    /**
     * To check if point is already present in the tree
     * 
     * @param  p   point to be checked
     * @return     true if point is present otherwise false
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException();

        return contains(root, p);
    }

    private boolean contains(Node root, Point2D p) {
        if (root == null) {
            return false;
        }

        if (root.p.equals(p)) return true;

        if (root.orien == X) {
            if (p.x() < root.p.x()) {
                return contains(root.lb, p);
            } else {
                return contains(root.rt, p);
            }
        } else {
            if (p.y() < root.p.y()) {
                return contains(root.lb, p);
            } else {
                return contains(root.rt, p);
            }
        }
    }

    /**
     * Draw all the points to standard draw
     */
    public void draw() {
        draw(root);
    }

    private void draw(Node root) {
        if (root == null) return;
        // draw black point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(root.p.x(), root.p.y());

        // draw splitting line
        StdDraw.setPenRadius();
        if (root.orien == X) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(root.p.x(), root.rect.ymin(), root.p.x(), root.rect.ymax());
            draw(root.lb);
            draw(root.rt);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(root.rect.xmin(), root.p.y(), root.rect.xmax(), root.p.y());
            draw(root.lb);
            draw(root.rt);
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

        search(subset, root, rect);

        return subset;
    }

    private void search(TreeSet<Point2D> subset, Node root, RectHV rect) {
        if (root == null) return;

        // check if the node rectangle intersect with the given rectangle
        if (!rect.intersects(root.rect)) return;

        // check if current node is inside
        if (rect.contains(root.p)) subset.add(root.p);

        // search both childs
        search(subset, root.lb, rect); 
        search(subset, root.rt, rect); 
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
        return nearest(root, p, champ);
    }

    private Point2D nearest(Node root, Point2D query, Point2D champ) {
        
        double minDist;

        if (champ == null) {
            minDist = 100;  // any high value
        } else {
            minDist = query.distanceTo(champ);
        }

        // check if this sub-tree needs to be searched
        if (root.rect.distanceTo(query) > minDist) {
            return champ;
        }

        boolean skipLeft = false;
        boolean skipRight = false;

        // distance to current node point
        double tmp = query.distanceTo(root.p);
        if (tmp < minDist) {
            minDist = tmp;  // FIXME minDist is passed by value - CAREFUL
            champ = root.p;
        }

        // check if left subtree needs to be searched
        if ((root.lb == null) || (root.lb.rect.distanceTo(query) > minDist)) { // skip
            skipLeft = true;
        }

        // check if right subtree needs to be searched
        if ((root.rt == null) || (root.rt.rect.distanceTo(query) > minDist)) { // skip
            skipRight = true;
        }

        if (skipLeft && skipRight) {
            return champ;
        } else if (skipLeft) {  // go right
            return nearest(root.rt, query, champ);
        } else if (skipRight) { // go left
            return nearest(root.lb, query, champ);
        } else {    // have to check both
            if (((root.orien == X) && (query.x() < root.p.x())) ||
                    ((root.orien == Y) && (query.y() < root.p.y()))) {  // go left first
                    champ = nearest(root.lb, query, champ);
                    return nearest(root.rt, query, champ);
                } else {    // go right first
                    champ = nearest(root.rt, query, champ);
                    return nearest(root.lb, query, champ);
                }
        }
    }

    public static void main(String[] args) {
        KdTree kd = new KdTree();
        Point2D p = new Point2D(0.1,0.1);

        System.out.println(kd.contains(p));

        for (int i = 0; i < 10; i++) {
            p = new Point2D(i/10.0, i/10.0);
            kd.insert(p);
        }

        for (int i = 0; i < 5; i++) {
            p = new Point2D(i/10.0, i/5.0);
            System.out.println(kd.contains(p));
        }

        p = new Point2D(0.7,0.3);
        System.out.println(kd.contains(p));
        System.out.println(kd.size());
        StdDraw.show();
        kd.draw();
    }
}
