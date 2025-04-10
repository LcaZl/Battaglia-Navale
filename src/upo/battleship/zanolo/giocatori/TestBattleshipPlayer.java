package upo.battleship.zanolo.giocatori;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.Vector;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import upo.battleship.zanolo.oggettiAusiliari.PlayerShipMatrix;
import upo.battleship.zanolo.oggettiAusiliari.Ship;

class TestBattleshipPlayer {

	private static BattleshipPlayer bP;
	private static Vector<Ship> v;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		v = new Vector<Ship>();
		for(int x = 0; x < 4; x++) v.add(new Ship(3, "Sottomarino"));
		bP = new BattleshipPlayerCPU(10,"Facile", v, new PlayerShipMatrix(10));
		bP.grid.init();
		bP.grid.setPoint(0, 0, 2);
		bP.grid.setPoint(1, 0, 2);
		bP.grid.setPoint(2, 0, 2);
	}

	@Test
	void testCalcPoints() {
		assertEquals(bP.calcPoints(v), 12);
	}

	@Test
	void testGetPoints() {
		assertEquals(bP.getPoints(), 12);
	}

	@Test
	void testCheckDestroyedShip() {
		Vector<Point> v = bP.checkDestroyedShip(new Point(0,0));
		assertNotNull(v);
		assertEquals(v.get(0), new Point(0,0));
		assertEquals(v.get(1), new Point(2,0));
	}

	@Test
	void testFindDirection() {
		assertEquals(bP.findDirection(new Point(0,0)), 0);
	}

	@Test
	void testSetGridDestroyed() {
		bP.setGridDestroyed(new Point(0,1), new Point(2,1));
		assertEquals(bP.grid.getValueOf(0, 1), 4);
		assertEquals(bP.grid.getValueOf(1, 1), 4);
		assertEquals(bP.grid.getValueOf(2, 1), 4);
	}

}
