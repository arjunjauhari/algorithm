
/**
 * Write a description of class PercolationStats here.
 * 
 * @author (Arjun Jauhari) 
 * @version (1.0)
 */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;
public class PercolationStatsQF
{
    // instance variables - replace the example below with your own
    private PercolationQF per;
    private double[] thresh;
    private int N,T,cnt;
    //private StdRandom rand;

    /**
     * Constructor for objects of class PercolationStats
     */
    public PercolationStatsQF(int N, int T)
    {
        if (N<=0 || T<=0) throw new java.lang.IllegalArgumentException("Not proper arguments");

        // initialise instance variables
        this.N = N;
        this.T = T;
        thresh = new double[T];
        int i,j;

        for (int l=0;l<T;l++) {
            per = new PercolationQF(N);
            //rand = new StdRandom();
            cnt = 0;
            while (!per.percolates()) {
                i = StdRandom.uniform(1,N+1);
                j = StdRandom.uniform(1,N+1);
                if (!per.isOpen(i, j)) {
                    per.open(i,j);
                    cnt++;
                }
            }
            thresh[l] = (double)cnt/(N*N);
        }
    }

    public double mean()                      // sample mean of percolation threshold
    {
        return StdStats.mean(thresh);
    }
    public double stddev()                    // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(thresh);
    }
    public double confidenceLo()              // low  endpoint of 95% confidence interval
    {
        return (mean() - (1.96*stddev()/Math.sqrt(T)));
    }
    public double confidenceHi()              // high endpoint of 95% confidence interval
    {
        return (mean() + (1.96*stddev()/Math.sqrt(T)));
    }

    public static void main(String[] args)    // test client (described below)
    {
        Stopwatch sw = new Stopwatch();
        PercolationStatsQF perStats = new PercolationStatsQF(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
        System.out.printf("%-23s = %f%n","Elapsed Time",sw.elapsedTime());
        System.out.printf("%-23s = %f%n","mean",perStats.mean());
        System.out.printf("%-23s = %f%n","stddev",perStats.stddev());
        System.out.printf("%-23s = %f, %f%n","95% confidence interval",perStats.confidenceLo(),perStats.confidenceHi());
    }
}
