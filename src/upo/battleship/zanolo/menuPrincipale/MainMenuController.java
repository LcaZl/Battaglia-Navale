package upo.battleship.zanolo.menuPrincipale;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Controller del menu principale.
 * 
 * @author luca
 */
public class MainMenuController implements ActionListener {
	
	private MainMenuModel mainMenuModel;
	private MainMenuView mainMenuView;
	
	/**
	 * Funzione invocata alla creazione del controller, si occupa di impostare gli actionListener 
	 * dei bottoni della vista e impostare la vista come osservatrice del modello.
	 * 
	 * @param model Modello del menu principale.
	 * @param view Vista del menu principale.
	 */
	public MainMenuController(MainMenuModel model, MainMenuView view) {
		mainMenuModel = model;
		mainMenuView = view;
		mainMenuView.buttonExitGame.addActionListener(this);
		mainMenuView.buttonLoadGame.addActionListener(this);
		mainMenuView.buttonNewGame.addActionListener(this);	
		mainMenuModel.addObserver(view);
	}
	
	/**
	 * Funzione che gestisce il premere dei bottoni.
	 *
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String act = e.getActionCommand();
		switch(act) {
		case "Nuova Partita":
			mainMenuModel.startNewGame();
		break;
		case "Carica Partita":
			mainMenuModel.loadGame();
		break;
		case "Abbandona":
			System.exit(0);
		break;
		}
	
	}
}
