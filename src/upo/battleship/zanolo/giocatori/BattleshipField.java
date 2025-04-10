package upo.battleship.zanolo.giocatori;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classe astratta che implementa solamente un metodo in comune sia al campo di CPU che di HUMAN.
 * Tutti gli altri metodi sono implementati in un preciso modo da CPU e Human.
 * 
 * @author luca
 *
 */
public abstract class BattleshipField extends JPanel{

	private static final long serialVersionUID = 5453196864156351730L;
	protected BattleshipPlayer model;
	protected JLabel[][] grid;
	protected int fieldDim;
	
	/**
	 * Questa funzione crea il campo di battaglia di un giocatore.
	 * Human e CPU avranno campi con viste diverse.
	 * Il campo di human (a sinistra) visualizzerà tutte le sue navi e ad ogni momento i colpi di cpu
	 * e le eventuali navi affondate.
	 * Il campo di CPU invece sarà completamente ricoperto bianco, il reale campo viene mostrato solo dopo un colpo.
	 */
	protected abstract void createField();
	
	/**
	 * Funzione utilizzata in caso di caricamento di una partita.
	 * Fa si che i campi dei giocatori siano allineati ai dati presenti nei modelli caricati dei giocatori.
	 */
	protected abstract void refresh();
	
	/**
	 * Questa funzione ricarica ogni punto colpito su entrambi i campi con una 'X' rossa al centro.
	 * Per il campo di cpu, rimuove anche il listener associato alla casella colpita, di modo che non
	 * sia più colpibile.
	 * 
	 */
	protected abstract void reloadPoint();
	
	/**
	 * Funzione invocata quando ogni casella di una singola nave viene colpita, affondando completamente la nave.
	 * Tale funzione colora le caselle della nave affondata interamente di rosso.
	 */
	protected void killShip() {
		Point start = model.lastDestroyedShip.get(0);
		Point end = model.lastDestroyedShip.get(1);
		if(start.x == end.x) {
			while(start.y <= end.y) {
				grid[start.x][start.y].setBackground(Color.red);
				start.y = start.y + 1;
			}
		}
		else if(start.y == end.y) {
			while(start.x <= end.x) {
				grid[start.x][start.y].setBackground(Color.red);
				start.x = start.x + 1;
			}
		}
		
	}
}
