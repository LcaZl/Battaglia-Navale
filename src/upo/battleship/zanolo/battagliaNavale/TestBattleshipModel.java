/**
 * 
 */
package upo.battleship.zanolo.battagliaNavale;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Vector;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import upo.battleship.zanolo.giocatori.BattleshipPlayerCPU;
import upo.battleship.zanolo.giocatori.BattleshipPlayerHuman;
import upo.battleship.zanolo.oggettiAusiliari.PlayerShipMatrix;
import upo.battleship.zanolo.oggettiAusiliari.Ship;

/**
 * @author luca
 *
 */
class TestBattleshipModel {

	private static BattleshipModel model;
	private static BattleshipController controller;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Vector<Ship> v = new Vector<Ship>();
		for(int x = 0; x < 4; x++) v.add(new Ship(3, "Sottomarino"));
		BattleshipPlayerHuman human = new BattleshipPlayerHuman(10,"TESTING",v,new PlayerShipMatrix(10));;
		BattleshipPlayerCPU cpu  = new BattleshipPlayerCPU(10, "Facile", v, new PlayerShipMatrix(10));
		model = new BattleshipModel(10,true,human,cpu,10);
		controller = new BattleshipController(model);
		new BattleshipView(controller, model);
	}

	/**
	 * Test method for {@link upo.battleship.zanolo.battagliaNavale.BattleshipModel#BattleshipModel(int, boolean, upo.battleship.zanolo.giocatori.BattleshipPlayerHuman, upo.battleship.zanolo.giocatori.BattleshipPlayerCPU, int)}.
	 */
	@Test
	void testBattleshipModel() {
		assertNotNull(model);
	}

	/**
	 * Test method for {@link upo.battleship.zanolo.battagliaNavale.BattleshipModel#getCpuModel()}.
	 */
	@Test
	void testGetCpuModel() {
		assertNotNull(model.getCpuModel());
	}

	/**
	 * Test method for {@link upo.battleship.zanolo.battagliaNavale.BattleshipModel#getHumanModel()}.
	 */
	@Test
	void testGetHumanModel() {
		assertNotNull(model.getHumanModel());
	}

	/**
	 * Test method for {@link upo.battleship.zanolo.battagliaNavale.BattleshipModel#createTimer()}.
	 */
	@Test
	void testCreateTimer() {
		model.createTimer();
		assertNotNull(model.tModel);
	}

	/**
	 * Test method for {@link upo.battleship.zanolo.battagliaNavale.BattleshipModel#startBattle()}.
	 */
	@Test
	void testStartBattle() {
		model.startBattle();
		assertTrue(model.isStarted());
	}

	/**
	 * Test method for {@link upo.battleship.zanolo.battagliaNavale.BattleshipModel#getTurni()}.
	 */
	@Test
	void testGetTurni() {
		assertEquals(model.getTurni(), 0);
	}

	/**
	 * Test method for {@link upo.battleship.zanolo.battagliaNavale.BattleshipModel#getWinner()}.
	 */
	@Test
	void testGetWinner() {
		assertNull(model.getWinner());
	}

	/**
	 * Test method for {@link upo.battleship.zanolo.battagliaNavale.BattleshipModel#saveGame()}.
	 */
	@Test
	void testSaveGame() {
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.startsWith("S1");
			}
		};
		File pf = new File("./Salvataggi/");
		String a[]  = pf.list(filter);
		int n = a.length;
		model.startBattle();
		model.saveBackMenu();
		File pf1 = new File("./Salvataggi/");
		String b[]  = pf1.list(filter);
		int n1 = b.length;
		assertTrue(n < n1);
	}
}
