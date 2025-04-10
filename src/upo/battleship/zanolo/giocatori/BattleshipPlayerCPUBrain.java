package upo.battleship.zanolo.giocatori;

import java.awt.Point;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Vector;

import upo.battleship.zanolo.oggettiAusiliari.PlayerShipMatrix;
import upo.battleship.zanolo.oggettiAusiliari.Ship;
/**
 * Questa classe idelamente rappresenta il cervello del giocatore CPU.
 * Questa classe tiene traccia di tutte le informazioni utili a determinare la
 * prossima casella del campo di Human da colpire.
 * Fornisce due modalità per determinare il prossimo colpo:
 * - Facile, rappresentata unicamente dalla funzione CpuHitMode0().
 * - Difficile.
 * 
 * @author luca
 *
 */
public class BattleshipPlayerCPUBrain implements Serializable{
	
	private static final long serialVersionUID = -4008718842611551442L;
	private PlayerShipMatrix enemyGrid;//0: pos non colpita, 1:posizione colpita = mare, 2:pos colpita  = nave
	private int level;
	private int fieldDim;
	private int checkHitDirection;//direzione in cui si sta sparando;
	private Point lastHit;//ultimo colpo
	private boolean lastHitResult;//esito ultimo colpo
	private Point lastHitShip;//ultimo colpo a segno
	private Vector<Integer> directionalVet;//vettore con le 4 direzioni
	//per uno stesso indice, hitsOut contiene il punto colpito e hitsOutResult il corrispondende esito (nave colpita o no).
	private Vector<Point> hitsOut;//cronologia colpi 
	private Vector<Boolean> hitsOutResult; //risultato colpi 
	private int hitsCount; //colpi sparati
	private boolean shipHitting; //se si è in serie di colpi ad una nave
	protected boolean shipKill; //se l'ultimo colpo ha affondato una nave
	private int hittingCount; //numero di colpi andati a segno sull'ultima nave individuata
	private Point firstPoint;
	private boolean invertFlag;//indica l'inversione di colpi

	public BattleshipPlayerCPUBrain(int dim, int lv, Vector<Ship> v) {
		enemyGrid = new PlayerShipMatrix(dim);//matrice per memorizzare
		level = lv;
		fieldDim = dim;
		directionalVet = new Vector<Integer>();
		loadDirectionalVet();
		hitsOut = new Vector<Point>();
		hitsOutResult = new Vector<Boolean>();
		hitsCount = 0;
		shipHitting = false;
		lastHit = null;
		lastHitShip = null;
		shipKill = false;
		checkHitDirection = -1;
		invertFlag = false;
	}
	
	/**
	 * Funzione per il caricamento di un vettore con i valori delle 4 direzioni possibili.
	 * (0: in sù, 1: a destra, 2: in giù, 3: a sinistra).
	 */
	private void loadDirectionalVet() {
		directionalVet.removeAllElements();
		for(int x = 0; x < 4; x++) directionalVet.add(x);
	}
	
	/**
	 * Questa funzione si occupa di aggiornare i valori tenendo aggiornata la griglia rappresentante
	 * i colpi sparati.
	 * 
	 * @param f Boolean rappresentante l'esito negativo o positivo dell'ultimo colpo sparato.
	 * @param p Punto colpito dall'ultimo colpo sparato
	 * @param destroyShip Boolean che informa se l'ultimo colpo sparato ha affondato un intera nave.
	 */
	protected void setLastHitResult(boolean f, Point p, boolean destroyShip) {
		hitsOut.add(p);
		hitsOutResult.add(f);
		lastHit = p;
		lastHitResult = f;
		shipKill = destroyShip;
		if(lastHitResult) {
			lastHitShip = lastHit;//uso lastHitShip poiche indica un colpo ad una nave.
			enemyGrid.setPoint(lastHitShip.x, lastHitShip.y, 2);
		}
		else
			enemyGrid.setPoint(lastHit.x, lastHit.y, 1);
//		printState();
	}
	
