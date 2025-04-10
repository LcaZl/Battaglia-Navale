package upo.battleship.zanolo.menuPrincipale;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TestMainMenu {

	private static MainMenuModel model;
	private static MainMenuView view;
	private static MainMenuController controller;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		model = new MainMenuModel();
		view = new MainMenuView(model);
		controller = new MainMenuController(model,view);
		assertNotNull(model);
		assertNotNull(view);
		assertNotNull(controller);
	}

	@Test
	void testStartNewGame() {
		model.startNewGame();
		assert(view.isVisible() == false);
	}

	@Test
	void testLoadGame() {
		model.loadGame();
		assert(view.isVisible() == false);
	}

}
