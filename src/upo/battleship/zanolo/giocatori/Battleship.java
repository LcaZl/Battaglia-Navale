package upo.battleship.zanolo.giocatori;

import java.awt.Point;
import java.util.Vector;

import upo.battleship.zanolo.oggettiAusiliari.Ship;

/**
 * Interfaccia che descrive metodi comuni ad entrambi i giocatori.
 * 
 * @author luca
 *
 */
public interface Battleship{
		
	/**
	 * @param v Vettore di navi.
	 * @return Intero rappresentante i punti assegati sulla base del vettore in input.
	 */
	public abstract int calcPoints(Vector<Ship> v);
	
	/**
	 * Funzione per ottenere i punti di un giocatore.
	 * 
	 * @return Ritorna un intero corrispondente ai punti di colui che implementa tale metodo.
	 */
	public abstract int getPoints();
	
	/**
	 * Funzione per ottenere lo stato di un giocatore.
	 * 
	 * @return Ritorna una stringa che rappresenta lo stato del giocatore.
	 */
	public abstract String getState();
	
	/**
	 * La funzione va a determinare se con l'ultimo punto colpito un giocatore ha affondato
	 * un intera nave.
	 * 
	 * @param lastHit Punto colpito per ultimo.
	 */
	public Vector<Point> checkDestroyedShip(Point lastHit);
	
	/**
	 * La funzione viene invocata nel caso in cui l'ultimo colpo abbia affondato un intera nave.
	 * Questa funzione imposta i valori, della PlayerShipMatrix di un giocatore, 
	 * a 4, in tutte le posizioni in cui era presente la nave appena affondata.
	 * 
	 * @param start Punto di inizio.
	 * @param end Punto di fine.
	 */
	public void setGridDestroyed(Point start, Point end);
	
	/**
	 * Questa funzione è ausiliaria a checkDestroyedShip.
	 * Questa funzione determina se una nave è posizionata verticalmente oppure orizzontalmente.
	 * 
	 * @param lastHit Ultimo colpo ricevuto da un giocatore.
	 * @return Ritorna 0 se l'ultimo colpo riguarda una nave posizionata orizzontalmente, 1 altrimenti.
	 * Nel caso invece in cui non sia possibile determinare il posizionamento di una nave ritorna 2, ad indicare
	 * tale situazione.
	 */
	public int findDirection(Point lastHit);
}
