package upo.battleship.zanolo.oggettiAusiliari;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TestPlayerShipMatrix {

	private static PlayerShipMatrix grid;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		grid = new PlayerShipMatrix(10);
	}

	@Test
	void testPlayerShipMatrix() {
		assertEquals(grid.getSize(), 10);
	
	}

	@Test
	void testInit() {
		for(int x = 0; x < grid.getSize(); x++)
			for(int y = 0; y < grid.getSize(); y++)
				assertEquals(grid.getValueOf(x, y), 0);
	}

	@Test
	void testLoadGrid() {
		grid.loadGrid(1, 5, 1, 3);
		for(int x = 1; x <= 5; x++) 
			assertEquals(grid.getValueOf(x, 5), 1);
	}

	@Test
	void testGetSize() {
		assertEquals(grid.getSize(), 10);
	}

	@Test
	void testGetValueOf() {
		assertEquals(grid.getValueOf(0, 0), 1);
	}

	@Test
	void testSetPoint() {
		grid.setPoint(5, 5, 3);
		assertEquals(grid.getValueOf(5, 5), 3);
	}

	@Test
	void testCheckPosPointIntInt() {
		assertTrue(grid.checkPosPoint(5,5));
		grid.setPoint(5, 6, 1);
		assertFalse(grid.checkPosPoint(new Point(5,5)));
		
	}

	@Test
	void testCheckPosDirection() {
		assertTrue(grid.checkPosDirection(5, 5, 0, 2));
		assertTrue(grid.checkPosDirection(5,5,1,2));
		assertFalse(grid.checkPosDirection(5, 5, 2, 2));
	}
}
