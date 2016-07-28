/**
 * Write a description of class PercolationStats here.
 * 
 * @author (Arjun Jauhari) 
 * @version (1.0)
 */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.StdOut;
public class PercolationStats
{
    // instance variables - replace the example below with your own
    private Percolation per;
    private double[] thresh;
    private int T, cnt;
    //private StdRandom rand;

    /**
     * Constructor for objects of class PercolationStats
     */
    public PercolationStats(int N, int T)
    {
        if (N <= 0 || T <= 0) throw 
            new java.lang.IllegalArgumentException("Not proper arguments");

        // initialise instance variables
        this.T = T;
        thresh = new double[T]; // to store p value for every iteration
        int i, j;

        for (int l = 0; l < T; l++) {
            per = new Percolation(N);
            //rand = new StdRandom();
            cnt = 0;
            while (!per.percolates()) {
                i = StdRandom.uniform(1, N+1);
                j = StdRandom.uniform(1, N+1);
                if (!per.isOpen(i, j)) {
                    per.open(i, j);
                    cnt++;
                }
            }
            thresh[l] = (double) cnt/(N*N);
        }
    }

    public double mean()                      // sample mean of percolation threshold
    {
        return StdStats.mean(thresh);
    }

    // sample standard deviation of percolation threshold
    public double stddev()                    
    {
        return StdStats.stddev(thresh);
    }
    
    // low  endpoint of 95% confidence interval
    public double confidenceLo()              
    {
        return (mean() - (1.96*stddev()/Math.sqrt(T)));
    }
    
    // high endpoint of 95% confidence interval
    public double confidenceHi()              
    {
        return (mean() + (1.96*stddev()/Math.sqrt(T)));
    }
    
    // test client (described below)
    public static void main(String[] args)    
    {
        //Stopwatch sw = new Stopwatch();
        PercolationStats perStats = new PercolationStats(
                Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        //StdOut.printf("%-23s = %f%n","Elapsed Time",sw.elapsedTime());
        StdOut.printf("%-23s = %f%n", "mean", perStats.mean());
        StdOut.printf("%-23s = %f%n", "stddev", perStats.stddev());
        StdOut.printf("%-23s = %f, %f%n", "95% confidence interval",
                perStats.confidenceLo(), perStats.confidenceHi());
    }
}
