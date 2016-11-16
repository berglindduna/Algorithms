package s4;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.ST;

public class WordNet {
	private final SAP s;
	private ST<String, Bag<Integer>> nouns = new ST<String, Bag<Integer>>();
	private ST<Integer,String> Synsets = new ST<Integer, String>();
	
	// constructor takes the name of the two input files
	public WordNet ( String synsets , String hypernyms ) {	
		In in = new In(synsets);
		
		nouns = new ST<String,Bag<Integer>>();
		Synsets = new ST<Integer,String>();
		
		int counter = 0;
		String line = in.readLine();
		
		while (line != null) {
			String[] fields = line.split(",");
			String[] strings = fields[1].split(" ");

			for (int i = 0; i < strings.length; i++) {		
				String string = strings[i];
				
				Bag<Integer> bag = new Bag<Integer>();
				
				if(nouns.contains(string))
					bag = nouns.get(string);

				bag.add(Integer.parseInt(fields[0]));
				nouns.put(string, bag);
			}
			
			Synsets.put(Integer.parseInt(fields[0]), fields[1]);
			line = in.readLine();
			counter++;
		}
		
		in = new In(hypernyms);
		Digraph G = new Digraph(counter + 1);	
		line = in.readLine();
		
		while (line != null) {
			String[] fields = line.split(",");
			
			for (int i = 1; i < fields.length; i++)
				G.addEdge(Integer.parseInt(fields[0]), Integer.parseInt(fields[i]));
			
			line = in.readLine();
		}
		
		s = new SAP(G);
		
		DirectedCycle cycle = new DirectedCycle(G);
		
		if (cycle.hasCycle())
			throw new java.lang.IllegalArgumentException();
	}
	
	// returns all WordNet nouns
	public Iterable < String > nouns () {
		return nouns.keys();
	}
	
	// is the word a WordNet noun ?
	public boolean isNoun ( String word ) {
		return nouns.contains(word);
	}

	// distance between nounA and nounB ( defined below )
	public int distance (String nounA , String nounB) {
		int theDist = 0;
		Bag<Integer> first;
		Bag<Integer> second;
		
		if (isNoun(nounA) && isNoun(nounB)) {
			first = nouns.get(nounA);
			second = nouns.get(nounB);
		}
		
		else
			throw new IllegalArgumentException();
		
		theDist = s.length(first, second);
		
		return theDist;
	}
	
	// a synset ( second field of synsets . txt ) that is a shortest common ancestor
	// of nounA and nounB
	public String sap ( String nounA , String nounB ) {
		int ancestor = 0;
		Bag<Integer> first;
		Bag<Integer> second;
		
		if (isNoun(nounA) && isNoun(nounB)) {
			first = nouns.get(nounA);
			second = nouns.get(nounB);
		}
		
		else
			throw new IllegalArgumentException();
		
		ancestor = s.ancestor(first, second);
		
		return Synsets.get(ancestor);
	}
	
	// do unit testing of this class
	public static void main ( String [] args ) {

	}

}
