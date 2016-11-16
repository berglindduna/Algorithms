package s4;

//import edu.princeton.cs.algs4.In;
//import edu.princeton.cs.algs4.StdOut;

public class Outcast {
	private WordNet theNet;
	
	// constructor takes a WordNet object
	public Outcast ( WordNet wordnet ) {
		theNet = wordnet;
	}
	
	// given an array of WordNet nouns , return an outcast
	public String outcast ( String [] nouns ) {
		String string = null;
		int maxLength = 0;
		
		for (int i = 0; i < (nouns.length); i++) {
			int sum = 0;
			
			for (int j = 0; j < (nouns.length); j++)
				sum += theNet.distance(nouns[i], nouns[j]);
			
			if (sum > maxLength) {
				maxLength = sum;
				string = nouns[i];	
			}
 		}
		
		return string;
	}
	
	public static void main ( String [] args ) {
		/* WordNet wordnet = new WordNet ( "src/s4/synsets.txt" , "src/s4/hypernyms.txt") ;
		 Outcast outcast = new Outcast ( wordnet );
		 
		 In in = new In("src/s4/outcast.txt");
		 String nouns[] = in.readLine().split(",");
		 
		 StdOut . println ( outcast . outcast ( nouns ));*/
	}


}
