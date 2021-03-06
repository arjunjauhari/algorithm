/**
 * Write a description of class Percolation here.
 * 
 * @author (Arjun Jauhari) 
 * @version (1.0)
 */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;
public class Percolation
{
    // instance variables
    private int N;
    private int idxI, idxJ;
    private boolean[][] op;
    private WeightedQuickUnionUF wquuf;    // to avoid backwash
    private WeightedQuickUnionUF wquufP;

    /**
     * Constructor for objects of class Percolation
     */
    public Percolation(int N)               // create N-by-N grid,
                                            // with all sites blocked
    {
        if (N <= 0) throw
            new java.lang.IllegalArgumentException("N should be greater than 0");
        // initialise instance variables
        this.N = N;
        op = new boolean[N][N]; // by default initialized to 0
        wquufP = new WeightedQuickUnionUF(N*N+2);
        wquuf = new WeightedQuickUnionUF(N*N+1);
        for (int r = 0; r < N; r++) {
            wquufP.union(r, N*N);            // connection to top virtual node
            wquuf.union(r, N*N);             // connection to top virtual node
            wquufP.union((N*N-1)-r, N*N+1);  // connection to bottom virtual node
        }
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j)
    {
        chkvalididx(i, j);
        idxI = i-1;
        idxJ = j-1;
        op[idxI][idxJ] = true;   // opening a site
        if (idxJ > 0 && op[idxI][idxJ-1])    // left
        {
            doUnion(idxI, idxJ, idxI, idxJ-1);
        }
        if (idxJ < N-1 && op[idxI][idxJ+1])    // right
        {
            doUnion(idxI, idxJ, idxI, idxJ+1);
        }
        if (idxI > 0 && op[idxI-1][idxJ])    // top
        {
            doUnion(idxI, idxJ, idxI-1, idxJ);
        }
        if (idxI < N-1 && op[idxI+1][idxJ])    // bottom
        {
            doUnion(idxI, idxJ, idxI+1, idxJ);
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
        // using wquuf to avoid backwash
        return (wquuf.connected(N*N, getidx(i-1, j-1))
                && isOpen(i, j));   // check whether connected to top virtual node
    }

    public boolean percolates()             // does the system percolate?
    {
        // if top and bottom virtual node are connected
        return wquufP.connected(N*N, N*N+1);
    }

    public static void main(String[] args)
    {
        Percolation per1 = new Percolation(5);
        per1.open(1, 2);
        if (per1.isOpen(1, 2))
            StdOut.println("yes open");
        if (per1.isFull(1, 2))
            StdOut.println("yes Full");
    }

    private void chkvalididx(int i, int j)
    {
        if (i <= 0 || i > N) throw
            new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > N) throw
            new IndexOutOfBoundsException("col index j out of bounds");
    }
}
