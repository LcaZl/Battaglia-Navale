/**
 * 
 */
package upo.battleship.zanolo.battagliaNavale;

import java.util.Observable;
import java.util.Observer;

import upo.battleship.zanolo.giocatori.BattleshipPlayerCPU;
import upo.battleship.zanolo.giocatori.BattleshipPlayerHuman;
import upo.battleship.zanolo.menuPrincipale.MainMenuController;
import upo.battleship.zanolo.menuPrincipale.MainMenuModel;
import upo.battleship.zanolo.menuPrincipale.MainMenuView;
import upo.battleship.zanolo.timer.TimerController;
import upo.battleship.zanolo.timer.TimerModel;
import upo.battleship.zanolo.timer.TimerView;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.time.*;

/**
 * Modello della partita, esso gestirà l'intera partita e si occupa di ricevere e inviare o inoltrare notifiche
 * a tutte le componenti connesse, è il fulcro della partita.
 * 
 * @author luca
 *
 */
public class BattleshipModel extends Observable implements Observer, Serializable{

	private static final long serialVersionUID = 1623821578513537643L;
		private BattleshipPlayerCPU cpu;
		protected BattleshipPlayerHuman human;
		protected int fieldDim;
		private boolean isStarted;
		protected boolean timerFlag;
		protected int turn;
		protected String state;
		private String winner;
		protected int nTurni;
		protected LocalDateTime saveTime;
		protected boolean gameLoaded;
		protected TimerModel tModel;
		protected TimerView tView;
		private int saveRemMin;
		private int saveRemSec;

		public BattleshipModel(int boardSize, boolean timeFlag,BattleshipPlayerHuman h,BattleshipPlayerCPU c,int dim) {
			human = h;
			cpu  = c;
			this.fieldDim = dim;
			isStarted = false;
			this.gameLoaded = false;
			turn = 2;//turni dei giocatori: 1:human 0:cpu 2:per eliminare il bottone
			human.addObserver(this);
			cpu.addObserver(this);
			this.addObserver(human);
			this.addObserver(cpu);
			nTurni = 0;
			timerFlag = timeFlag;
			winner = null;
		}
		
		protected BattleshipPlayerCPU getCpuModel() {
			return cpu;
		}
		
		protected BattleshipPlayerHuman getHumanModel() {
			return human;
		}
		
		/**
		 * Funzione che crea il timer.
		 * Invocata solamente in caso di scelte dell'utente di giocare con un timer.
		 */
		protected void createTimer() {
			if(this.gameLoaded) {
				tModel = new TimerModel(saveRemMin,saveRemSec, fieldDim);
				tView = new TimerView(tModel);
				new TimerController(tView,tModel);
			}
			else {
				tModel = new TimerModel(fieldDim);
				tView = new TimerView(tModel);
				new TimerController(tView,tModel);
			}
			tModel.addObserver(this);
		}
		
		/**
		 * Funzione che decreta l'inizio del gioco inviando le apposite notifiche ai vari componenti.
		 */
		protected void startBattle() {
			isStarted = true;
			setChanged();
			notifyObservers(10);//la notifica viene inviata solamente a BattleshipView
			setChanged();
			notifyObservers(11);
		}
		
		/**
		 * @return Valore di isStarted, se invocata comunica se la partita è iniziata oppure no.
		 */
		protected boolean isStarted() {
			return this.isStarted;
		}
		
		/**
		 * Funzione che controlla se uno dei due giocatori ha vinto.
		 * Questa funzione viene invocata ad ogni passaggio di turno.
		 * In caso positivo, ovvero se uno dei due giocatori ha points = 0, imposta winner
		 * con il nome del vincitore e decreta lo stop del gioco impostando isStarted a false
		 * e notificandolo alla vista.
		 * 
		 */
		private void checkWinner() {
			if(cpu.getPoints() == 0) {
				isStarted = false;
				winner = human.getName();
				setChanged();
				notifyObservers(21);
				setChanged();
				notifyObservers(16);
			}
			else if(human.getPoints() == 0) {
				isStarted = false;
				winner = "Cpu";
				setChanged();
				notifyObservers(21);
				setChanged();
				notifyObservers(16);
			}
		}
		
