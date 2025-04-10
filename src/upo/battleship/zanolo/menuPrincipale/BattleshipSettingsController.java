package upo.battleship.zanolo.menuPrincipale;

import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;


/**
 * Controller di BattleshipSettings
 * 
 * @author luca
 *
 */
public class BattleshipSettingsController implements ActionListener, ItemListener{

	private BattleshipSettingsModel model;
	private BattleshipSettingsView view;
	
	/**
	 * Questo costruttore imposta le variabili interne e gli actionListener di alcuni oggetti grafici della vista.
	 * 
	 * @param m Modello di BattleshipSettings
	 * @param v Vista di BattlshipSettings
	 */
	public BattleshipSettingsController(BattleshipSettingsModel m, BattleshipSettingsView v) {
		this.model = m;
		this.view = v;
		v.defaultGame.addActionListener(this);
		v.startGame.addActionListener(this);
		v.timeButton.addActionListener(this);
		v.fieldSizeChoice.addItemListener(this);
		model.addObserver(view);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String a = e.getActionCommand();
		String size = (String)view.fieldSizeChoice.getSelectedItem();
		String mode = (String)view.modeChoice.getSelectedItem();
		int boardSize = Integer.parseInt(size.substring(0, 2));
		switch(a) {
			case "Inizio Veloce":
					//Impostazioni per una partita standard (Il numero di navi viene definito nel model).
					model.startBattle(view.nameField.getText(),10,"Facile",null,view.timeButton.isSelected());
			break;
			case "Battaglia!":
					model.startBattle(view.nameField.getText(),boardSize,mode,shipNReader(),view.timeButton.isSelected());
			break;
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		int n;
		if(e.getItem().toString().length() > 2) {
			n = (Integer)view.fieldSizeChoice.getSelectedIndex();
			view.reloadShipNumber(n);	
		}
	}	
	
	/**
	 * Funzione che crea e restituisce un vettore del numero di navi scelte.
	 * I valori sono presi dalla vista.
	 * 
	 * @return Vector<Integer> Vettore contente i numeri di navi scelti dall'utente tramite GUI.
	 */
	private Vector<Integer> shipNReader(){
		Vector<Integer> vet = new Vector<Integer>();
		Integer temp;
		for(int i = 0;i < view.shipNumChoiceVet.size(); i++) {
			temp = (Integer)view.shipNumChoiceVet.get(i).getSelectedItem();
			vet.add(temp);
		}
		return vet;
	}
	
}