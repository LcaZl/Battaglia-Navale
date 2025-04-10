package upo.battleship.zanolo.menuPrincipale;

import java.awt.Image;
import java.util.Observable;
import java.util.Vector;

import javax.swing.ImageIcon;

import upo.battleship.zanolo.oggettiAusiliari.Ship;
import upo.battleship.zanolo.positioning.HumanShipsPositioningController;
import upo.battleship.zanolo.positioning.HumanShipsPositioningModel;
import upo.battleship.zanolo.positioning.HumanShipsPositioningView;
/**
 * Questa classe gestisce le impostazioni di una nuova partita.
 * Permette all utente di scegliere: nome, difficoltà CPU, timer, dimensioni campo di battaglia,
 * numero cacciatorpedinieri, numero sottomarini, numero corazzate, numero portaerei.
 * 
 * @author luca
 */
/**
 * @author luca
 *
 */
public class BattleshipSettingsModel extends Observable{
	
	public final Image image = new ImageIcon("picture/settingsPicture.jpg").getImage();
	public final Image image2 = new ImageIcon("picture/settingsPanelBackground.jpg").getImage();
	protected Vector<Integer> shipNVet;
	//configurazioni numero massimo navi in relazione al campo.
	public final Integer[] shipMaxN10x10 = {3,2,2,1}; 
	public final Integer[] shipMaxN15x15 = {5,3,3,2}; 
	public final Integer[] shipMaxN20x20 = {7,5,4,3}; 
	public final Integer[] shipDefaultNum = {4,3,2,1};
	
	/**
	 * Metodo costruttore che si occupa di inizializzare il vettore con i numeri di navi di default.
	 *
	 */
	public BattleshipSettingsModel() {
		shipNVet = new Vector<Integer>();
		for(int x = 0; x < 4; x++) shipNVet.add(this.shipDefaultNum[x]);
	}

	/**
	 * Questa funzione fa passare al posizionamento delle navi dell'utente.
	 * Se un utente ha specificato dei numeri di navi personalizzati, verranno utilizzati.
	 * Se invece non sono specificati verranno utilizzati i valori di default definiti nel costruttore.
	 * 
	 * @param playerName Nome giocatore.
	 * @param boardSize Dimensione campo di battaglia.
	 * @param cpuMode Difficoltà CPU.
	 * @param ships Numero di navi.
	 * @param timeFlag Valore boolean ad indicare la volontà di giocare la versione a tempo.
	 */
	protected void startBattle(String playerName,int boardSize, String cpuMode,Vector<Integer> ships, boolean timeFlag) {
		if(checkInputData(playerName)) {
			setChanged();
			this.notifyObservers(0);
			if(ships != null) shipNVet = ships;
			HumanShipsPositioningModel model = new HumanShipsPositioningModel(boardSize, createArmy(),playerName,cpuMode,timeFlag);
			HumanShipsPositioningController cont = new HumanShipsPositioningController(model);
			new HumanShipsPositioningView(model,cont);	
		}
	}
	
	/**
	 * Questa funzione controlla se il nome del giocatore è stato inserito.
	 * Esso è l'unico campo obbligatorio in ogni caso.
	 * Controlla che l'utente come nome non abbia inserito 'TESTING', poichè tale nome
	 * è riservato ai salvataggi di test.
	 * 
	 * @param playerName Nome giocatore.
	 * @return boolean Indica se il nome è presente o se non è stato specificato.
	 */
	private boolean checkInputData(String playerName) {
		if(!playerName.isEmpty() && !playerName.equals("TESTING")) {
			return true;
		}
		else{
			setChanged();
			this.notifyObservers(1);
			return false;
		}
	}
	

	/**
	 * Questa funzione crea l'esercito del giocatore.
	 * Un vettore contenente tante navi quante specificate in ShipNVet.
	 * 
	 * @return Vector<Ship> Vettore contenente il numero e il tipo di navi scelto dal giocatore.
	 */
	private Vector<Ship> createArmy() {
		Vector<Ship> army = new Vector<Ship>();
		for(int i = 0; i < shipNVet.size(); i++) {
			for(int k = 0; k < shipNVet.get(i); k++) {
				switch(i) {
					case 0:
						army.add(new Ship(2,"Cacciatorpediniere"));
					break;
					case 1:
						army.add(new Ship(3, "Sottomarino"));
					break;
					case 2:
						army.add(new Ship(4, "Corazzata"));
					break;
					case 3:
						army.add(new Ship(5, "Portaerei"));
					break;	
				}
			}
		}
		return army;
	}
}
