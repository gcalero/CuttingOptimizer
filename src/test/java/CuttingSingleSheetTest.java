import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cuttingoptimizer.CuttingOptimizer;
import cuttingoptimizer.CuttingResult;
import cuttingoptimizer.Panel;
import cuttingoptimizer.Piece;
import cuttingoptimizer.Settings;

public class CuttingSingleSheetTest {

	@Test
	public void testHorizontalHalves() {
		Settings settings = new Settings();
		settings.setSheetSize(1000d, 1000d);

		CuttingOptimizer optimizer = new CuttingOptimizer();
		optimizer.setup(settings);

		optimizer.addPiece(1000d, 500d);
		optimizer.addPiece(1000d, 500d);

		CuttingResult r = optimizer.calculate();

		assertEquals(1, r.getAmountOfSheets());

		Panel[] panels = r.getSheet(0).getPanels();
		assertEquals(2, panels.length);

		for (int i = 0; i < 2; i++) {
			assertEquals(0d, panels[i].x1, 0.0f);
			assertEquals(i * 500d, panels[i].y1, 0.0f);
			assertEquals(1000d, panels[i].x2, 0.0f);
			assertEquals((i + 1) * 500d, panels[i].y2, 0.0f);
		}
	}

	@Test
	public void testVerticalHalves() {
		Settings settings = new Settings();
		settings.setSheetSize(1000d, 1000d);

		CuttingOptimizer optimizer = new CuttingOptimizer();
		optimizer.setup(settings);

		optimizer.addPiece(500d, 1000d);
		optimizer.addPiece(500d, 1000d);

		CuttingResult r = optimizer.calculate();

		assertEquals(1, r.getAmountOfSheets());

		Panel[] panels = r.getSheet(0).getPanels();
		assertEquals(2, panels.length);

		for (int i = 0; i < 2; i++) {
			assertEquals(i * 500d, panels[i].x1, 0.0f);
			assertEquals(0d, panels[i].y1, 0.0f);
			assertEquals((i + 1) * 500d, panels[i].x2, 0.0f);
			assertEquals(1000d, panels[i].y2, 0.0f);
		}
	}

	@Test
	public void testHorizontalThirds() {
		Settings settings = new Settings();
		settings.setSheetSize(1000d, 1200d);

		CuttingOptimizer optimizer = new CuttingOptimizer();
		optimizer.setup(settings);

		optimizer.addPiece(1000d, 400d);
		optimizer.addPiece(1000d, 400d);
		optimizer.addPiece(1000d, 400d);

		CuttingResult r = optimizer.calculate();

		assertEquals(1, r.getAmountOfSheets());

		Panel[] panels = r.getSheet(0).getPanels();
		assertEquals(3, panels.length);

		for (int i = 0; i < 3; i++) {
			assertEquals(0d, panels[i].x1, 0.0f);
			assertEquals(i * 400d, panels[i].y1, 0.0f);
			assertEquals(1000d, panels[i].x2, 0.0f);
			assertEquals((i + 1) * 400d, panels[i].y2, 0.0f);
		}
	}

	@Test
	public void testVerticalThirds() {
		Settings settings = new Settings();
		settings.setSheetSize(1200d, 1000d);

		CuttingOptimizer optimizer = new CuttingOptimizer();
		optimizer.setup(settings);

		optimizer.addPiece(400d, 1000d);
		optimizer.addPiece(400d, 1000d);
		optimizer.addPiece(400d, 1000d);

		CuttingResult r = optimizer.calculate();

		assertEquals(1, r.getAmountOfSheets());

		Panel[] panels = r.getSheet(0).getPanels();
		assertEquals(3, panels.length);

		for (int i = 0; i < 3; i++) {
			assertEquals(i * 400d, panels[i].x1, 0.0f);
			assertEquals(0d, panels[i].y1, 0.0f);
			assertEquals((i + 1) * 400d, panels[i].x2, 0.0f);
			assertEquals(1000d, panels[i].y2, 0.0f);
		}
	}

	@Test
	public void testIrregularPiecesWithRemainder() {
		Settings settings = new Settings();
		settings.setSheetSize(1500d, 1000d);

		CuttingOptimizer optimizer = new CuttingOptimizer();
		optimizer.setup(settings);

		List<Piece> pieces = new ArrayList<>();
		pieces.add(new Piece(1000, 500));
		pieces.add(new Piece(500, 1000));
		pieces.add(new Piece(500, 300));

		for (Piece p : pieces) {
			optimizer.addPiece(p.width, p.height);
		}

		CuttingResult r = optimizer.calculate();
		assertEquals(1, r.getAmountOfSheets());
		assertEquals(1500d, r.getSheet(0).getWidth(), 0.0f);
		assertEquals(1000d, r.getSheet(0).getHeight(), 0.0f);

		List<Piece> clone = new ArrayList<Piece>(pieces);
		double totalArea = 0;
		for (Panel p : r.getSheet(0).getPanels()) {
			Piece piece = new Piece(p.x2 - p.x1, p.y2 - p.y1);

			assertTrue("Output panel was not given as input " + p, p.isFree()
					|| clone.contains(piece));
			assertTrue(p.isFree() || clone.remove(piece));
			totalArea += piece.width * piece.height;
		}

		assertTrue("Some pieces are not in output: " + clone.size(),
				clone.isEmpty());

		// assert that all area is covered by pieces or remainders
		assertEquals(r.getSheet(0).getWidth() * r.getSheet(0).getHeight(),
				totalArea, 0.0f);
	}

}
