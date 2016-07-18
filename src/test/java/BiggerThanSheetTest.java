import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cuttingoptimizer.CuttingOptimizer;
import cuttingoptimizer.CuttingResult;
import cuttingoptimizer.Panel;
import cuttingoptimizer.Settings;

public class BiggerThanSheetTest {

	@Test
	public void testPieceWiderThanSheet() {
		Settings settings = new Settings();
		settings.setSheetSize(1000d, 1100d);

		CuttingOptimizer optimizer = new CuttingOptimizer();
		optimizer.setup(settings);

		optimizer.addPiece(1100d, 500d); // it is wider than the sheet
		optimizer.addPiece(1000d, 110d);

		CuttingResult r = optimizer.calculate();

		assertEquals(1, r.getAmountOfSheets());

		Panel[] panels = r.getSheet(0).getPanels();
		assertEquals(2, panels.length);

		assertEquals(0.0, panels[0].x1, 0.0f);
		assertEquals(0.0, panels[0].y1, 0.0f);
		assertEquals(1000.0, panels[0].x2, 0.0f);
		assertEquals(110.0, panels[0].y2, 0.0f);
		assertFalse(panels[0].isFree());
		assertTrue(panels[1].isFree());
	}

	@Test
	public void testPieceTallerThanSheet() {
		Settings settings = new Settings();
		settings.setSheetSize(500d, 1000d);

		CuttingOptimizer optimizer = new CuttingOptimizer();
		optimizer.setup(settings);

		optimizer.addPiece(400d, 1500d); // it is taller than the sheet
		optimizer.addPiece(250d, 500d);

		CuttingResult r = optimizer.calculate();

		assertEquals(1, r.getAmountOfSheets());

		Panel[] panels = r.getSheet(0).getPanels();
		assertEquals(3, panels.length);

		assertEquals(0.0, panels[0].x1, 0.0f);
		assertEquals(0.0, panels[0].y1, 0.0f);
		assertEquals(250.0, panels[0].x2, 0.0f);
		assertEquals(500.0, panels[0].y2, 0.0f);
		assertFalse(panels[0].isFree());
		assertTrue(panels[1].isFree());
		assertTrue(panels[2].isFree());
	}

}
