package upo.battleship.zanolo.menuPrincipale;

import java.awt.Image;
import java.util.Observable;

import javax.swing.ImageIcon;

/**
 * Modello del menu principale, esso ha 2 funzioni principali, il caricamento di una partita 
 * e l'inizio di una nuova. L'abbandono del menu è gestito dal controller.
 * 
 * @author luca
 *
 */
public class MainMenuModel extends Observable{
	
	public final Image image = new ImageIcon("picture/MenuBackground.jpg").getImage();
	
	/**
	 * Questa funzione crea una nuova partita portando il giocatore alle impostazioni.
	 * 
	 * @param
	 * @return
	 */
	protected void startNewGame() {
		setChanged();
		notifyObservers();	
		BattleshipSettingsModel model = new BattleshipSettingsModel();
		BattleshipSettingsView view  = new BattleshipSettingsView(model);
		new BattleshipSettingsController(model,view);
	}
	
	/**
	 * Questa funzione porta il giocatore al menu per la scelta del salvataggio da caricare.
	 * 
	 * @param
	 * @return
	 */
	protected void loadGame() {
		setChanged();
		notifyObservers();
		BattleshipLoadGameModel lmodel = new BattleshipLoadGameModel();
		BattleshipLoadGameController lcontroller = new BattleshipLoadGameController(lmodel);
		new BattleshipLoadGameView(lcontroller, lmodel);
	}
}
