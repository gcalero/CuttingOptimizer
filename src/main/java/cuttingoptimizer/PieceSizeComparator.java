package cuttingoptimizer;

import java.util.Comparator;

public class PieceSizeComparator implements Comparator<Piece> {

	private final int decreasingFactor;

	public PieceSizeComparator(boolean decreasing) {
		decreasingFactor = decreasing ? -1 : 1;
	}

	public int compare(Piece c1, Piece c2) {
		if (c1 == null && c2 == null)
			return 0;
		if (c1 != null && c2 == null)
			return -1;
		if (c1 == null && c2 != null)
			return 1;

		if (c1.width != c2.width) {
			return (int) ((c1.width - c2.width) * decreasingFactor);
		} else {
			return (int) ((c1.height - c2.height) * decreasingFactor);
		}
	}
}
