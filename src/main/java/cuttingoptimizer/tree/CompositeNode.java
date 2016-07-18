package cuttingoptimizer.tree;

import cuttingoptimizer.Piece;

public abstract class CompositeNode extends Node {

	public Node c1;
	public Node c2;

	public CompositeNode(Node c1, Node c2) {
		this.c1 = c1;
		this.c2 = c2;
	}

	@Override
	public double fitFactor(Piece p) {
		return Math.max(c1.fitFactor(p), c2.fitFactor(p));
	}

}
