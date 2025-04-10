package upo.battleship.zanolo.menuPrincipale;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TestBattleshipLoadGame {

	private static BattleshipLoadGameModel model;
	private static BattleshipLoadGameView view;
	private static BattleshipLoadGameController controller;
	private static File file;
	
	@BeforeAll
	static void setUpBeforeClass(){
		model = new BattleshipLoadGameModel();
		controller = new BattleshipLoadGameController(model);
		view = new BattleshipLoadGameView(controller, model);
		file = new File("./Salvataggi/");
		assertTrue(file.exists());
	}

	@Test
	void testBattleshipLoadGameModel() {
		assertFalse(model.selFlag);
		assertNotNull(model);
		assertNotNull(controller);
		assertNotNull(view);
		assertEquals(model.countObservers(), 1);
	}

	@Test
	void testSetSave() {
		model.setSave("prova");
		assertTrue(model.selFlag);
		assertEquals(model.selectedSave, "prova");
	}
	
	@Test
	void testBack() {
		model.back();
		assertFalse(view.isVisible());
	}

	@SuppressWarnings("resource")
	@Test
	void testLoadGame() {
		model = new BattleshipLoadGameModel();
		controller = new BattleshipLoadGameController(model);
		view = new BattleshipLoadGameView(controller, model);
		model.selFlag = false;
		model.loadGame();
		assertFalse(model.selFlag);
		assertTrue(view.isVisible());
		File f = new File("./Salvataggi/S0_0_TESTING");
		File p = new File("./Salvataggi/T");
		FileChannel source = null;
		FileChannel dest = null;
		try {
			p.createNewFile();
			source = new FileInputStream(f).getChannel();
			dest = new FileOutputStream(p).getChannel();
			dest.transferFrom(source, 0, source.size());
			if(source!= null)source.close();
			if(dest != null)dest.close();
			dest.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		model.setSave(f.getName());
		assertNotNull(model.selectedSave);
		assertTrue(model.selFlag);
		model.loadGame();
		assertFalse(view.isVisible());
		p.renameTo(f);
	}

	@Test
	void testDeleteGame() {
		model = new BattleshipLoadGameModel();
		controller = new BattleshipLoadGameController(model);
		view = new BattleshipLoadGameView(controller, model);
		model.selFlag = false;
		assertFalse(model.selFlag);
		File del = new File("./Salvataggi/S0_1_TESTING");
		try {
			del.createNewFile();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		assertTrue(del.exists());
		model.setSave(del.getName());
		assertNotNull(model.selectedSave);
		assertEquals(model.selectedSave, del.getName());
		model.deleteGame();
		assertFalse(model.selFlag);
		assertFalse(del.exists());
	}

}