	/**
	 * Funzione richiamata ogni qual volta la CPU debba fornire un punto da colpire.
	 * 
	 * @return Ritorna il punto da colpire determinato in modalità diverse in base alla difficoltà
	 * scelta per la CPU.
	 * @throws Exception Se non si è in grado di fornire un punto c'è stato un errore.
	 */
	protected Point getPoint(){
		switch(level) {
		case 0: 
			return cpuHitsMode0();
		case 1: 
			hitsCount++;
			try {
				return cpuHitsMode1();
			}
			catch(Exception e) {
				System.err.print("Errore calcolo prossimo punto CPU.");
			}
		}
		return cpuHitsMode0();
	}
	
	/**
	 * Funzione che restituisce un punto, scelto casualmente tra quelli ancora non colpiti.
	 * Questa funzione viene utilizzata per il calcolo del punto da colpire se la
	 * CPU è impostata in modalità facile.
	 * 
	 * @return
	 */
	private Point cpuHitsMode0() {//colpi totalmente casuali
		Random genPos = new SecureRandom();
		int x,y;
		do {
			x = (genPos.nextInt(fieldDim))%fieldDim;
			y = (genPos.nextInt(fieldDim))%fieldDim;
		}while(enemyGrid.getValueOf(x, y) >= 1);
		lastHit = new Point(x,y);
		return new Point(x,y);
	}
		
	/**
	 * Questa funzione, sulla base dei dati disponibili del all'inizio del turno della CPU sceglie
	 * il modo in cui determinare il prossimo colpo.
	 * Casi:
	 * 1 - Si distingue in 2 sottocasi:
	 * 		- Primo: al colpo precedente non è stata colpita nessuna nave.
	 * 		- Secondo: il colpo precedente ha colpito e affondato una nave.
	 * 2 - Il colpo precedente ha colpito una nave per la prima volta, si dovrà trovare la direzione in cui è posizionata la nave.
	 * 3 - Si stava cercando la direzione in cui è posizionata una nave ma il colpo ha colpito il mare.
	 * 4 - Il colpo ha colpito una nave mentre si cercava la direzione, quindi so l'orientamento della nave.
	 * 	   La direzione trovata rimane impostata poichè dopo aver trovato l'orientamento giusto non viene più alterato fino a quando la nave trovata non sarà affondata.
	 * 5 - Se sto colpendo una nave della quale so l'orientamento ed il colpo precedente è andato a vuoto, e inoltre Human non ha comunicato nessuna
	 * 	   nave affondata allora il prossimo colpo dovrà colpire dalla prima casella colpita nella direzione opposta rispetto a quella attuale.
	 * (Il caso in cui seguendo questa logica il prossimo colpo andrebbe oltre i limiti del campo di battaglia è gestito in un altra funzione)
	 * 6 - Errore nei dati.
	 * @return Ritorna un punto generato dalla funzione scelta.
	 * @throws Exception Se non può essere fornito un punto con una di queste modalità allora c'è un errore.
	 */
	private Point cpuHitsMode1() throws Exception {
		if(lastHit == null) return cpuHitsMode0();//primo turno di cpu
		/*1*/else if(lastHitResult == false && shipHitting == false) return cpuHitsMode0();//prima non ho colpito nulla
				else if(shipKill == true)  {//prima ho affondato una nave
				hittingCount += 1;
				deletePoints();
				setDestroy();
				return cpuHitsMode0();
			}
		/*2*/else if(lastHitResult == true && shipHitting == false){//ho colpito una nave per la prima volta al colpo precedente.
				//prima ho colpito una nave per la prima volta
				firstPoint = lastHit;
				hittingCount += 1;
				shipHitting = true;
				return directionalPoint();	
			}
		/*3*/else if(lastHitResult == false && shipHitting == true && hittingCount == 1) {//ho sbagliato direzione, riesamino il punto ancora precedente.
				//stavo cercando la direzione da colpire ma ho sbagliato, ritento
				return directionalPoint();
			}
		/*4*/else if(lastHitResult == true && shipHitting == true) {
				//ho colpito nella direzione giusta, continuerò
				hittingCount += 1;
				return advancedPoint();
			}
		/*5*/else if(lastHitResult == false && shipHitting == true && shipKill == false) {
				//se un colpo in serie va a vuoto ma non ho affondato la nave, dovro colpire nella direzione opposta.
				invertFlag = true;
				checkHitDirection = (checkHitDirection + 2) % 4;
				return advancedPoint();
			}
		/*6*/else throw new Exception("\nCPU - Errore nel determinare il prossimo punto da colpire.\n");
	}

