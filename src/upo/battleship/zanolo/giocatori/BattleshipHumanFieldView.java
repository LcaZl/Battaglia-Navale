package upo.battleship.zanolo.giocatori;


import javax.swing.BorderFactory;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import javax.swing.border.*;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Color;

/**
 * Vista del campo di Human.
 * Essa non ha un controller in quanto non sono previste interazioni tra l'utente e i componenti.
 * 
 * @author luca
 */
public class BattleshipHumanFieldView extends BattleshipField implements Observer{
	
	private static final long serialVersionUID = 7086723061995774397L;
	
	public BattleshipHumanFieldView(BattleshipPlayerHuman m) {
		this.model = m;
		fieldDim = model.grid.getSize();
		model.addObserver(this);
		build();
		createField();
		if(model.gameLoaded)
			refresh();
		this.setVisible(true);
	}
	
	private void build() {
		grid = new JLabel[fieldDim][fieldDim];
		this.setLayout(new GridLayout(fieldDim,fieldDim));
		this.setPreferredSize(new Dimension(680,680));
	}
	
	@Override
	public void createField() {
		Border border = BorderFactory.createLineBorder(Color.black);
		for(int y = 0;y < fieldDim; y++) {
			for(int x = 0; x < fieldDim; x++) {
				grid[x][y] = new JLabel();
				grid[x][y].setPreferredSize(new Dimension(900/fieldDim,900/fieldDim));
				grid[x][y].setHorizontalAlignment(JLabel.CENTER);
				grid[x][y].setVerticalAlignment(JLabel.CENTER);
				grid[x][y].setFont(new Font("Times New Roman", Font.BOLD, 40));
				grid[x][y].setBorder(border);
				grid[x][y].setOpaque(true);
				if(model.grid.getValueOf(x, y) == 0) {
					grid[x][y].setBackground(Color.blue);
				}
				else
					grid[x][y].setBackground(Color.GRAY);
				grid[x][y].setName("" + x + "-" + y);
				grid[x][y].setForeground(Color.red);
				this.add(grid[x][y]);
			}
		}
	}

	@Override
	protected void reloadPoint() {	
			grid[model.hitInPoint.x][model.hitInPoint.y].setText("X");
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
				switch(model.grid.getValueOf(x, y)) {
					case 0:
						grid[x][y].setBackground(Color.BLUE);
					break;
					case 1:
						grid[x][y].setBackground(Color.GRAY);
					break;
					case 2:
						grid[x][y].setBackground(Color.GRAY);
						grid[x][y].setText("X");
					break;
					case 3:
						grid[x][y].setBackground(Color.BLUE);
						grid[x][y].setText("X");
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
		if(n == 31) {//notifica da BattleshipPlayerHuman
			reloadPoint();
		}
		if(n == 32) {//nave distrutta
			killShip();
		}
	}
}