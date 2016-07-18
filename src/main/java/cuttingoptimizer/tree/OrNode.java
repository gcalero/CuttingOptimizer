package cuttingoptimizer.tree;

import cuttingoptimizer.Panel;
import cuttingoptimizer.Piece;

public class OrNode extends CompositeNode {

	public OrNode(Node c1, Node c2) {
		super(c1, c2);
	}

	@Override
	public Node arrange(Piece p) {
		// We have to arrange p only into one of Ci and discard the others
		// TODO later: return an array of Nodes (check AndNode for more details)
		double f1 = c1.fitFactor(p);
		double f2 = c2.fitFactor(p);

		if (f1 == 0 && f2 == 0) {
			return null;
		} else if (f1 >= f2) {
			return c1.arrange(p); // c2 is discarded here
		} else if (f2 > f1) {
			return c2.arrange(p);
		}
		return null;
	}

	@Override
	public Panel[] getPanels() {
		return c1.getPanels();
	}

	@Override
	public String toString() {
		return "( " + c1 + " OR " + c2 + ")";
	}

}