	/**
	 * Questa funzione viene invocata sucessivamente al primo colpo ad una nave, e fino a quando non si avrà colpito una seconda casella di tale nave.
	 * Serve per trovare la direzione, viene scelta in base a quelle non precedentemente scelte e verificato che sia una direzione disponibile.
	 * Questa funzione scegliendo la direzione in cui colpire determina anche un orientamento verticale oppure orizzonatale della nave considerata.
	 * 
	 * @return Ritorna un punto scelto in base alle direzioni non ancora colpite e a quelle logicamente colpibili.
	 */
	private Point directionalPoint() {
			int dir = -1;
			loadDirectionalVet();
			Random genPos = new SecureRandom();
			boolean dirCheck = false;
			Point p = new Point();
			do {
				do
					dir = genPos.nextInt(4);
				while(!directionalVet.contains(dir));
				directionalVet.removeElement(dir);
				this.checkHitDirection = dir;
				directionalVet.removeElement(dir);
				dirCheck = false;
				//0.su 1:dx 2:giu 3:sx
				switch(dir) {
				case 0:
					if(((lastHitShip.y-1) >= 0)&&(enemyGrid.getValueOf(lastHitShip.x, lastHitShip.y-1) == 0)) {
						dirCheck = true;
						p.x =  lastHitShip.x;
						p.y = lastHitShip.y -1;
					}
				break;
				case 1:
					if(((lastHitShip.x + 1 ) <= (enemyGrid.getSize()-1))&&(enemyGrid.getValueOf(lastHitShip.x+1, lastHitShip.y) == 0)) {
						dirCheck = true;
						p.x =  lastHitShip.x + 1;
						p.y = lastHitShip.y;
					}
				break;
				case 2:
					if(((lastHitShip.y + 1 ) <= (enemyGrid.getSize()-1)) && (enemyGrid.getValueOf(lastHitShip.x, lastHitShip.y+1) == 0)) {
						dirCheck = true;
						p.x =  lastHitShip.x;
						p.y = lastHitShip.y + 1;
					}
				break;
				case 3:
					if(((lastHitShip.x - 1 ) >= 0) && (enemyGrid.getValueOf(lastHitShip.x - 1, lastHitShip.y) == 0)) {
						dirCheck = true;
						p.x =  lastHitShip.x - 1;
						p.y = lastHitShip.y;
					}
				break;
				}	
			}while(!dirCheck);
			return p;
	}
	
