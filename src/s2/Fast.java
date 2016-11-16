package s2;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Fast {
    public static void main(String[] args) {
        
    	int N = StdIn.readInt();
    	
    	Point[] points = new Point[N];
    	
    	int x,y;

    	for (int i = 0; i < N; i++) {	
    		x = StdIn.readInt();
    		y = StdIn.readInt();
    		Point point = new Point(x,y);
    		points[i] = point;
    	}
    	
    	Arrays.sort(points); 
    	Point[] copyPoints = new Point[N-1];
    	int copyPoints_length = 0;
    	
    	int count, pos;
    	double theSlope;
 
         for (int i = 0; i < N-3; i++){  
             Point thePoint = points[i];          
             copyPoints_length = N-1-i;
             
             for (int j = 1; j < N-i; j++) {
            	 copyPoints[j-1] = points[j+i];
             }
                    
             Arrays.sort(copyPoints,0, copyPoints_length, thePoint.SLOPE_ORDER); 
             
             count = 0;  
             pos = 0;  
            
             theSlope = thePoint.slopeTo(copyPoints[0]);  
             
             for (int j = 0; j < copyPoints_length; j++){  
            	 
            	 if (thePoint.slopeTo(copyPoints[j]) == theSlope) {
            		 count++;
            	 }
                 
                 else {  
                	 
                     if (count >= 3){ 
                    	 
                    	 if (copyPoints[pos].compareTo(thePoint) >= 0) {
                    		 StdOut.print(thePoint + " -> ");
                    		 
                    		 for (int k = pos; k < j-1; k++) {
                    			 StdOut.print(copyPoints[k] + " -> ");
                    		 }
                    		 
                    		 StdOut.println(copyPoints[j-1]);
   	
                    	 }
                     }
  
                     count = 1;  
                     pos = j;  
                     theSlope = thePoint.slopeTo(copyPoints[j]);  
                 }
            	 
             }
             
             if (count >= 3){ 
            	 
            	 if (copyPoints[pos].compareTo(thePoint) >= 0) {
            		 StdOut.print(thePoint + " -> ");
            		 
            		 for (int k = pos; k < copyPoints_length-1; k++) {
            			 StdOut.print(copyPoints[k] + " -> ");
            		 }
            		 
            		 StdOut.println(copyPoints[copyPoints_length-1]);
            	 }
             }
             
         } 
    }
}