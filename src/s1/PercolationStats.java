package s1;

import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

    private double mean;
    private double stddev;
    private double confidenceLow;
    private double confidenceHigh;

    public PercolationStats (int N, int T) { // perform T independent experiments on an N-by-N grid
        if ((N <= 0) || (T <= 0)) throw new IllegalArgumentException();
  
        double[] thresholds = new double[T];
        int col, row;
        for (int i = 0; i < T; i++) {
            Percolation per = new Percolation(N);
            
            while (!per.percolates()) {
            	col = StdRandom.uniform(N);
            	row = StdRandom.uniform(N);
                
                while (per.isOpen(row, col)) {
                	col = StdRandom.uniform(N);
                	row = StdRandom.uniform(N);
                }

                per.open(row, col);
            }
            
            double doubN = N;
            thresholds[i] = per.numberOfOpenSites() / (doubN * doubN);
        }
        
        mean = StdStats.mean(thresholds);
        stddev = StdStats.stddev(thresholds);
        confidenceLow = mean - ((1.96 * stddev) / Math.sqrt(T));
        confidenceHigh = mean + ((1.96 * stddev) / Math.sqrt(T));
    }

    public double mean() {return mean;} // sample mean of percolation threshold 
    public double stddev() {return stddev;} // sample standard deviation of percolation threshold
    public double confidenceLow() {return confidenceLow;} // low  endpoint of 95% confidence interval
    public double confidenceHigh() {return confidenceHigh;} // high endpoint of 95% confidence interval
 
}