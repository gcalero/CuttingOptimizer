package cuttingoptimizer;

import java.util.List;

public class CuttingResult {

	private List<Sheet> mSheets;

	private List<Piece> mNotFitting;

	public CuttingResult(List<Sheet> sheets, List<Piece> notFitting) {
		mSheets = sheets;
		mNotFitting = notFitting;
	}

	public int getAmountOfSheets() {
		return mSheets.size();
	}

	public Sheet getSheet(int i) {
		return mSheets.get(i);
	}

	/**
	 * For debug only
	 */
	public void printResult() {
		String json = "[";
		for (int i = 0; i < getAmountOfSheets(); i++) {
			Sheet sheet = getSheet(i);
			json += "{ \"width\": " + sheet.getWidth() + ", \"height\":"
					+ sheet.getHeight() + ", \"panels\": [";
			Panel[] panelsOfSheet = sheet.getPanels();
			for (int j = 0; j < panelsOfSheet.length; j++) {
				Panel p = panelsOfSheet[j];
				json += "{\"x1\":" + p.x1 + ", \"y1\":" + p.y1 + ", \"x2\": "
						+ p.x2 + ", \"y2\":" + p.y2 + ", \"free\":"
						+ p.isFree() + "}";
				if (j < panelsOfSheet.length - 1) {
					json += ",";
				}
			}
			json += "]}";
			if (i < getAmountOfSheets() - 1) {
				json += ",";
			}
		}
		json += "]";

		System.out.println(json);
	}

	public List<Piece> getNotFitting() {
		return mNotFitting;
	}

}
