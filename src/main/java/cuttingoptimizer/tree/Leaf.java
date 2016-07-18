package cuttingoptimizer.tree;

import cuttingoptimizer.Panel;
import cuttingoptimizer.Piece;

public class Leaf extends Node {

	public Panel data;

	public Leaf(Panel data) {
		this.data = data;
	}

	@Override
	public double fitFactor(Piece p) {
		if (!data.isFree())
			return 0;
		double availableWidth = data.x2 - data.x1;
		double availableHeight = data.y2 - data.y1;
		if (availableWidth < p.width || availableHeight < p.height)
			return 0;
		if (availableWidth == p.width && availableHeight == p.height)
			return Double.MAX_VALUE;

		double remainingW = availableWidth - p.width;
		double remainingH = availableHeight - p.height;

		// a factor to minimize remainder
		return 1.0d / (remainingW + remainingH);
	}

	public Node arrange(Piece p) {
		if (fitFactor(p) == 0)
			return null;
		Panel arrangedPanel = new Panel(data.x1, data.y1, data.x1 + p.width,
				data.y1 + p.height);
		arrangedPanel.setFree(false);
		Node arranged = new Leaf(arrangedPanel);
		Node remainingNode = getAllPartitions(data, arrangedPanel);
		if (remainingNode != null) {
			return new AndNode(arranged, remainingNode);
		} else {
			return arranged;
		}
	}

	private Node getAllPartitions(Panel original, Panel arranged) {
		double remainingWidth = original.x2 - arranged.x2;
		double remainingHeight = original.y2 - arranged.y2;

		if (remainingWidth == 0 && remainingHeight == 0) {
			// there is no remainder to partition
			return null;
		} else if (remainingWidth == 0) {
			Panel p = new Panel(original.x1, arranged.y2, original.x2,
					original.y2);
			p.setFree(true);
			return new Leaf(p);
		} else if (remainingHeight == 0) {
			Panel p = new Panel(arranged.x2, original.y1, original.x2,
					original.y2);
			p.setFree(true);
			return new Leaf(p);
		} else {
			return new OrNode(divideRemainder(original, arranged, 1),
					divideRemainder(original, arranged, 2));
		}
	}

	private AndNode divideRemainder(Panel original, Panel arranged,
			int partitionType) {
		double x1 = original.x1;
		double x2 = original.x2;
		double y1 = original.y1;
		double y2 = original.y2;

		double xm = arranged.x2;
		double ym = arranged.y2;

		assert x1 < xm && xm < x2;
		assert y1 < ym && ym < y2;

		Panel p1, p2;
		if (partitionType == 1) {
			p1 = new Panel(xm, y1, x2, ym);
			p2 = new Panel(x1, ym, x2, y2);
		} else {
			p1 = new Panel(xm, y1, x2, y2);
			p2 = new Panel(x1, ym, xm, y2);
		}
		p1.setFree(true);
		p2.setFree(true);
		Leaf l1 = new Leaf(p1);
		Leaf l2 = new Leaf(p2);

		AndNode and = new AndNode(l1, l2);
		return and;
	}

	@Override
	public Panel[] getPanels() {
		return new Panel[] { data };
	}

	@Override
	public String toString() {
		return "Leaf " + data;
	}
}
