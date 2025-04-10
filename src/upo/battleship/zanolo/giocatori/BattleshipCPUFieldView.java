 package upo.battleship.zanolo.giocatori;

import java.util.Observable;
import java.util.Observer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

import upo.battleship.zanolo.oggettiAusiliari.PlayerShipMatrix;

/**
 * Vista del campo di battaglia della CPU.
 * 
 * @author luca
 */
public class BattleshipCPUFieldView extends BattleshipField implements Observer{

	private static final long serialVersionUID = -1695750299290935172L;
	private BattleshipCPUFieldController controller;
	private PlayerShipMatrix matrix;
	
	public BattleshipCPUFieldView(BattleshipPlayerCPU m,BattleshipCPUFieldController cont) {	
		this.model = m;
		this.controller = cont;
		this.matrix = model.grid; 
		model.addObserver(this);
		fieldDim = matrix.getSize();
		build();
		createField();
		if(model.gameLoaded) {
			refresh();
		}
		this.setVisible(true);
	}
	
	private void build() {
		grid = new JLabel[fieldDim][fieldDim];
		this.setLayout(new GridLayout(fieldDim,fieldDim));
		this.setPreferredSize(new Dimension(680,680));
	}

	
	@Override
	protected void createField()
	{
		Border border = BorderFactory.createLineBorder(Color.black);
		for(int y = 0;y < fieldDim; y++) {
			for(int x = 0; x < fieldDim; x++) {
				grid[x][y] = new JLabel();
				grid[x][y].setOpaque(true);
				grid[x][y].setName(x+"-"+y);
				grid[x][y].setBackground(Color.white);
				grid[x][y].setBorder(border);
				grid[x][y].setPreferredSize(new Dimension(900/fieldDim,900/fieldDim));
				this.add(grid[x][y]);
			}
		}
	}
	
	
	/**
	 * Funzione invocata alla fine del turno di Human, il campo di CPU viene
	 * temporaneamente disattivato in modo da evitare doppi colpi.
	 * Viene invocata anche alla fine della partita.
	 * 
	 */
	protected void disableGrid() {
		for(int x = 0; x < fieldDim; x++)
			for(int y = 0;y < fieldDim; y++) {
				grid[x][y].removeMouseListener(controller);
			}
	}
	
	/**
	 * Questa funzione riattiva il campo di CPU, ripristinando i listener per ciascuna casella
	 * non ancora colpita.
	 */
	protected void enableGrid() {
		for(int x = 0; x < fieldDim; x++)
			for(int y = 0;y < fieldDim; y++) {
				if(model.grid.getValueOf(x, y) <= 1)
					grid[x][y].addMouseListener(controller);
			}
	}
	
	@Override
	protected void reloadPoint() {
		if(model.hitInFlag)
			grid[model.hitInPoint.x][model.hitInPoint.y].setBackground(Color.GRAY);
		else if(!model.hitInFlag)
			grid[model.hitInPoint.x][model.hitInPoint.y].setBackground(Color.BLUE);	
		grid[model.hitInPoint.x][model.hitInPoint.y].removeMouseListener(controller);
	}
	
	@Override
	protected void killShip() {
		Point start = model.lastDestroyedShip.get(0);
		Point end = model.lastDestroyedShip.get(1);
		if(start.x == end.x) {
			while(start.y <= end.y) {
				grid[start.x][start.y].setBackground(Color.red);
				start.y = start.y + 1;
			}
		}
		else if(start.y == end.y) {
			while(start.x <= end.x) {
				grid[start.x][start.y].setBackground(Color.red);
				start.x = start.x + 1;
			}
		}
	}
	
	@Override
	protected void refresh() {
		for(int y = 0;y < fieldDim; y++) {
			for(int x = 0; x < fieldDim; x++) {
				grid[x][y].addMouseListener(controller);
				switch(model.grid.getValueOf(x, y)) {
				case 0:
				case 1:
					grid[x][y].setBackground(Color.white);
				break;
				case 2:
					grid[x][y].setBackground(Color.GRAY);
				break;
				case 3:
					grid[x][y].setBackground(Color.BLUE);
				break;
				case 4:
					grid[x][y].setBackground(Color.RED);
				break;
				}
			}
		}
	}
	@Override
	public void update(Observable o, Object arg) {
		int n = (int)arg;
		if(n == 21) {	
			reloadPoint();
			disableGrid();
		}
		if(n == 22) {//nave distrutta
			killShip();
		}
		if(n == 24) {
			enableGrid();
		}
	}
}
