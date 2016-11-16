package D0;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class Drawing {

	public static void main(String[] args) {
		StdDraw.line(StdRandom.uniform(), StdRandom.uniform(), StdRandom.uniform(), StdRandom.uniform());
		StdDraw.setPenColor(5, 159, 190);
		StdDraw.rectangle(StdRandom.uniform(), StdRandom.uniform(), 0.1, 0.2);
		StdDraw.setPenRadius(0.008);

		for (int i = 0; i < 5; i++) {
			StdDraw.setPenColor(StdRandom.uniform(255), StdRandom.uniform(255), StdRandom.uniform(255));
			StdDraw.circle(StdRandom.uniform(), StdRandom.uniform(), 0.2);
		}

		StdDraw.setPenColor(45, 169, 200);
		StdDraw.filledRectangle(StdRandom.uniform(), StdRandom.uniform(), 0.2, 0.1);
		StdDraw.filledCircle(StdRandom.uniform(), StdRandom.uniform(), 0.2);
		StdDraw.text(StdRandom.uniform(), StdRandom.uniform(), "Hello");
	}

}
