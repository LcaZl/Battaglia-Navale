package upo.battleship.zanolo.menuPrincipale;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Observable;

import javax.swing.ImageIcon;

import upo.battleship.zanolo.battagliaNavale.BattleshipController;
import upo.battleship.zanolo.battagliaNavale.BattleshipModel;
import upo.battleship.zanolo.battagliaNavale.BattleshipView;

/**
 * 
 * Classe che si occupa del caricamento di una partita precedentemente salvata.
 * Consente anche di cancellare un salvataggio.
 * Nota: una volta caricato un salvataggio esso verrà eliminato.
 * 
 * @author luca
 *
 */
public class BattleshipLoadGameModel extends Observable{

	public final Image image = new ImageIcon("picture/loadGameFrame.jpg").getImage();
	protected String selectedSave;
	protected boolean selFlag;
	
	public BattleshipLoadGameModel(){
		selFlag = false;
	}
	
	/**
	 * Questa funzione viene eseguita quando premuto il bottone 'Menu principale'.
	 * Essa riporta l'utente al menu principale.
	 * La notifica '404' inviata serve per notificare la vista di effettuare il dispose().
	 * 
	 */
	protected void back() {
		setChanged();
		notifyObservers(52);
		MainMenuModel model = new MainMenuModel();
		MainMenuView view = new MainMenuView(model);
		new MainMenuController(model,view);	
	}
	
	/**
	 * Questa funzione si occupa di impostare la variabili del modello al nome del salvataggio scelto,
	 * poi asserisce un flag ad indicare che tale nome è impostato.
	 * 
	 * @param saveName Stringa con il nome del file da caricare/eliminare.
	 */
	protected void setSave(String saveName) {
		this.selectedSave = saveName;
		selFlag = true;
	}
	
	/**
	 * Funzione che carica una partita precedente.
	 * 
	 * @exception Questa funzione può generare eccezioni se il file scelto o la directory 'Salvataggi' non esistono.
	 * 
	 */
	protected void loadGame() {
		if(selFlag) 
		{
			try {
				FileInputStream Fis1 = new FileInputStream("Salvataggi/" + selectedSave);
				ObjectInputStream Ois1 = new ObjectInputStream(Fis1);
				BattleshipModel model = (BattleshipModel) Ois1.readObject();
				Ois1.close();
				this.addObserver(model);
				setChanged();
				notifyObservers(52);
				BattleshipController cont = new BattleshipController(model);
				new BattleshipView(cont, model);
				deleteGame();
			}
			catch(IOException e) {
				System.err.print("Errore caricamento salvataggio.\n");
				e.printStackTrace();
			}
			catch(ClassNotFoundException e1) {
				System.err.print("Errore caricamento salvataggio.\n");
				e1.printStackTrace();
			}
		}
		//se il flag non è impostato non è stato selezionato nessun salvataggio, quindi non fa nulla.
	}
	
	/**
	 * Funzione che cancella un salvataggio. Viene invocata anche dopo il caricamento di una partita al fine
	 * di eliminare il salvataggio caricato.
	 * 
	 * @throws IllegalArgumentException Se il file selezionato non esiste.
	 */
	protected void deleteGame() throws IllegalArgumentException{
		if(selFlag) {
			File pf = new File("Salvataggi/" + selectedSave);
			if(!pf.exists()) throw new IllegalArgumentException("Errore: file da eliminare inesistente.");
			pf.delete();
			setChanged();
			notifyObservers(51);
			selFlag = false;
		}
		//se il flag non è impostato non è stato selezionato nessun salvataggio, quindi non fa nulla.
	}
}
