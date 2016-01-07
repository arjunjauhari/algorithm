
/**
 * Write a description of class Percolation here.
 * 
 * @author (Arjun Jauhari) 
 * @version (1.0)
 */
import edu.princeton.cs.algs4.QuickFindUF;
public class PercolationQF
{
    // instance variables - replace the example below with your own
    private int N;
    private int d_i, d_j;
    private boolean[][] op;
    private QuickFindUF wquuf;
    private QuickFindUF wquufP;    // to avoid backwash

    /**
     * Constructor for objects of class Percolation
     */
    public PercolationQF(int N)               // create N-by-N grid,
                                            // with all sites blocked
    {
        if (N <= 0) throw new java.lang.IllegalArgumentException("N should be greater than 0");
        // initialise instance variables
        this.N = N;
        op = new boolean[N][N]; // by default initialized to 0
        wquufP = new QuickFindUF(N*N+2);
        wquuf = new QuickFindUF(N*N+1);
        for (int r = 0; r < N; r++) {
            wquufP.union(r, N*N);            // connection to top virtual node
            wquuf.union(r, N*N);             // connection to top virtual node
            wquufP.union((N*N-1)-r, N*N+1);  // connection to bottom virtual node
        }
    }

    public void open(int i, int j)          // open site (row i, column j) if it is not open already
    {
        chkvalididx(i, j);
        d_i = i-1;
        d_j = j-1;
        op[d_i][d_j] = true;   // opening a site
        if (d_j > 0 && op[d_i][d_j-1])    // left
        {
            doUnion(d_i, d_j, d_i, d_j-1);
        }
        if (d_j < N-1 && op[d_i][d_j+1])    // right
        {
            doUnion(d_i, d_j, d_i, d_j+1);
        }
        if (d_i > 0 && op[d_i-1][d_j])    // top
        {
            doUnion(d_i, d_j, d_i-1, d_j);
        }
        if (d_i < N-1 && op[d_i+1][d_j])    // bottom
        {
            doUnion(d_i, d_j, d_i+1, d_j);
        }
    }

    private void doUnion(int a, int b, int c, int d)
    {
            wquuf.union(getidx(a, b), getidx(c, d));
            wquufP.union(getidx(a, b), getidx(c, d));
    }

    private int getidx(int i, int j)
    {
        return N*(i) + (j);
    }

    public boolean isOpen(int i, int j)     // is site (row i, column j) open?
    {
        chkvalididx(i, j);
        return (op[i-1][j-1]);
    }

    public boolean isFull(int i, int j)     // is site (row i, column j) full?
    {
        chkvalididx(i, j);
        return (wquuf.connected(N*N, getidx(i-1, j-1)) && isOpen(i, j));   // check whether connected to top virtual node
    }

    public boolean percolates()             // does the system percolate?
    {
        return wquufP.connected(N*N, N*N+1); // if top and bottom virtual node are connected
    }

    public static void main(String[] args)
    {
        Percolation per1 = new Percolation(5);
        per1.open(1, 2);
        if (per1.isOpen(1, 2))
            System.out.println("yes open");
        if (per1.isFull(1, 2))
            System.out.println("yes Full");
    }

    private void chkvalididx(int i, int j)
    {
        if (i <= 0 || i > N) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > N) throw new IndexOutOfBoundsException("col index j out of bounds");
    }
    ///**
    // * An example of a method - replace this comment with your own
    // * 
    // * @param  y   a sample parameter for a method
    // * @return     the sum of x and y 
    // */
    //public int sampleMethod(int y)
    //{
    //    // put your code here
    //    return x + y;
    //}
}