		/**
		 * Questa funzione viene invocata solamente in partite in cui è stato scelto di 
		 * utilizzare un timer.
		 * Se invocata significa che il tempo è scaduto.
		 * Il vincitore sarà automaticamente CPU.
		 * 
		 */
		private void endGameTimer() {
			isStarted = false;
			winner = "Cpu";
			setChanged();
			notifyObservers(16);
		}
		
		/**
		 * @return Numero di turni fino al momento del richiamo di tale funzione.
		 */
		protected int getTurni() {
			return nTurni;
		}
			
		/**
		 * Funzione che ritorna una stringa contenente il nome del vincitore.
		 * 
		 * @return Stringa rappresentante il vincitore.
		 */
		protected String getWinner() {
			return winner;
		}
		
		/**
		 * @return Stringa rappresentante lo stato di Human.
		 */
		protected String getHumanState() {
			return "TURNO " + nTurni + ":\n" + human.getName() + " " + cpu.getState();
		}
		
		/**
		 * Questa funzione genera una stringa con le informazioni principali del turno e i punti di cpu e human.
		 * 
		 * @return Stringa rappresentante lo stato di Cpu.
		 */
		protected String getCpuState() {
			return human.getState() + "\nPunti CPU : " + cpu.getPoints() + "\nPunti " + human.getName() + ": " + human.getPoints() + "\n";
		}

		/**
		 * Funzione che si occupa del salvataggio dei dati della partita.
		 * Invocata se l'utente scegli da GUI di salvare.
		 */
		protected void saveGame() {
			if(isStarted) {
				ObjectOutputStream oss1;//oss2,oss3;
				if(this.timerFlag) {
					this.saveRemMin = tModel.getMinutes();
					this.saveRemSec = tModel.getSeconds();
					this.tModel = null;
					this.tView = null;
				}
				try {
					saveTime = LocalDateTime.now();
					oss1 = new ObjectOutputStream(new FileOutputStream("./Salvataggi/S1_" + saveTime + "_" + human.getName()));
					oss1.writeObject(this);
					oss1.close();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		
		/**
		 * Funzione che fa ritornare l'utente al menu principale senza salvare la partita.
		 */
		protected void backMenu() {
			if(isStarted) {
				MainMenuModel model = new MainMenuModel();
				MainMenuView view = new MainMenuView(model);
				new MainMenuController(model,view);
				setChanged();
				notifyObservers(12);
			}
		}
		
		/**
		 * Funzione che salva la partita prima di far tornare l'utente al menu principale.
		 */
		protected void saveBackMenu() {
			if(isStarted()){
				this.saveGame();
				this.backMenu();
			}
		}
		
		
		/**
		 * Funzione invocata esclusivamente nel caso in cui il model venga caricato da un
		 * salvataggio.
		 * Questa funzione reimposta gli observers.
		 */
		private void refresh() {
			human.addObserver(this);
			cpu.addObserver(this);
			this.addObserver(human);
			this.addObserver(cpu);	
		}

		@Override
		public void update(Observable arg0, Object arg1) {
			int n = (int)arg1;
			if(n == 23) {//notifica da BattleshipPlayerCPU
				if(isStarted) {
					human.setHitInPoint(cpu.getHitOutPoint());
					setChanged();
					notifyObservers(13);
					cpu.setHitOutFlag(human.getHitInFlag());
					setChanged();
					notifyObservers(14);
				}
				checkWinner();
			}
			if(n == 33) {//notifica da BattleshipPlayerHuman (dira se cpu ha colpito una nave e poi si aspetta che human colpisca)
				nTurni++;
				checkWinner();
				if(isStarted) {
					setChanged();
					notifyObservers(11);
					setChanged();
					notifyObservers(15);
				}
			}
			if(n == 41) {
				endGameTimer();
			}
			if(n == 32) {
				cpu.setLastHitDestroyShip(true);
			}
			if(n == 52) {
				gameLoaded = true;
				human.setGameLoaded(true);
				cpu.setGameLoaded(true);
				if(this.timerFlag) createTimer();
				refresh();
			}
		}
}
