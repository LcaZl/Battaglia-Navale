package upo.battleship.zanolo.timer;

import java.io.Serializable;
import java.util.Observable;


/**
 * Modello di Timer.
 * 
 * @author luca
 *
 */
public class TimerModel extends Observable implements Serializable{

	private static final long serialVersionUID = 3927177638799138770L;
	protected int minutes;
	protected int seconds;
	protected int timeLimit;
	private boolean ended;
	
	public TimerModel(int min) {
		timeLimit = min;
		minutes = 0;
		seconds = 0;
		ended = false;
		this.setChanged();
		this.notifyObservers();
	}
	
	public TimerModel(int min, int sec, int tLim) {
		timeLimit = tLim;
		minutes = min;
		seconds = sec;
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Funzione che restituisce una rappresentazione testuale del valore del Timer quando richiamata.
	 *
	 * @return String Stringa valorizzata con i valori del Timer.
	 */
	public String toString() {
		String res = "";
		res = res + (minutes<10 ? "0" + minutes : minutes);
		res = res + ":";
		res = res + (seconds<10 ? "0" + seconds : seconds);
		return res;
	}
	
	/**
	 * Funzione che fornisce i minuti del timer.
	 * 
	 * @return int Valore dei minuti.
	 */
	public int getMinutes() {
		return minutes;
	}


	/**
	 * Funzione che fornisce i secondi del timer.
	 * 
	 * @return int Valore dei secondi.
	 */
	public int getSeconds() {
		return seconds;
	}

	/**
	 * Funzione che restituisce il tempo limite.
	 * 
	 * @return int Valore del tempo limite del timer.
	 */
	public int getTimeLimit() {
		return timeLimit;
	}
	
	private void increase() {
		if(seconds<59) {
			seconds++;
		}
		else {
			seconds = 0;
			minutes++;
		}
		this.setChanged();
		this.notifyObservers(42);
	}
	
	private void timeEnd() {
		if(!ended) {
			ended = true;
			setChanged();
			notifyObservers(41);
		}
	}
	
	/**
	 * Funzione che fa iniziare il Timer.
	 * Invocata dal controller alla sua creazione.
	 * 
	 */
	protected void startTimer() {
		t = new TimerThread();
		t.start();
	}
	
	protected class TimerThread extends Thread implements Serializable{

		private static final long serialVersionUID = 8687452199153350476L;

		private TimerThread() {
			this.setDaemon(true);
		}

		@Override
		public void run() {
			while(true) {
				if(minutes == timeLimit) {
						try {
							t.interrupt();
							timeEnd();
						}
						catch(Exception e) {
							System.err.print("Errore (e) controller del timer");
							e.printStackTrace();
						}
					}
				else
					try {
						Thread.sleep(1000);
						increase();
					} 
					catch (InterruptedException e) {
							//doNothing
					}
					catch(Error e1) {
						System.err.print("Error nel Timer controller\n");
					}
			}
		}
	}
	TimerThread t;
}
