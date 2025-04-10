package upo.battleship.zanolo.battagliaNavale;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import upo.battleship.zanolo.giocatori.BattleshipCPUFieldView;
import upo.battleship.zanolo.giocatori.BattleshipHumanFieldView;
import upo.battleship.zanolo.timer.TimerView;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

/**
 * Vista di Battleship. Essa crea il frame principale che conterrà tutti i vari componenti.
 * Tali componenti non sono tutti presenti in questa classe. I campi dei due giocatori
 * infatti vengono forniti da giocatori stessi.
 * Questa vista si occupa della gestione delle viste dei giocatori, del timer, dell'info della partita
 * e del menu con le azioni possibili durante la partita.
 * 
 * @author luca
 *
 */
public class BattleshipView extends JFrame implements Observer{

	private static final long serialVersionUID = 6251254989243528741L;
	private BattleshipCPUFieldView cpuView;
	private BattleshipHumanFieldView humanView;
	private BattleshipController controller;
	private BattleshipModel model;
	private JPanel gameInfo;
	protected JTextArea turn;
	private JButton startGame;
	private TimerView tView;
	
	public BattleshipView(BattleshipController c, BattleshipModel m) {
		super();
		this.model = m;
		this.controller = c;
		model.addObserver(this);
		build();
		if(m.gameLoaded)
			refresh();
	}
	
	private void build() {	
		this.setTitle("Battaglia Navale");
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		//Viste dei giocatori.
		humanView = new BattleshipHumanFieldView(model.getHumanModel());
		cpuView = new BattleshipCPUFieldView(model.getCpuModel(),controller.getCpuController());
		
		TitledBorder bord = new TitledBorder(new LineBorder(Color.BLACK),"Info Partita");
		bord.setTitlePosition(TitledBorder.LEFT);
		bord.setTitleFont(new Font("Times New Roman",Font.BOLD,30));
		//Pannello che conterrà, una volta iniziata la partita, la JTextArea.
		gameInfo = new JPanel(new BorderLayout());
		gameInfo.setPreferredSize(new Dimension(1440,300));
		gameInfo.setBorder(bord);
		//Bottone inizialmente presente in gameInfo.
		startGame = new JButton("[PREMI]Inizia la battaglia![PREMI]");
		startGame.setSize(200,150);
		startGame.addActionListener(controller);
		startGame.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		gameInfo.add(startGame, BorderLayout.CENTER);
		//Contenitore dei campi dei giocatori.
		JLabel viewsCont = new JLabel();
		viewsCont.setLayout(new FlowLayout());
		viewsCont.setPreferredSize(new Dimension(1840,900));
		viewsCont.add(humanView);
		viewsCont.add(cpuView);
		//Creazione menu a tendina.
		Font f = new Font("Times New Roman", Font.PLAIN, 20);
		JMenuBar frameBar = new JMenuBar();
		frameBar.setFont(f);
		JMenu m1 = new JMenu("Partita");
		JMenuItem i1 = new JMenuItem("Esci");
		JMenuItem i2 = new JMenuItem("Salva ed esci");
		JMenuItem i3 = new JMenuItem("Menu principale");
		JMenuItem i4 = new JMenuItem("Salva e torna al menu principale");
		i1.addActionListener(controller);
		i2.addActionListener(controller);
		i3.addActionListener(controller);
		i4.addActionListener(controller);
		m1.setFont(f);
		i1.setFont(f);
		i2.setFont(f);
		i3.setFont(f);
		i4.setFont(f);
		m1.add(i1);
		m1.add(i2);
		m1.add(i3);
		m1.add(i4);
		frameBar.add(m1);
		this.setJMenuBar(frameBar);
		this.add(gameInfo, BorderLayout.PAGE_START);
		this.add(viewsCont, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	/**
	 * Questa funzione crea il pannello di info della partita impostando anche il messaggio iniziale
	 * 
	 */
	private void createInfoPanel() {	
		turn = new JTextArea();
		turn.setPreferredSize(new Dimension(200,500));
		turn.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		turn.setEditable(false);
		turn.setText("Inizia la partita!\nInizia " + model.human.getName() + "\n---------------------------------------------\n\n");
		//problema scorrimento text area commenti partita
		if(model.timerFlag) {
			model.createTimer();
			tView = model.tView;
			gameInfo.add(tView, BorderLayout.LINE_END);
		}
		gameInfo.add(turn , BorderLayout.CENTER);
		gameInfo.validate();
		gameInfo.repaint();
	}
	
	/**
	 * Questa funzione genera il messaggio di fine partita  e lo inserisce nell JTextArea.
	 * 
	 */
	private void endGame() {
		if(model.tModel != null)
			tView.setVisible(false);
		turn.setText("PARTITA CONCLUSA!\n");
		turn.append("Il vincitore è : " + model.getWinner() + "\n" +""
				+ model.getWinner() + " ha vinto in " + model.getTurni() + " turni.\n");
		if(model.tModel != null)
			turn.append("Tempo : " + model.tModel.getMinutes() + ":" + model.tModel.getSeconds() + "\n");
		this.validate();
		this.repaint();
	}
	
	/**
	 * Funzione invocata esclusivamente in caso di caricamento di una partita salvata.
	 * Se il gioco nel salvataggio è gia iniziato, essa ricostruisce la vista e rimuove il bottone per iniziare la battaglia.
	 */
	private void refresh() {
		if(model.isStarted()) {
			build();
			this.startGame.setEnabled(false);
			this.startGame.setVisible(false);
			this.startGame.removeActionListener(controller);
			createInfoPanel();
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		int n = (int)arg;
		if(n == 10) {
			this.startGame.setEnabled(false);
			this.startGame.setVisible(false);
			this.startGame.removeActionListener(controller);
			createInfoPanel();
		}
		else if(n == 12) {
			this.dispose();
		}
		else if(n == 14) {
			turn.append(model.getCpuState() + "\n\n");
		}
		else if(n == 15) {
			turn.removeAll();
			turn.validate();
			turn.repaint();
			gameInfo.add(turn , BorderLayout.CENTER);
			gameInfo.validate();
			gameInfo.repaint();
			turn.setText(model.getHumanState() + "\n");
		}
		else if(n == 16) {
			endGame();
		}
	}
}
