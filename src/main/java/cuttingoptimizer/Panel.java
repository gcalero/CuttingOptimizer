package cuttingoptimizer;

public class Panel {

	public double x1;
	public double y1;
	public double x2;
	public double y2;
	private boolean mFree;

	public Panel(double x1, double y1, double x2, double y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public void setFree(boolean free) {
		mFree = free;
	}

	public boolean isFree() {
		return mFree;
	}

	@Override
	public String toString() {
		return "[" + x1 + "," + y1 + "," + x2 + "," + y2 + "," + mFree + "]";
	}

}
