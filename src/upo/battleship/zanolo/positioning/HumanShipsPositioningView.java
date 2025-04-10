package upo.battleship.zanolo.positioning;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.border.*;

import upo.battleship.zanolo.oggettiAusiliari.PlayerShipMatrix;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;


/**
 * Vista del posizionamento delle navi.
 * 
 * @author luca
 *
 */
public class HumanShipsPositioningView extends JFrame implements Observer{

	private static final long serialVersionUID = 1L;
	private HumanShipsPositioningModel model;
	private HumanShipsPositioningController controller;  
	private JLabel shipsPanel;
	private JLabel fieldPanel;
	private GridBagConstraints gbc = new GridBagConstraints();
	private JLabel[][] grid;
	private int fieldDim;
	private Border border;
	private PlayerShipMatrix matrix;
	private JButton startButton;
	private JButton resetButton;
	private JButton randPlace;
	protected ButtonGroup bGroup;


	public HumanShipsPositioningView(HumanShipsPositioningModel m, HumanShipsPositioningController c) {
		this.model = m;
		this.controller = c;
		this.matrix = m.humanMatrix;
		model.addObserver(this);
		build();
	}
	
	private void build() {
		this.setTitle("Human - Posizionamento Navi");
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
				
		fieldDim = matrix.getSize();
		border = BorderFactory.createLineBorder(Color.black);

		shipsPanel = new JLabel();
		shipsPanel.setPreferredSize(new Dimension(600,1400));
		shipsPanel.setLayout(new BorderLayout());
		shipsPanel.setHorizontalAlignment(JLabel.LEFT);
		shipsPanel.setBorder(border);

		fieldPanel = new JLabel();
		fieldPanel.setLayout(new GridLayout(fieldDim,fieldDim));
		fieldPanel.setBorder(border);
		
		//creazione campo posizionamento navi.
		createShipsField();

		//creazione titolo e descrizione menu a sinistra.
		JLabel titleLabel = new JLabel("Posizionamento");
		titleLabel.setPreferredSize(new Dimension(500,60));
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("Times New Roman",Font.BOLD, 30));
		JLabel infoLabel = new JLabel("<html>Per posizionare:<BR>Selezionare la nave che si desidera posizionare,"
				+ "sucessivamente selezionare il quadrato della griglia da cui direzionarla. Per direzionarla: <br>"
				+ "selezionare un quadrato adiacente nella direzione preferita, la nave verrà <br>"
				+ "posizionata immediatamente se possibile</html>");
		infoLabel.setFont(new Font("times New Roman", Font.PLAIN, 12));
		infoLabel.setPreferredSize(new Dimension(500,80));
		
		JLabel tmp = new JLabel();
		tmp.setPreferredSize(new Dimension(500,150));
		tmp.setLayout(new BoxLayout(tmp, BoxLayout.Y_AXIS));
		tmp.add(titleLabel);
		tmp.add(infoLabel);
		//aggiunta al menu di sinistra del titolo e la descrizione.
		shipsPanel.add(tmp, BorderLayout.PAGE_START);
		
		//creazione della lista di navi diponibili per il posizionamento.
		JLabel temp = new JLabel();
		temp.setLayout(new GridBagLayout());
		temp.setPreferredSize(new Dimension(600,1100));
		gbc.insets = new Insets(20,0,0,50);
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		bGroup  =  new ButtonGroup();
		createShipsList(temp);
		JScrollPane tempJsp = new JScrollPane(temp);
		tempJsp.setPreferredSize(new Dimension(600, 700));
		tempJsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		shipsPanel.add(tempJsp, BorderLayout.CENTER);

		resetButton = new JButton("Reset");
		resetButton.addActionListener(controller);
		resetButton.setPreferredSize(new Dimension(100,100));
		resetButton.setFont(new Font("Times New Roman",Font.PLAIN,20));
		
		startButton = new JButton("Inizia");
		startButton.addActionListener(controller);
		startButton.setPreferredSize(new Dimension(100,100));
		startButton.setFont(new Font("Times New Roman",Font.PLAIN,20));
		startButton.setEnabled(false);
		
