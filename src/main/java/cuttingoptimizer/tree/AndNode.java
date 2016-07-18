package cuttingoptimizer.tree;

import cuttingoptimizer.Panel;
import cuttingoptimizer.Piece;

public class AndNode extends CompositeNode {

	public AndNode(Node c1, Node c2) {
		super(c1, c2);
	}

	@Override
	public Node arrange(Piece p) {
		// TODO later: arrange will return an array with all possibilities
		// regardles of the fit factor.
		// Take this array and combine it with the other Ci.
		// Do the same with each Ci whose fit factor > 0
		double f1 = c1.fitFactor(p);
		double f2 = c2.fitFactor(p);

		if (f1 == 0 && f2 == 0) {
			return null;
		} else if (f1 >= f2) {
			return new AndNode(c1.arrange(p), c2);
		} else if (f2 > f1) {
			return new AndNode(c1, c2.arrange(p));
		}
		return null;
	}

	@Override
	public Panel[] getPanels() {
		Panel[] c1Panels = c1.getPanels();
		Panel[] c2Panels = c2.getPanels();
		Panel[] panels = new Panel[c1Panels.length + c2Panels.length];
		System.arraycopy(c1Panels, 0, panels, 0, c1Panels.length);
		System.arraycopy(c2Panels, 0, panels, c1Panels.length, c2Panels.length);
		return panels;
	}

	@Override
	public String toString() {
		return "( " + c1 + " AND " + c2 + ")";
	}

}
