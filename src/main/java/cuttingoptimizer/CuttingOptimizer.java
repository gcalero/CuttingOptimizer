package cuttingoptimizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CuttingOptimizer {

	private Settings mSettings;

	private List<Piece> mPieces;

	public CuttingOptimizer() {
		mPieces = new ArrayList<>();
	}

	public void setup(Settings settings) {
		mSettings = settings;
	}

	public void addPiece(double width, double height) {
		mPieces.add(new Piece(width, height));
	}

	public CuttingResult calculate() {
		// Sort the array of pieces from biggest to smallest
		Collections.sort(mPieces, new PieceSizeComparator(true));

		List<Sheet> sheets = new ArrayList<>();
		double maxFactor = 0;
		Sheet sheetWithMaxFactor = null;
		// Start arranging the pieces into the sheets
		List<Piece> notFitting = new ArrayList<>();

		for (Piece p : mPieces) {
			for (Sheet s : sheets) {
				double factor = s.fitFactor(p);
				if (sheetWithMaxFactor == null || factor > maxFactor) {
					maxFactor = factor;
					sheetWithMaxFactor = s;
				}
			}
			if (sheetWithMaxFactor == null) {
				if (Sheet.fits(mSettings, p)) {
					sheetWithMaxFactor = new Sheet(mSettings);
					sheets.add(sheetWithMaxFactor);
				} else {
					notFitting.add(p);
					continue;
				}
			}
			
			sheetWithMaxFactor.arrange(p);
			checkResult(sheets); // debug
		}

		return new CuttingResult(sheets, notFitting);
	}

	/**
	 * For debug only
	 * 
	 * @param sheets
	 */
	private void checkResult(List<Sheet> sheets) {
		// obtener los paneles de cada sheet
		// en cada sheet: (X21 < X11 < X22 v X21 < X12 < X22) ^ idem con Y
		for (Sheet s : sheets) {
			// get the result as it is the final result
			Panel[] panels = s.getPanels();
			checkNoIntersection(panels);
		}

	}

	private void checkNoIntersection(Panel[] panels) {
		for (Panel p1 : panels) {
			double x11 = p1.x1;
			double x12 = p1.x2;
			double y11 = p1.y1;
			double y12 = p1.y2;
			for (Panel p2 : panels) {
				if (p1 != p2) {
					double x21 = p2.x1;
					double y21 = p2.y1;
					if (x21 > x11 && x21 < x12 && y21 > y11 && y21 < y12) {
						throw new RuntimeException("Overlaping " + p1 + " and "
								+ p2);
					}
				}
			}
		}

	}

}