	/**
	 * Questa funzione si occupa di continuare la scia di colpi dopo aver individuato la direzione in cui è orientata la nave.
	 * Nella scelta di tale punto viene verificato che esso non superi le dimensioni del campo di battaglia, ovvero non genererà un punto con
	 * coordinate superiori alle massime predefinite o inferiori alle minime.
	 * Il secondo fattore determinante nella scelta è il seguente: se la cpu si accorge che il potenziale punto sucessivo da colpire è in realtà
	 * già stato copito, invertirà la direzione del colpo ((direzione + 2 ) % 4), generando un punto nella direzione opposta.
	 * 
	 * @return Ritorna un punto scelto in base alla direzione di colpi precedentemente determinata.
	 */
	private Point advancedPoint() {
		Point p = new Point();
		Point base = lastHitShip;
		if(invertFlag) {
			base = firstPoint;
			invertFlag = false;
		}
		switch(checkHitDirection) {
			case 0:
				p.x = base.x;
				p.y = base.y-1;
				if((p.y < 0) || (enemyGrid.getValueOf(p.x, p.y) != 0)) {
					checkHitDirection = (checkHitDirection + 2 ) % 4;
					invertFlag = true;
					return advancedPoint();
				}
			break;
			case 1:
				p.x = base.x+1;
				p.y = base.y;
				if((p.x > (enemyGrid.getSize()-1)) || (enemyGrid.getValueOf(p.x, p.y) != 0)) {
					checkHitDirection = (checkHitDirection + 2 ) % 4;
					invertFlag = true;
					return advancedPoint();
				}
			break;
			case 2:			
				p.x = base.x;
				p.y = base.y+1;
				if((p.y > (enemyGrid.getSize()-1)) || (enemyGrid.getValueOf(p.x, p.y) != 0)) {
					checkHitDirection = (checkHitDirection + 2 ) % 4;
					invertFlag = true;
					return advancedPoint();
				}
			break;
			case 3: 			
				p.x = base.x-1;
				p.y = base.y;
				if((p.x < 0) || (enemyGrid.getValueOf(p.x, p.y) != 0)) {
					checkHitDirection = (checkHitDirection + 2 ) % 4;
					invertFlag = true;
					return advancedPoint();
				}
			break;
		}
		return p;
	}
	
	/**
	 * Questa funzione viene invocata esclusivamente quando viene rilevato che il colpo precedente ha affondato una nave.
	 * Essa ripristina a predefiniti valori alcune impostazioni della CPU.
	 * Va a portare la CPU in uno stato di ricerca, ovvero il prossimo colpo dovrà cercare una nave, in quanto non è conosciuta,
	 * a questo punto, nessuna nave non affondata.
	 * 
	 * @return void
	 */
	protected void setDestroy() {
		this.shipHitting = false;
		this.hittingCount = 0;
		checkHitDirection = -1;
		shipKill = false;
	}
	
	/**
	 * 
	 * Questa funzione con l'ausilio di setAdj() va ad impostare ad 1 tutte le caselle adiacenti ad una nave appena affondata.
	 * In questo modo si esculdono dalla scelta per il prossimo colpo tutte quelle caselle in cui, per le regole del gioco, non ci 
	 * possono essere navi.
	 * 
	 * @return void
	 */
	private void deletePoints() {
		Point p = new Point();
		int count = 0;
		int x = hitsOut.size()-1;
		while(count < hittingCount) {
			if(hitsOutResult.get(x) == true) {
				p = hitsOut.get(x);
				setAdj(p);
				count++;
			}
			x--;
		}
	}
	
	/**
	 * Questa funzione preso un punto in input va ad impostare ad 1 tutti i punti adiacenti ad esso.
	 * 
	 * @param a Punto in input.
	 */
	private void setAdj(Point a) {
		int posX = a.x;
		int posY = a.y;
		int ix = 1;
		int iy = 1;
		if(posX - 1 >= 0) {
			posX--;
			ix = 2;
		}
		if(posY - 1 >= 0) {
			posY--;
			iy = 2;
		}
		for(int x = posX;x <= posX+ix;x++)
			for(int y = posY;y <= posY+iy; y++)
				if((x <= (enemyGrid.getSize()-1))&&(y <= (enemyGrid.getSize()-1)))
					if(enemyGrid.getValueOf(x, y) != 2)
							enemyGrid.setPoint(x, y, 1);
	}
	
	
	@SuppressWarnings("unused")
	private void printState() {
		System.out.print("\nLastHit ->" + lastHit + "\n"
						+ "LastHitShip ->" + lastHitShip + "\n"
						+ "LastHitresult ->" + lastHitResult + "\n"
						+ "vettore colpi ->" + hitsOut + "\n"
						+ "vettore risultati ->" + hitsOutResult + "\n"
						+ "hitsCount ->" + hitsCount + "\n"
						+ "hittingCount " + hittingCount + "\n"
						+ "Shipkill ->" + shipKill + "\n"
						+ "ShipHitting -> " + shipHitting + "\n"
						+ "firstPoint -> " + firstPoint + "\n");
	}
}
