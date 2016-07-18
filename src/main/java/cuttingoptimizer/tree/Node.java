package cuttingoptimizer.tree;

import cuttingoptimizer.Panel;
import cuttingoptimizer.Piece;

public abstract class Node {

	public abstract double fitFactor(Piece p);

	public boolean fits(Piece p) {
		return fitFactor(p) > 0;
	}

	public abstract Node arrange(Piece p);

	public abstract Panel[] getPanels();

}
