package s2;

import java.util.Arrays;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Brute {
	public static void main(String[] args) {
        int N = StdIn.readInt();
        
        Point[] points = new Point[N];
        
        int x, y;
        
        // create N Points with integers x and y
        for (int i = 0; i < N; i++) {
        	x = StdIn.readInt();
        	y = StdIn.readInt();
            Point point = new Point(x, y);
            points[i] = point;
        }
        
        // sorts the array of points after y values (x values if the y values are equal)
        Arrays.sort(points);
       
        // compare slopes between p and q, p and r, p and s
        for (int p = 0; p < N; p++) {
        	
            for (int q = p + 1; q < N; q++) {
            	
            	double slopePtoQ = points[p].slopeTo(points[q]);
            	
                for (int r = q + 1; r < N; r++) {
                	// if the slopes between p and q and p and r are equal then check the last slope
                	
                	if (slopePtoQ == points[p].slopeTo(points[r])) {
                		
	                    for (int s = r + 1; s < N; s++) {
	                    	
	                        if (slopePtoQ == points[p].slopeTo(points[s])) {
	                            StdOut.println(points[p] + " -> " + points[q] + " -> " + points[r] + " -> " + points[s]);
	                        }
	                    }
                	}
                }
            }
        }
    }
}
