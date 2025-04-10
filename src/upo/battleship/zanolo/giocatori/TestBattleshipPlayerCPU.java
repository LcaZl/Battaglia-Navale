package upo.battleship.zanolo.giocatori;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.Vector;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import upo.battleship.zanolo.oggettiAusiliari.PlayerShipMatrix;
import upo.battleship.zanolo.oggettiAusiliari.Ship;

class TestBattleshipPlayerCPU {

	static private BattleshipPlayerCPU cpu;
	private static Vector<Ship> v;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		v = new Vector<Ship>();
		for(int x = 0; x < 4; x++) v.add(new Ship(3, "Sottomarino"));
	}

	@Test
	void testBattleshipPlayerCPU() {
		cpu = new BattleshipPlayerCPU(10,"Facile",v, new PlayerShipMatrix(10));
		assertEquals(cpu.grid.getSize(), 10);
		assertEquals(cpu.points, 12);
	}

	@Test
	void testGetHitIn() {
		cpu = new BattleshipPlayerCPU(10,"Facile",v, new PlayerShipMatrix(10));
		Point p = new Point(0,0);
		cpu.grid.setPoint(0, 0, 1);
		assertNotNull(p);
		cpu.hitInPoint = p;
		cpu.getHitIn();
		assertEquals(cpu.grid.getValueOf(p.x, p.y),2);
	}
}
