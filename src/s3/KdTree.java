package s3;


/*************************************************************************
 *************************************************************************/

import java.util.Arrays;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.SET;

public class KdTree {
	
    private static class Node {
        private Point2D point;
        private RectHV rectangle;
        private Node right_top;
        private Node left_bottom;

        public Node(Point2D point) {
            this.point = point;
        }
    }

    private Node root;
	private int sizeOfTree = 0;

    // construct an empty set of points
    public KdTree() {}

    // is the set empty?
    public boolean isEmpty() {
        return (root == null);
    }

    // number of points in the set
    public int size() {
        return sizeOfTree;
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
    	
        if (isEmpty()) {
            root = new Node(p);
            root.rectangle = new RectHV(0, 0, 1, 1);
            sizeOfTree ++;
        }
        
        else
        	root = insertPoint(root, p, 0);
    }

    private Node insertPoint(Node node, Point2D p, int pos) {
        if (node == null) {
            sizeOfTree ++;
            Node newNode = new Node(p);
            return newNode;
        }
        
        if (node.point.equals(p)) 
            return node;
        
        int temp;
        
        if  (pos%2 == 0)
        	temp = Double.compare(p.x(), node.point.x());
        else
        	temp = Double.compare(p.y(), node.point.y());
        
        int nextPos = pos+1;
        
        if (temp < 0) {
        	node.left_bottom = insertPoint(node.left_bottom, p, nextPos);
            
        	if (node.left_bottom.rectangle == null) {
        		
                if (pos%2 == 0) {
                	node.left_bottom.rectangle = new RectHV(node.rectangle.xmin(), node.rectangle.ymin(), node.point.x(), node.rectangle.ymax());
                } 
                
                else {
                	node.left_bottom.rectangle = new RectHV(node.rectangle.xmin(), node.rectangle.ymin(), node.rectangle.xmax(), node.point.y());
                }
            }
        } 
        
        else {
        	node.right_top = insertPoint(node.right_top, p, nextPos);
        	
            if (node.right_top.rectangle == null) {
               
            	if (pos%2 == 0) {
                	node.right_top.rectangle = new RectHV(node.point.x(), node.rectangle.ymin(), node.rectangle.xmax(), node.rectangle.ymax());
                } 
            	
            	else {
                	node.right_top.rectangle = new RectHV(node.rectangle.xmin(), node.point.y(), node.rectangle.xmax(), node.rectangle.ymax());
                }
            }
        }
        
        return node;
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
    	Node node = root;
		int pos = 0;
		
		while (node != null) {
			
			if (node.point.equals(p))
				return true;
			
			
			else if ((pos++ % 2) == 0) {
				if (p.x() < node.point.x())
					node = node.left_bottom;
				
				else
					node = node.right_top;
			}
			
			else {
				if (p.y() < node.point.y())
					node = node.left_bottom;
				
				else
					node = node.right_top;
			}
		}
		
		return false;
    }

    // draw all of the points to standard draw
    public void draw() {}

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        SET<Point2D> set = new SET<Point2D>();
    	
		if (!isEmpty()) 
			getPoints(set, rect, root);
		
		return set;
    }

    private void getPoints(SET<Point2D> set, RectHV rect, Node node) {
    	if (rect.intersects(node.rectangle)) {
    		
	        if (rect.contains(node.point)) 
	            set.add(node.point);
	        
	        if (node.left_bottom != null) 
	            getPoints(set, rect, node.left_bottom);
	        
	        if (node.right_top != null) 
	            getPoints(set, rect, node.right_top);
    	}
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
    	if (isEmpty())
    		return null;
        return nearestFind(root, p, root.point, 1, 0);
    }

    private Point2D nearestFind(Node node, Point2D p, Point2D nearestPoint, double closestDist, int pos) {
        if (node == null) 
            return nearestPoint;

        double curDist = p.distanceSquaredTo(node.point);
        
        if (curDist < closestDist) {
            nearestPoint = node.point;
            closestDist = curDist;
        }
        
        Node theFirst = node.left_bottom;
        Node theSecond = node.right_top;
        
        if (pos%2 == 0) {
        	
        	if (p.x() > node.point.x()) {
        		theFirst = node.right_top;
        		theSecond = node.left_bottom;
        	}
        }
        
        else {
        	
        	if (p.y() > node.point.y()) {
        		theFirst = node.right_top;
        		theSecond = node.left_bottom;
        	}
        }
        
        int pos2 = pos++;
        
        if (theFirst != null && theFirst.rectangle.distanceSquaredTo(p) < closestDist) {
            nearestPoint = nearestFind(theFirst, p, nearestPoint, closestDist,pos2);
            closestDist = nearestPoint.distanceSquaredTo(p);
        }
        
        if (theSecond != null && theSecond.rectangle.distanceSquaredTo(p) < closestDist) {
        	nearestPoint = nearestFind(theSecond, p, nearestPoint, closestDist, pos2);
        }

        return nearestPoint;
    }

    /*******************************************************************************
     * Test client
     ******************************************************************************/
    public static void main(String[] args) {	
    	In in = new In();
        Out out = new Out();
        
        int nrOfRecangles = in.readInt();
        int nrOfPointsCont = in.readInt();
        int nrOfPointsNear = in.readInt();
        
        RectHV[] rectangles = new RectHV[nrOfRecangles];
        Point2D[] pointsCont = new Point2D[nrOfPointsCont];
        Point2D[] pointsNear = new Point2D[nrOfPointsNear];
        
        for (int i = 0; i < nrOfRecangles; i++) {
            rectangles[i] = new RectHV(in.readDouble(), in.readDouble(),
                    in.readDouble(), in.readDouble());
        }
        
        for (int i = 0; i < nrOfPointsCont; i++) {
            pointsCont[i] = new Point2D(in.readDouble(), in.readDouble());
        }
        
        for (int i = 0; i < nrOfPointsNear; i++) {
            pointsNear[i] = new Point2D(in.readDouble(), in.readDouble());
        }
        
        KdTree set = new KdTree();
        
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble(), y = in.readDouble();
            set.insert(new Point2D(x, y));
        }
        
        for (int i = 0; i < nrOfRecangles; i++) {
            // Query on rectangle i, sort the result, and print
            Iterable<Point2D> ptset = set.range(rectangles[i]);
            int ptcount = 0;
            
            for (Point2D p : ptset)
                ptcount++;
            
            Point2D[] ptarr = new Point2D[ptcount];
            int j = 0;
            
            for (Point2D p : ptset) {
                ptarr[j] = p;
                j++;
            }
            
            Arrays.sort(ptarr);
            out.println("Inside rectangle " + (i + 1) + ":");
            
            for (j = 0; j < ptcount; j++)
                out.println(ptarr[j]);
        }
        
        out.println("Contain test:");
        
        for (int i = 0; i < nrOfPointsCont; i++) {
            out.println((i + 1) + ": " + set.contains(pointsCont[i]));
        }

        out.println("Nearest test:");
        
        for (int i = 0; i < nrOfPointsNear; i++) {
            out.println((i + 1) + ": " + set.nearest(pointsNear[i]));
        }

        out.println();
    }
}
