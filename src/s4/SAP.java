package s4;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class SAP {
	
	private final Digraph digraph;
	
	// constructor takes a digraph ( not necessarily a DAG )
	public SAP (Digraph G) {
		
		digraph = new Digraph(G);
		
		DirectedCycle cycle = new DirectedCycle(digraph);
		
		if (cycle.hasCycle()) 
			throw new IllegalArgumentException("Graph is not acyclic");

		int a = 0;
		
		for (int i = 0; i < digraph.V(); i++) {
			if (!digraph.adj(i).iterator().hasNext()) 
				a++;
			
			if (a > 1)
				throw new IllegalArgumentException("Graph is not rooted");
		}
		
		if (a == 0) 
			throw new IllegalArgumentException("Graph is not acyclic");
			

	}
	
	private void checkRange (int v) {
		if (v > (digraph.V() - 1))
			throw new java.lang.IndexOutOfBoundsException();
	}
	
	private void checkRange (Iterable <Integer> A) {
		for (Integer i: A)
			checkRange(i);
	}
	
	// length of shortest ancestral path between v and w; -1 if no such path
	public int length ( int v , int w) {
		checkRange(v);
		checkRange(w);
		List<Integer> vList = new ArrayList<Integer>();
		List<Integer> wList = new ArrayList<Integer>();
		vList.add(v);
		wList.add(w);
		return (length(vList, wList));
	}
	
	// a shortest common common ancestor of v and w; -1 if no such path
	public int ancestor ( int v , int w) {
		checkRange(v);
		checkRange(w);
		List<Integer> vList = new ArrayList<Integer>();
		List<Integer> wList = new ArrayList<Integer>();
		vList.add(v);
		wList.add(w);
		return (ancestor(vList, wList));
	}
	
	// length of shortest ancestral path of vertex subsets A and B ; -1 if no such path
	public int length ( Iterable < Integer > A , Iterable < Integer > B) {
		checkRange(A);
		checkRange(B);
		shortestPath temp = new shortestPath(digraph, A, B);
		return (temp.getMinLength());
	}
	
	// a shortest common ancestor of vertex subsets A and B; -1 if no such path
	public int ancestor ( Iterable < Integer > A , Iterable < Integer > B) {
		checkRange(A);
		checkRange(B);
		shortestPath temp = new shortestPath(digraph, A, B);
		return (temp.getAncestor());
	}
	
	// do unit testing of this class
	 public static void main(String[] args) throws FileNotFoundException {
		 In in = new In(args[0]);
		 Digraph G = new Digraph(in);
		 SAP s = new SAP(G);
		 int v, w, length, ancestor;
		 
		 while (!in.isEmpty()) {
			 v = in.readInt();
	         w = in.readInt();
	         length   = s.length(v, w);
	         ancestor = s.ancestor(v, w);
	         
	         StdOut.println("length " + length);
	         StdOut.println("ancestor " + ancestor);
       }
	 }

}

class shortestPath {
	private int V;
	private int ancestor;
	private int minLength;
	private BreadthFirstDirectedPaths a, b;
	
		
	public shortestPath (Digraph G, Iterable<Integer> A, Iterable<Integer> B) {
		minLength = -1;
		ancestor = -1;
		V = G.V();
		a = new BreadthFirstDirectedPaths(G, A);
		b = new BreadthFirstDirectedPaths(G, B);
		 
		for (int i = 0; i < V; i++) {
			
			if (a.hasPathTo(i) && b.hasPathTo(i)) {
				
				int temp = a.distTo(i) + b.distTo(i);
				
				if (minLength < 0 || minLength >= temp) {
					minLength = temp;
					ancestor = i;
				}
			}
		}
	}
	
	public int getAncestor() {return ancestor;}
	
	public int getMinLength() {return minLength;}
}