		randPlace = new JButton("Posizionamento Casuale");
		randPlace.addActionListener(controller);
		randPlace.setPreferredSize(new Dimension(100,100));
		randPlace.setFont(new Font("Times New Roman",Font.PLAIN,20));
		randPlace.setEnabled(true);
		
		JLabel buttonLabel = new JLabel();
		buttonLabel.setLayout(new BorderLayout());
		buttonLabel.setPreferredSize(new Dimension(600,200));
		buttonLabel.add(resetButton, BorderLayout.LINE_START);
		buttonLabel.add(startButton, BorderLayout.LINE_END);
		buttonLabel.add(randPlace, BorderLayout.CENTER);
		shipsPanel.add(buttonLabel, BorderLayout.PAGE_END);
		
		this.add(shipsPanel, BorderLayout.LINE_START);
		this.add(fieldPanel,BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	/**
	 * Questa funzione crea il menu di scelta della nave da posizionare in base alle navi disponibili.
	 * 
	 * @param l Label da scrivere
	 * 
	 */
	private void createShipsList(JLabel l){
		for(int x = 0;x < model.shipsVet.size(); x++)
		{
			gbc.gridy = x+2;
			JLabel shipDim = new JLabel("(Dimensione : " + model.shipsVet.get(x).getSize() + ")");
			shipDim.setFont(new Font("Times New Roman",Font.PLAIN, 20));
			JRadioButton sel = new JRadioButton(model.shipsVet.get(x).getShipName());
			sel.addActionListener(controller);
			sel.setFont(new Font("Times New Roman",Font.PLAIN,20));
			sel.setPreferredSize(new Dimension(270,40));
			bGroup.add(sel);
			gbc.gridx = 0;
			l.add(sel, gbc);
			gbc.gridx = 1;
			l.add(shipDim, gbc);
		}
	}
	
	/**
	 * Questa funzione crea il campo di battaglia in cui posizionare le navi.
	 */
	private void createShipsField() {
		grid = new JLabel[fieldDim][fieldDim];
		for(int y = 0;y < fieldDim; y++) {
			for(int x = 0; x < fieldDim; x++) {
				grid[x][y] = new JLabel();
				grid[x][y].setPreferredSize(new Dimension(30,30));
				grid[x][y].setHorizontalAlignment(JLabel.CENTER);
				grid[x][y].setVerticalAlignment(JLabel.CENTER);
				grid[x][y].setBorder(border);
				grid[x][y].setOpaque(true);
				if(matrix.getValueOf(x, y) == 0) {
					grid[x][y].setBackground(Color.blue);
					grid[x][y].addMouseListener(controller);
				}
				else
					grid[x][y].setBackground(Color.GRAY);
				grid[x][y].setName(x + "-" + y);
			    fieldPanel.add(grid[x][y]);
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		switch(model.getPositioningState()) {
		case 1:
			Point p = (Point)arg;
			grid[(int)p.x][(int)p.y].setBackground(Color.GRAY);
		break;
		case 2:
			fieldPanel.removeAll();
			createShipsField();
			fieldPanel.validate();
			fieldPanel.repaint();
			bGroup.getSelection().setEnabled(false);
		break;
		case 3:
			fieldPanel.removeAll();
			createShipsField();
			fieldPanel.validate();
			fieldPanel.repaint();
			for(Enumeration<AbstractButton> buttons = bGroup.getElements(); buttons.hasMoreElements();) {
				AbstractButton button = buttons.nextElement();
				button.setEnabled(true);
			}
			this.startButton.setEnabled(false);
		break;
		case 4:
			this.startButton.setEnabled(true);
		break;
		case 5:
			this.setVisible(false);
			this.dispose();
		break;
		case 6:
			for(Enumeration<AbstractButton> buttons = bGroup.getElements(); buttons.hasMoreElements();) {
				AbstractButton button = buttons.nextElement();
				button.setEnabled(false);
			}
			fieldPanel.removeAll();
			createShipsField();
			fieldPanel.validate();
			fieldPanel.repaint();
			startButton.setEnabled(true);
		break;
		}	
	}
}
