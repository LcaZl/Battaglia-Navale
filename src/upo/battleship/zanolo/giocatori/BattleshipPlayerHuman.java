package upo.battleship.zanolo.giocatori;

import java.util.Vector;

import upo.battleship.zanolo.oggettiAusiliari.PlayerShipMatrix;
import upo.battleship.zanolo.oggettiAusiliari.Ship;

import java.util.Observable;
import java.util.Observer;

/**
 * Classe che estende BattleshipPlayer fornendo l'implementazione di getHitIn().
 * Questa classe rappresenta l'utente umano che gioca a Battaglia navale.
 * Tiene traccia solo dei colpi ricevuti al fine di aggiornare la vista del campo di Human.
 * 
 * @author luca
 */
public class BattleshipPlayerHuman extends BattleshipPlayer implements Observer{

	private static final long serialVersionUID = 4765732086076264476L;
	protected String playerName;
	protected BattleshipHumanFieldView view;
	
	public BattleshipPlayerHuman(int dim, String name,Vector<Ship> ships, PlayerShipMatrix matr){
		this.shipsList = ships;
		this.playerName = name;
		this.grid = matr;
		this.points = calcPoints(shipsList);
		this.gameLoaded = false;
	}

	/**
	 * Funzione che restituisce il nome del giocatore.
	 * 
	 * @return Nome del giocatore.
	 */
	public String getName() {
		return playerName;
	}

	@Override
	protected void getHitIn() {
		this.lastDestroyedShip = null;
		if(grid.getValueOf(hitInPoint.x,hitInPoint.y) == 1) {
			grid.setPoint(hitInPoint.x, hitInPoint.y, 2);//nave colpita
			hitInFlag = true;
			points--;
			state = new String("Cpu colpisce punto [" + hitInPoint.x + ", " + hitInPoint.y + "] : Nave colpita!" );
			this.lastDestroyedShip = checkDestroyedShip(hitInPoint);
		}
		else if(grid.getValueOf(hitInPoint.x,hitInPoint.y) == 0) {
			hitInFlag = false;
			grid.setPoint(hitInPoint.x, hitInPoint.y, 3);//mare colpito
			state = new String("Cpu colpisce punto [" + hitInPoint.x + ", " + hitInPoint.y + "] : Colpo a vuoto!" );
		}
		setChanged();
		notifyObservers(31);
		if(this.lastDestroyedShip != null) {
			setGridDestroyed(lastDestroyedShip.get(0), lastDestroyedShip.get(1));
			setChanged();
			notifyObservers(32);
		}
		setChanged();
		notifyObservers(33);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		int n = (int)arg;
		if(n == 13) {//notifica da battshipmodel
			getHitIn();
		}	
	}
}
