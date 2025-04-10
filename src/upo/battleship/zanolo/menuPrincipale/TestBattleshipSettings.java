/**
 * 
 */
package upo.battleship.zanolo.menuPrincipale;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author luca
 *
 */
class TestBattleshipSettings {
	private static BattleshipSettingsModel model;
	private static BattleshipSettingsView view;
	private static BattleshipSettingsController controller;
	
	
	@BeforeAll
	static void setUpBeforeClass(){
		model = new BattleshipSettingsModel();
		view  = new BattleshipSettingsView(model);
		controller = new BattleshipSettingsController(model,view);
		assertNotNull(model);
		assertNotNull(view);
		assertNotNull(controller);
	}
	
	/**
	 * Test method for {@link upo.battleship.zanolo.menuPrincipale.BattleshipSettingsModel#BattleshipSettingsModel()}.
	 */
	@Test
	void testBattleshipSettingsModel() {
		assertEquals(model.shipNVet.size(), 4);
	}

	/**
	 * Test method for {@link upo.battleship.zanolo.menuPrincipale.BattleshipSettingsModel#startBattle(java.lang.String, int, java.lang.String, java.util.Vector, boolean)}.
	 */
	@Test
	void testStartBattle() {
		model.startBattle("Luca", 10, "Facile", model.shipNVet, false);
		assertFalse(view.isVisible());
	}

}
