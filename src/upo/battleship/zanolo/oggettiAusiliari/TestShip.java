/**
 * 
 */
package upo.battleship.zanolo.oggettiAusiliari;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author luca
 *
 */
class TestShip {
	
	private static Ship ship;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ship = new Ship(3,"Sottomarino");
	}

	/**
	 * Test method for {@link upo.battleship.zanolo.oggettiAusiliari.Ship#Ship(int, java.lang.String)}.
	 */
	@Test
	void testShip() {
		assertEquals(ship.getId(),1);
	}

	/**
	 * Test method for {@link upo.battleship.zanolo.oggettiAusiliari.Ship#getSize()}.
	 */
	@Test
	void testGetSize() {
		assertEquals(ship.getSize(), 3);
	}

	/**
	 * Test method for {@link upo.battleship.zanolo.oggettiAusiliari.Ship#getId()}.
	 */
	@Test
	void testGetId() {
		assertEquals(ship.getId(), 1);
	}

	/**
	 * Test method for {@link upo.battleship.zanolo.oggettiAusiliari.Ship#getShipName()}.
	 */
	@Test
	void testGetShipName() {
		assertEquals(ship.getShipName(), "Sottomarino");
	}

}
