package upo.battleship.zanolo.giocatori;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import upo.battleship.zanolo.oggettiAusiliari.PlayerShipMatrix;
import upo.battleship.zanolo.oggettiAusiliari.Ship;

import java.util.Vector;

class TestBattleshipPlayerHuman {

	private static BattleshipPlayerHuman human;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		human = new BattleshipPlayerHuman(10,"prova",new Vector<Ship>(), new PlayerShipMatrix(10));
		human.grid.init();
		human.grid.setPoint(0, 0, 0);
		human.grid.setPoint(5, 5, 1);
		human.points = 1;
	}

	@Test
	void testBattleshipPlayerHuman() {
	}

	@Test
	void testGetName() {
		assertEquals(human.getName(),"prova");
	}

	@Test
	void testGetHitIn() {
		human.hitInPoint = new Point(0,0);
		human.getHitIn();
		assertFalse(human.hitInFlag);
		assertEquals(human.grid.getValueOf(0, 0), 3);
		assertEquals(human.points, 1);
		human.hitInPoint = new Point(5,5);
		human.getHitIn();
		assertTrue(human.hitInFlag);
		assertEquals(human.grid.getValueOf(5, 5), 2);
		assertEquals(human.points, 0);
	}
}
