package upo.battleship.zanolo;

import upo.battleship.zanolo.menuPrincipale.MainMenuController;
import upo.battleship.zanolo.menuPrincipale.MainMenuModel;
import upo.battleship.zanolo.menuPrincipale.MainMenuView;

/**
 * Classe con main che crea le istanze del menu principale del gioco.
 * Classe che fa iniziare il gioco.
 * 
 * @author luca
 *
 */
public class StartGame {
	
	public static void main(String[] args) {

		try {
			MainMenuModel model = new MainMenuModel();
			MainMenuView view = new MainMenuView(model);
			new MainMenuController(model,view);
		}
		catch(Exception e) {
			System.err.print("Errore all'interno del gioco.\n");
		}
	}
}
