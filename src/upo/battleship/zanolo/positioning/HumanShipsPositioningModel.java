package upo.battleship.zanolo.positioning;

import java.util.Vector;

import upo.battleship.zanolo.battagliaNavale.BattleshipController;
import upo.battleship.zanolo.battagliaNavale.BattleshipModel;
import upo.battleship.zanolo.battagliaNavale.BattleshipView;
import upo.battleship.zanolo.giocatori.BattleshipPlayerCPU;
import upo.battleship.zanolo.giocatori.BattleshipPlayerHuman;
import upo.battleship.zanolo.oggettiAusiliari.PlayerShipMatrix;
import upo.battleship.zanolo.oggettiAusiliari.Ship;

import java.util.Observable;
import java.util.Random;
import java.awt.Point;
import java.security.SecureRandom;

/**
 * Questa classe gestisce il posizionamento delle navi da parte dell'utente, il risultato ottenuto sarà una matrice con tutte le navi
 * precedentemente scelte posizionate, condizione obbligatoria per giocare una partita.
 * 
 * @author luca
 *
 */
public class HumanShipsPositioningModel extends Observable{

	protected PlayerShipMatrix humanMatrix;//campo di battaglia
	protected PlayerShipMatrix cpuMatr;
	protected Vector<Ship> shipsVet;//Vettore con le navi da piazzare
	protected boolean placeFlag;
	private int positioningState;//Serve per sapere a che punto del posizionamento si è.
	private String selectedShip;//Contiene il nome della nave selezionata.
	private Point startPoint;
	private Point endPoint;
	protected int placedShips;//Nuemero di navi piazzate.
	private int dir;
	private int fieldDim;
	private String playerName,cpuMode;
	private boolean timeFlag;
	
	public HumanShipsPositioningModel(int fDim,Vector<Ship> ships,String pName, String cpuM, boolean tFlag) {
		this.humanMatrix = new PlayerShipMatrix(fDim);
		this.shipsVet = ships;
		this.fieldDim = fDim;
		this.playerName = pName;
		this.cpuMode = cpuM;
		this.timeFlag = tFlag;
		placeFlag = false;
		placedShips = 0;
		positioningState = 0;

	}
	
	/**
	 * Questa funzione, preso in input un punto, lo elabora in modi diversi a seconda del valore di positioning state.
	 * Valori:
	 * 0 - Non è stata selezionata alcuna nave, non succede nulla e il caso di conseguenza non viene esplicitamente gestito;
	 * 1 - Nave selezionata, viene verificato il punto in input, se non valido non succede nulla (l'utente potrà immediatamente sceglierne un altro);
	 * 2 - Nave selezionata, punto iniziale selezionato, il punto in input viene utilizzato per determinare la direzione in cui posizionarla.
	 * 	   Viene quindi determinata e verificata la validità della direzione e la griglia viene caricata con la nave scelta a partire dal primo punto scelto
	 * 	   e direzionata verso il secondo per un numero di punti pari alle sue dimensioni.
	 * 3 - (Valore elaborato da un'altra funzione);
	 * 4 - Valore che indica che tutte le navi sono state posizionate. L'utente è libero di iniziare la partita o di riposizionare tutte le navi;
	 * 5 - (Valore elaborato da un'altra funzione).
	 * 6 - (Valore elaborato da un'altra funzione).
	 * @param x Coordinata x del punto.
	 * @param y Coordinata y del punto.
	 */
	protected void receivePoint(int x, int y) {
			switch(positioningState) {
			//case 0, nessuna nave selezionata.
				case 1: 
					if(humanMatrix.checkPosPoint(x, y)){
						startPoint = new Point(x,y);
						setChanged();
						notifyObservers(startPoint);
						positioningState = 2;
					}
				break;
				case 2: 
					if(verifyDirection(x,y)) {
						if(humanMatrix.checkPosDirection(startPoint.x, startPoint.y, dir, shipsVet.get(getVectorIndex()).getSize()-1)) {
							humanMatrix.loadGrid(startPoint.x, startPoint.y, dir, shipsVet.get(getVectorIndex()).getSize());
							setChanged();
							notifyObservers();
							placedShips++;
							if(placedShips == shipsVet.size()){
								positioningState = 4;
								setChanged();
								notifyObservers();
							}
							positioningState = 0;	
						}
					}		
				break;
			}
	}	
	
	protected int getPositioningState() {
		return positioningState;
	}
	
	/**
	 * Questa funzione imposta ad 1 positioningState quando viene selezionata una nave da posizionare.
	 * 
	 * @param type Tipologia di nave selezionata
	 */
	protected void shipSelected(String type) {
			positioningState = 1;
			selectedShip = type;
	}
	
