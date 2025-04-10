package upo.battleship.zanolo.giocatori;

import java.util.Vector;

import upo.battleship.zanolo.oggettiAusiliari.PlayerShipMatrix;
import upo.battleship.zanolo.oggettiAusiliari.Ship;

import java.util.Observable;
import java.util.Observer;
import java.awt.Point;

/**
 * Classe che implementa BattleshipPlayer fornendo un implementazione di getHitIn().
 * Tale classe rappresenta la CPU e tiene traccia dei dati necessari sia per determinare il prossimo colpo
 * che per mantenere la view aggiornata, gestisce inoltre i colpi in entrata aggiornando la vista del
 * suo campo di  battaglia.
 * 
 * @author luca
 */
public class BattleshipPlayerCPU extends BattleshipPlayer implements Observer{

	private static final long serialVersionUID = 4409273706442800950L;
	private int cpuLevel;
	private int fieldDim;
	protected boolean lastHitDestroyShip;
	protected Point hitOutPoint;//punto che cpu colpira a human
	protected boolean hitOutFlag; //se punto colpito da cpu era nave
	private BattleshipPlayerCPUBrain cpuBrain;
	
	public BattleshipPlayerCPU(int dim, String mode, Vector<Ship> shipNVet, PlayerShipMatrix m){
		this.fieldDim = dim;
		this.shipsList = shipNVet;
		calculateLevel(mode);
		points = calcPoints(shipsList);
		cpuBrain = new BattleshipPlayerCPUBrain(fieldDim, cpuLevel, shipsList);
		grid = new PlayerShipMatrix(fieldDim);
		hitInFlag = false; 
		hitOutFlag = false;
		lastHitDestroyShip = false;
		this.grid = m;
	}
	
	/**
	 * Tale funzione traduce la stringa rappresentante il livello del cpu in un valore intero predefinito.
	 * 
	 * @param level Stringa rappresentate il livello della CPU.
	 */
	private void calculateLevel(String level) {
			switch(level) {
				case "Facile":
					this.cpuLevel = 0;
				break;
				case "Normale":
					this.cpuLevel = 1;
				break;
			}
	}
	
	/**
	 * Funzione che aggiorna l'ultimo punto colpo colpito del campo di battaglia.
	 * 
	 * @param x Coordinata x.
	 * @param y Coordinata y.
	 */
	protected void setHitInPoint(int x, int y) {
		this.hitInPoint = new Point(x,y);
	}
	
	@Override
	protected void getHitIn() {
		int x = hitInPoint.x;
		int y = hitInPoint.y;
		this.lastDestroyedShip = null;
		if(grid.getValueOf(x, y) == 1) {
			hitInFlag = true;
			points--;
			grid.setPoint(x, y, 2);//2 per non ricaricare nella view il listener(1 rappresenta le navi e 0 il mare)
			state = new String("colpisce punto [" + hitInPoint.x + ", " + hitInPoint.y + "] : Nave colpita!" );
			this.lastDestroyedShip = checkDestroyedShip(hitInPoint);
		}
		else if(grid.getValueOf(x, y) == 0) {
			hitInFlag = false;
			grid.setPoint(x, y, 3);
			state = new String("colpisce punto [" + hitInPoint.x + ", " + hitInPoint.y + "] : Colpo a vuoto!" );
		}
		setChanged();
		notifyObservers(21);//notifico la view di aggiornarsi e disattivare il campo.
		if(this.lastDestroyedShip != null) {
			setGridDestroyed(lastDestroyedShip.get(0), lastDestroyedShip.get(1));
			setChanged();
			notifyObservers(22);
		}
		hitOutPoint = cpuBrain.getPoint();
		setChanged();
		notifyObservers(23);//notifico a BattleshipModel il termine del turno.
		cpuBrain.setLastHitResult(hitOutFlag, hitOutPoint, lastHitDestroyShip);
		lastHitDestroyShip = false;
	}
	
	

	/**
	 * @return Ritorna hitOutPoint.
	 */
	public Point getHitOutPoint() {
		return hitOutPoint;
	}

	/**
	 * @param val Valore a cui impostare hitOutPoint.
	 */
	public void setHitOutPoint(Point val) {
		this.hitOutPoint = val;
	}

	/**
	 * @param lastHitDestroyShip the lastHitDestroyShip to set
	 */
	public void setLastHitDestroyShip(boolean lastHitDestroyShip) {
		this.lastHitDestroyShip = lastHitDestroyShip;
	}

	/**
	 * @return ritorna hitOutFlag.
	 */
	public boolean isHitOutFlag() {
		return hitOutFlag;
	}

	/**
	 * @param val Valore a cui impostare hitOutFlag.
	 */
	public void setHitOutFlag(boolean val) {
		this.hitOutFlag = val;
	}

	@Override
	public void update(Observable o, Object arg) {
		int n = (int)arg;
		if(n == 11) {//notifica da Battleshipmodel che inizia il gioco
			setChanged();
			notifyObservers(24);
		}
	}
}
