package upo.battleship.zanolo.timer;


import java.io.Serializable;

/**
 * Controller di un timer, appena creato questo oggetto imposta la view come observer del model
 * e fa partire il timer.
 * 
 * @author luca
 *
 */
public class TimerController implements Serializable {

	private static final long serialVersionUID = -7156725343238097826L;
	private TimerView view;
	private TimerModel model;
	
	/**
	 * Avvia il Timer.
	 * 
	 * @param v Rappresentazione grafica del Timer.
	 * @param m Modello con specifiche di funzionamento del Timer.
	 */
	public TimerController(TimerView v, TimerModel m) {
		super();
		this.view = v;
		this.model = m;
		model.addObserver(view);
		view.refreshView(model.toString());
		model.startTimer();
	}
}
