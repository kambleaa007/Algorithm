/* *****************************************************************************
 *  Name: Ada Lovelace
 *  Date: Sept 20, 2023
 *  Description: Percolation
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] array;
    private WeightedQuickUnionUF wf;
    private final int num;
    private final int start;
    private final int end;

    private int opened = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 0) throw new IllegalArgumentException("Value of n is less than zero");
        array = new boolean[n][n];
        int row, col;
        for (row = 0; row < n; row++) {
            for (col = 0; col < n; col++) {
                array[row][col] = false;
            }
        }
        num = n;
        start = num * num;
        end = num * num + 1;
        wf = new WeightedQuickUnionUF(n * n + 2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int val;
        row = row - 1;
        col = col - 1;

        if (row >= num || row < 0 || col >= num || col < 0)
            throw new IllegalArgumentException("Enter a valid argument");

        if (!array[row][col]) {
            array[row][col] = true;
            val = row * num + col;

            if (row > 0 && array[row - 1][col]) wf.union(val, val - num);
            if (col > 0 && array[row][col - 1]) wf.union(val, val - 1);
            if (row < num - 1 && array[row + 1][col]) wf.union(val, val + num);
            if (col < num - 1 && array[row][col + 1]) wf.union(val, val + 1);

            if (val < num) wf.union(val, start);
            if (val >= num * num - num) wf.union(val, end);

            opened++;

        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        row = row - 1;
        col = col - 1;

        if (row >= num || row < 0 || col >= num || col < 0)
            throw new IllegalArgumentException("Enter a valid argument");

        return array[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        row = row - 1;
        col = col - 1;

        if (row >= num || row < 0 || col >= num || col < 0)
            throw new IllegalArgumentException("Enter a valid argument");

        return (wf.connected((row * num + col), start) && array[row][col]);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opened;
    }

    // does the system percolate?
    public boolean percolates() {
        return wf.connected(start, end);
    }

    // public static void main(String[] args) {
    //     System.out.println("Percolation");
    // }
}