	/**
	 * Questa funzione determina se la direzione scelta con l'ultimo punto cliccato è corretta oppure no.
	 * 
	 * @param x Coordinata x del punto
	 * @param y Coordinata y del punto.
	 * @return Boolean Valore true per indicare che la direzione individuata dall'ultimo punto cliccato è corretta e la nave è posizionabile in quella direzione, false altrimenti.
	 */
	private boolean verifyDirection(int x, int y) {
		if(humanMatrix.checkPosPoint(x, y))
			endPoint = new Point(x,y);
		else
			return false;
		int distance = (int)startPoint.distance(endPoint);
		//Condizioni per posizionamento in linea retta nelle 4 direzioni
		if(startPoint.x == endPoint.x && startPoint.y > endPoint.y) {//dir = 0 verso alto.
			if((shipsVet.get(getVectorIndex()).getSize()-1) >= distance) {
				dir = 0;
				return true;
			}
		}
		if(startPoint.y == endPoint.y && startPoint.x < endPoint.x) {//dir = 1 verso destra.
			if((shipsVet.get(getVectorIndex()).getSize()-1) >= distance) {
				dir = 1;
				return true;
			}
		}
		if(startPoint.x == endPoint.x && startPoint.y < endPoint.y) {//dir = 2 verso basso.
			if((shipsVet.get(getVectorIndex()).getSize()-1) >= distance) {
				dir = 2;
				return true;
			}
		}
		if(startPoint.y == endPoint.y && startPoint.x > endPoint.x) {//dir = 3 verso sinistra.
			if((shipsVet.get(getVectorIndex()).getSize()-1) >= distance) {
				dir = 3;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Questa funzione è utilizzata per far corrispondere la nace scelta ad una nave presente nel vettore di navi.
	 * Questo è necessario poichè un utente potrebbe posizionare le navi in ordine diverso da quello in cui compaiono nel vettore, inoltre
	 * serve per determinare la dimensione della nave scelta nella vista dall'utente.
	 * 
	 * @return int Indice della nave nel vettore corrispondente a quella scelta.
	 */
	private int getVectorIndex() {
		for(int x = 0;x < shipsVet.size(); x++) {
			if(selectedShip.equals(shipsVet.get(x).getShipName()))
				return x;
		}
		return 0;
	}
	
	/**
	 * Questa funzione gestisce il caso 3 di positioningState, il caso 3 si verifica quando l'utente preme il bottono reset, per 
	 * cancellare dal campo ogni nave posizionata e iniziare da capo.
	 * Questa funzione inizializza la matrice del modello.
	 * 
	 */
	protected void reset()
	{
		this.humanMatrix.init();
		positioningState = 3;
		setChanged();
		notifyObservers();
		positioningState = 0;
		placedShips = 0;
	}
	
	/**
	 * Funzione richiamata quando l'utente ha posizionato tutte le navi, quindi quando PositioningState è 4, e l'utente preme il pulsante per
	 * iniziare una nuova partita.
	 * Tale funzione inizia la partita.
	 * 
	 */
	protected void start() {
		positioningState = 5;
		setChanged();
		notifyObservers();
		System.out.print(humanMatrix.toString());
		BattleshipPlayerHuman human = new BattleshipPlayerHuman(humanMatrix.getSize(),playerName,shipsVet,this.humanMatrix);;
		this.cpuMatr = new PlayerShipMatrix(fieldDim);
		this.randPlacement(cpuMatr);
		BattleshipPlayerCPU cpu  = new BattleshipPlayerCPU(cpuMatr.getSize(), cpuMode, shipsVet, this.cpuMatr);
		BattleshipModel model = new BattleshipModel(humanMatrix.getSize(),timeFlag,human,cpu,this.humanMatrix.getSize());//Inizia il gioco
		BattleshipController cont = new BattleshipController(model);
		new BattleshipView(cont, model);
	}
	
	
	/**
	 * Gestisce il posizionamento casuale delle navi dell'utente.
	 */
	protected void randPositioning() {
		randPlacement(humanMatrix);
		positioningState = 6;
		setChanged();
		notifyObservers();	
	}
	
	/**
	 * Funzione che posiziona casualmente le navi nel campo di battaglia.
	 * 
	 */
	protected void randPlacement(PlayerShipMatrix matrix) {
		int nIter = 0;
		Random genPos = new SecureRandom();
		boolean errFlag = false;
		int dimShip,posX,posY,dir;
		do {
			matrix.init();
			for(int i = shipsVet.size()-1; i >= 0; i--) {
				dimShip = shipsVet.get(i).getSize();
				do {
					nIter++;
					if(nIter == 200) {
						matrix.init();
						nIter = 0;
						errFlag = true;
						i = shipsVet.size()-1;
					}
					do {
						posX = (genPos.nextInt(fieldDim))%fieldDim;
						posY = (genPos.nextInt(fieldDim))%fieldDim;
					}
					while(!matrix.checkPosPoint(posX,posY));
					dir = (genPos.nextInt(4))%4;
					//verso alto = 0, verso destra = 1, verso basso = 2, verso sinistra = 3;
				}
				while(!matrix.checkPosDirection(posX,posY,dir,dimShip) && !errFlag);
				if(errFlag == false) {
					matrix.loadGrid(posX,posY,dir,dimShip);
					nIter = 0;
				}
			}
		}while(!checkGrid());
	}
	
	/**
	 * Questa funzione controlla se la matrice contiene il corretto numero di casselle impostate ad 1, lo verifica
	 * contandole e verificando l'uguaglianza del conteggio con la somma delle dimensioni di tutte le navi da posizionare.
	 * 
	 * @return Boolean true per indicare se la griglia contiene il giusto numero di navi, false altrimenti.
	 */
	private boolean checkGrid() {
		int count1 = 0;
		for(int x = 0; x < fieldDim; x++) {
			for(int y = 0; y < fieldDim; y++)
				if(humanMatrix.getValueOf(x, y) == 1)
					count1++;
		}
		int count2 = 0;
		for(int x = 0; x < shipsVet.size(); x++)
			count2 = count2 + shipsVet.get(x).getSize();
		if(count1 == count2) return true;
		else return false;
	}
}
