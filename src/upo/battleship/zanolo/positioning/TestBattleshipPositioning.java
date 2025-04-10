/**
 * 
 */
package upo.battleship.zanolo.positioning;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Vector;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import upo.battleship.zanolo.oggettiAusiliari.Ship;

/**
 * @author luca
 *
 */
class TestBattleshipPositioning {

	private static HumanShipsPositioningModel model;
	private static HumanShipsPositioningController controller;  
	private static HumanShipsPositioningView view;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Vector<Ship> v= new Vector<Ship>();
		for(int x = 0; x < 2; x++) v.add(new Ship(3,"Sottomarino"));
		for(int x = 0; x < 2; x++) v.add(new Ship(3,"Cacciatorpediniere"));
		model = new HumanShipsPositioningModel(10,v,"prova","Facile",false);
		controller = new HumanShipsPositioningController(model);
		view = new HumanShipsPositioningView(model,controller);	
		assertNotNull(model);
		assertNotNull(controller);
		assertNotNull(view);
		
	}
	/**
	 * Test method for {@link upo.battleship.zanolo.positioning.HumanShipsPositioningModel#HumanShipsPositioningModel(int, java.util.Vector, java.lang.String, java.lang.String, boolean)}.
	 */
	@Test
	void testHumanShipsPositioningModel() {
		assertEquals(model.humanMatrix.getSize(), 10);
		assertEquals(model.shipsVet.size(), 4);
		assertFalse(model.placeFlag);
		assertEquals(model.placedShips, 0);
	}

	/**
	 * Test method for {@link upo.battleship.zanolo.positioning.HumanShipsPositioningModel#receivePoint(int, int)}.
	 */
	@Test
	void testReceivePoint() {
		//positioning state = 0, nessuna nave selezionata
		model.shipSelected("Sottomarino");
		assertEquals(model.getPositioningState(), 1);
		model.receivePoint(1, 1);
		assertEquals(model.getPositioningState(), 2);
	}

	/**
	 * Test method for {@link upo.battleship.zanolo.positioning.HumanShipsPositioningModel#shipSelected(java.lang.String)}.
	 */
	@Test
	void testShipSelected() {
		model.shipSelected("Sottomarino");
		assertEquals(model.getPositioningState(), 1);
	}

	/**
	 * Test method for {@link upo.battleship.zanolo.positioning.HumanShipsPositioningModel#reset()}.
	 */
	@Test
	void testReset() {
		model.reset();
		assertEquals(model.getPositioningState(), 0);
		assertEquals(model.placedShips, 0);
	}

	/**
	 * Test method for {@link upo.battleship.zanolo.positioning.HumanShipsPositioningModel#start()}.
	 */
	@Test
	void testStart() {
		model.start();
		assertFalse(view.isVisible());
	}

}
