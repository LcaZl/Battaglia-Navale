package upo.battleship.zanolo.battagliaNavale;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import upo.battleship.zanolo.giocatori.BattleshipCPUFieldController;


/**
 * Controller di Battleship.
 * Si occupa principalmente di gestire gli eventi legati alle voci del JMenu.
 * 
 * @author luca
 *
 */
public class BattleshipController implements ActionListener{

	private BattleshipCPUFieldController controllerCpu;
	private BattleshipModel model;
	
	/**
	 * Questa funzione crea il controller del giocatore CPU.
	 * Quello di human non viene creato in quanto non è prevista nessuna interazione con l'utente 
	 * nelle campo rappresentante le sue navi.
	 * 
	 * @param m Modello Battleship.
	 */
	public BattleshipController(BattleshipModel m) {
		this.model = m;
		this.controllerCpu = new BattleshipCPUFieldController(model.getCpuModel());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = (String)e.getActionCommand();
		if(cmd.equals("[PREMI]Inizia la battaglia![PREMI]"))
			model.startBattle();
		if(cmd.equals("Esci"))
			System.exit(0);
		if(cmd.equals("Salva ed esci")) {
			model.saveGame();
			System.exit(0);
		}
		if(cmd.equals("Menu principale")) {
			model.backMenu();
		}
		if(cmd.equals("Salva e torna al menu principale")) {
			model.saveBackMenu();
		}
	}
	
	protected BattleshipCPUFieldController getCpuController() {
		return controllerCpu;
	}
}
