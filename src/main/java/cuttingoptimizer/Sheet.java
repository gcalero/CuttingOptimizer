package cuttingoptimizer;

import cuttingoptimizer.tree.Leaf;
import cuttingoptimizer.tree.Node;

public class Sheet {

	private Node root;
	private double mWidth;
	private double mHeight;

	public Sheet(Settings settings) {
		mWidth = settings.getWidth();
		mHeight = settings.getHeight();
		Panel p = new Panel(0, 0, mWidth, mHeight);
		p.setFree(true);
		root = new Leaf(p);
	}

	public double fitFactor(Piece p) {
		return root.fitFactor(p);
	}

	public Node arrange(Piece p) {
		Node arranged = root.arrange(p);
		if (arranged != null) {
			root = arranged;
		}
		return arranged;
	}

	public Panel[] getPanels() {
		return root.getPanels();
	}

	@Override
	public String toString() {
		return "Sheet " + hashCode() + " root: {" + root + "}";
	}

	public double getWidth() {
		return mWidth;
	}

	public double getHeight() {
		return mHeight;
	}

	public static boolean fits(Settings mSettings, Piece p) {
		return p.width <= mSettings.getWidth()
				&& p.height <= mSettings.getHeight();
	}

}
