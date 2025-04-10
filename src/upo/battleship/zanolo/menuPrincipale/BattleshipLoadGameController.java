package upo.battleship.zanolo.menuPrincipale;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

/**
 * Controller di BattleshipLoadGame.
 * 
 * @author luca
 */
public class BattleshipLoadGameController implements ActionListener{

	private BattleshipLoadGameModel model;
	
	public BattleshipLoadGameController(BattleshipLoadGameModel m) {
		model = m;
	}

	/**
	 * Gestisce gli aventi generati dal click dei bottoni della view.
	 *
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = (String)e.getActionCommand();
		switch(cmd) {
			case "Carica Partita":
				model.loadGame();
			break;
			case "Cancella Partita":
				model.deleteGame();
			break;
			case "Menu principale":
				model.back();
			break;
			case "Esci dal Gioco":
				System.exit(0);
			break;
			default:
				JRadioButton but = (JRadioButton)e.getSource();
				model.setSave(but.getName());
			break;
		}
	}
}
