/* *****************************************************************************
 *  Name: Ada Lovelace
 *  Date: Sept 20, 2023
 *  Description: PercolationStats
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENT = 1.96;
    private final double[] value;
    private double m, std;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        int row, col;
        double d;
        value = new double[trials];

        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Value of n or trials is less than zero");

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);

            while (!p.percolates()) {
                row = StdRandom.uniformInt(1, n + 1);
                col = StdRandom.uniformInt(1, n + 1);
                p.open(row, col);
            }
            d = p.numberOfOpenSites();
            value[i] = d / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        m = StdStats.mean(value);
        return m;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        std = StdStats.stddev(value);
        return std;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (m + (CONFIDENT * std) / Math.sqrt(value.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (m - (CONFIDENT * std) / Math.sqrt(value.length));
    }

    // test client (see below)
    public static void main(String[] args) {
        // System.out.println("PercolationStats");
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, t);

        StdOut.printf("mean                    = %f\n", ps.mean());
        StdOut.printf("stddev                  = %f\n", ps.stddev());
        StdOut.printf("95%% confidence interval = [%f, %f]", ps.confidenceLo(), ps.confidenceHi());
    }

}



