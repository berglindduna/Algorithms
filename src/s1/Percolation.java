package s1;

//import edu.princeton.cs.algs4.StdIn;
//import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	// creates N-by-N grid with all sides initially blocked
	
	private final int size;
    private boolean[][] grid; // size-by-size grid of boolean values
    // false = blocked, true = open
    private final int top;
    private final int bottom;
    private final WeightedQuickUnionUF qu;
    private int count;

    public Percolation (int N) {
    	// N needs to be > 0
    	
    	if (N <= 0) throw new java.lang.IllegalArgumentException(); // N has to be a positive number
        size = N;
        grid = new boolean[size][size];
        top = 0; // imaginary site before the first site
        bottom = (size * size) + 1; // imaginary site after the last site
        qu = new WeightedQuickUnionUF((size * size) + 2); // qu has size*size sites + 2 imaginary sites before the first and last
        count = 0;
    }

    public void open (int row, int col) { 
    	// isOpen makes sure that row and col are inside their prescribed range
    	// opens grid[row][col] if not opened
    	// connects the site to the neighbour sites (left,right,up,down) if open
    	
    	if (!isOpen(row, col)) {
            int index = getIndex(row, col);

            if (row == 0) qu.union(top, index); //top row
            if (row == (size - 1)) qu.union(bottom,  index); //bottom row
            
            if ((row + 1) < size) connectNeighbour(index, row + 1, col); // down neighbour
            if ((row - 1) >= 0) connectNeighbour(index, row - 1, col); // up neighbour
            if ((col + 1) < size) connectNeighbour(index, row, col + 1); // right neigbour
            if ((col - 1) >= 0) connectNeighbour(index, row, col - 1); // left neighbour

            grid[row][col] = true;
            count ++;
    	}
    }

    public boolean isOpen (int row, int col) {
    	// returns true if grid[row][col] is open
    	// Throws an exception if index is out of bounds
    	// 0 <= row&col < size
  
    	if ( row < 0 || row > (size - 1) || col < 0 || col > (size - 1) ) {
    		throw new java.lang.IndexOutOfBoundsException();
    	}
    	else {
    		return grid[row][col];
    	}
    }

    public boolean isFull (int row, int col) {
    	// returns true if the corresponding site is in the same component as the virtual top cell
    	// isOpen makes sure that row and col are inside their prescribed range
    	
    	if (isOpen(row, col)) {
    		return qu.connected(top, getIndex(row, col));
    	}
    	return false;
    }
    
    public int numberOfOpenSites() {
    	// returns how many elements in grid are open
    	
    	return count;
    }

    public boolean percolates() {
    	// returns true if the virtual top site and the virtual bottom site are in the same component
    	
    	return qu.connected(top, bottom);
    }

    private void connectNeighbour(int index, int row, int col) {
    	// puts qu[index] in the same component as the site corresponding to grid[row][col] if its open
       	
    	if (isOpen(row, col)) qu.union(getIndex(row, col), index);
    }
    
    private int getIndex(int row, int col) {
    	// returns the value of the side in qu corresponding to grid[row][col]
    	
    	return (row * size + col + 1);
    }
    
    public static void main (String[] args) { //unit testing
    	
    	/*PercolationTests t = new PercolationTests();
    	t.testPercolation();
    	t.testIsOpen();
    	t.testOpen();
    	t.testIsFull();
    	t.testNumberOfOpenSites();
    	t.testPercolates();*/
    	
    	/*int N = StdIn.readInt();
    	int T = StdIn.readInt();
    	
        PercolationStats percStats = new PercolationStats(N, T);
        
        StdOut.println("mean()           = " + percStats.mean());
        StdOut.println("stddev()         = " + percStats.stddev());
        StdOut.println("confidenceLow()  = " + percStats.confidenceLow());
        StdOut.println("confidenceHigh() = " + percStats.confidenceHigh());*/
	}
}