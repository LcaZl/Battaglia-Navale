package upo.battleship.zanolo.giocatori;

import java.util.Observable;
import java.util.Vector;

import upo.battleship.zanolo.oggettiAusiliari.PlayerShipMatrix;
import upo.battleship.zanolo.oggettiAusiliari.Ship;

import java.awt.Point;
import java.io.Serializable;

/**
 * Questa classe implementa l'interfaccia Battleship e fornisce un implementazione di tutti
 * i metodi di quest ultima.
 * 
 * @author luca
 */
public abstract class BattleshipPlayer extends Observable implements Battleship, Serializable{
	
	private static final long serialVersionUID = 6465934282539580176L;
	protected int points;
	protected PlayerShipMatrix grid;//0:nebbia, 1:mare.
	protected boolean gameLoaded;
	
	/**
	 * @return Ritorna il valore di gameLoaded.
	 */
	public boolean isGameLoaded() {
		return gameLoaded;
	}

	/**
	 * @param gameLoaded Imposta il valore di gameloaded al parametro in input.
	 */
	public void setGameLoaded(boolean gameLoaded) {
		this.gameLoaded = gameLoaded;
	}

	protected Vector<Ship> shipsList;
	protected Vector<Point> lastDestroyedShip;
	protected boolean hitInFlag; //se punto colpito era nave.
	protected Point hitInPoint; //punto colpito.
	protected String state;//Commento cpu o human.

	
	@Override
	public int calcPoints(Vector<Ship> v) {
		int sum = 0;
		for(int x = 0; x < v.size(); x++) {
			sum = sum + v.get(x).getSize();
		}
		return sum;
	}
	
	@Override
	public int getPoints() {
		return this.points;
	}
	
	/**
	 * @return Ritorna hitInFlag.
	 */
	public boolean getHitInFlag() {
		return hitInFlag;
	}

	/**
	 * @param hitInFlag Valore a cui impostare hitInFlag.
	 */
	public void setHitInFlag(boolean hitInFlag) {
		this.hitInFlag = hitInFlag;
	}

	/**
	 * @return Ritorna hitInPoint.
	 */
	public Point getHitInPoint() {
		return hitInPoint;
	}

	/**
	 * @param hitInPoint Valore a cui impostare hitInPoint.
	 */
	public void setHitInPoint(Point hitInPoint) {
		this.hitInPoint = hitInPoint;
	}

	@Override
	public String getState() {
		return this.state;
	}
	
	@Override
	public Vector<Point> checkDestroyedShip(Point lastHit) {
				int dir = findDirection(lastHit);
				if(dir == 2) return null; //non esistono navi di dimensione 1
				Point start = new Point();
				Point end = new Point();
				switch(dir) {
					case 1:
						int posY1 = lastHit.y-1;//su
						int posY2 = lastHit.y+1;//giu
						int posX = lastHit.x;
						while((posY1 >= 0)&&(grid.getValueOf(posX, posY1) == 2))
							posY1 = posY1 - 1;
						while((posY2 <= grid.getSize()-1)&&(grid.getValueOf(posX, posY2) == 2))
							posY2 = posY2 + 1;//=10
						if((posY1 < 0)||(grid.getValueOf(posX, posY1) == 0)||(grid.getValueOf(posX, posY1) == 3))
							if((posY2 > grid.getSize()-1)||(grid.getValueOf(posX, posY2) == 0)||(grid.getValueOf(posX, posY2) == 3)) {
								start.x = posX;
								start.y = posY1+1;
								end.x = posX;
								end.y = posY2-1;
								Vector<Point> v = new Vector<Point>();
								v.add(start);
								v.add(end);
								return v;
							}
						break;
					case 0:
						int posX1 = lastHit.x-1;//sx
						int posX2 = lastHit.x+1;//dx
						int posY = lastHit.y;
						while((posX1 >= 0)&&(grid.getValueOf(posX1, posY) == 2))
							posX1 = posX1 - 1;
						while((posX2 <= grid.getSize()-1)&&(grid.getValueOf(posX2, posY) == 2))
							posX2 = posX2 + 1;
						if((posX1 < 0)||(grid.getValueOf(posX1, posY) == 0)||(grid.getValueOf(posX1, posY) == 3))
							if((posX2 > grid.getSize()-1)||(grid.getValueOf(posX2, posY) == 0)||(grid.getValueOf(posX2, posY) == 3)) {
								start.x = posX1+1; start.y = posY;
								end.x = posX2-1; end.y = posY;
								Vector<Point> v = new Vector<Point>();
								v.add(start);
								v.add(end);
								return v;
							}
						break;
				}	
				System.out.print("aaa");
				return null;
	}
	
	@Override
	public int findDirection(Point lastHit) {
		int posX = lastHit.x;
		int posY = lastHit.y;
		if((posX-1 >= 0)&&(grid.getValueOf(posX-1, posY) == 2))
				return 0;//dx e sx
		if((posX+1 <= (grid.getSize()-1))&&(grid.getValueOf(posX+1, posY) == 2))
				return 0;//dx e sx
		if((posY-1 >= 0)&&(grid.getValueOf(posX, posY-1) == 2))
				return 1;//su giù
		if((posY+1 <= (grid.getSize()-1))&&(grid.getValueOf(posX, posY+1) == 2))
				return 1;//su e giu
		return 2;
	}
	
	@Override
	public void setGridDestroyed(Point start, Point end) {
		int posXs = start.x;
		int posYs = start.y;
		int posXe = end.x;
		int posYe = end.y;
		if(posYs == posYe) {
			if(posXs < posXe) {
				while(posXs <= posXe) {
					grid.setPoint(posXs, posYs, 4);
					posXs++;
				}
			}
			else if(posXs > posXe) {
				while(posXe <= posXs) {
					grid.setPoint(posXe, posYs, 4);
					posXe++;
				}
				posXe++;
			}
		}
		else if(posXs == posXe) {
			if(posYs < posYe) {
				while(posYs <= posYe) {
					grid.setPoint(posXs, posYs, 4);
					posYs++;
				}
			}
			else if(posYs > posYe) {
				while(posYe <= posYs) {
					grid.setPoint(posXs, posYe, 4);
					posYe++;
				}
			}
		}
	}
	
	/**
	 * Questa funzione abstract elabora i dati impostati dal modello di Battleship.
	 * Tali dati sono relativi all'ultimo colpo ricevuto.
	 * La sua implementazione sarà definita in modi differenti da CPU e Human.
	 *  
	 */
	protected abstract void getHitIn();

}
