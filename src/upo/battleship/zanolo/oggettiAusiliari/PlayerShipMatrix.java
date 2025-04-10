package upo.battleship.zanolo.oggettiAusiliari;

import java.awt.Point;
import java.io.Serializable;

/**
 * 
 * PlayerShipMatrix è un oggetto che gestisce una matrice, una matrice di battaglia navale.
 * 
 * @author luca
 * 
 */
public class PlayerShipMatrix implements Serializable{

	private static final long serialVersionUID = 6248398899164360917L;
	private Integer[][] matrix;
	private int size;
	
	/**
	 * Questa funzione crea e inizializza una nuova matrice.
	 * Le dimensioni sono pari al valore in input dim.
	 * 
	 * @param dim Dimensione della matrice.
	 */
	public PlayerShipMatrix(int dim) {
		matrix = new Integer[dim][dim];
		init();
		size = dim;
	}
	
	/**
	 * Questa funzione inizializza la matrice impostanto ogni valore di ogni posizione a 0.
	 */
	public void init() {
		for(int x = 0; x < matrix.length; x++) {
			for(int y = 0; y < matrix.length; y++)
				matrix[x][y] = 0;
		}	
	} 
	
	/**
	 * Questa funzione inserisce nella matrice una nave impostando i valori
	 * corrispondenti alla sua posizione ad 1.
	 * 
	 * @param posX Valore x della posizione iniziale
	 * @param posY Valore y della posizione inizia
	 * @param dir Direzione in cui deve essere posizionata
	 * @param dim Dimensione della nave da posizionare.
	 */
	public void loadGrid(int posX,int posY,int dir,int dim){
		for(int k = 0; k < dim; k++) {
			switch(dir) {
				case 0: matrix[posX][posY] = 1;posY--; break;
				case 1: matrix[posX][posY] = 1;posX++; break;
				case 2: matrix[posX][posY] = 1;posY++; break;
				case 3: matrix[posX][posY] = 1;posX--; break;
			}
		}
	}
	
	/**
	 * Questa funzione restituisce la dimensione della matrice.
	 * 
	 * @return int Dimensione della matrice.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Questa funzione presi in input le coordinate di un punto, restituisce il valore corrispondente.
	 * 
	 * @param x Coordinata x.
	 * @param y Coordinata y.
	 * @return int
	 *
	 */
	public int getValueOf(int x,int y) {
		return matrix[x][y];
	}

	
	/**
	 * Questa funzione preso in input le coordinate di un punto e un valore, imposta il valore di tale punto al valore in input.

	 * 
	 * @param x Coordinata x.
	 * @param y Coordinata y.
	 * @param val Valore da inserire.
	 *
	 */
	public void setPoint(int x, int y, int val) {
		if(val >= 0 && val <= 4)
			matrix[x][y] = val;
	}
	

	/**
	 * Questa funzione preso in input un punto, restituisce true se il punto è circondato da soli punti con valore 0, false altrimenti.
	 *
	 * @param p Punto da analizzare.
	 * @return Ritorna true se il punto è circondato solamente da caselle con valore 0, false altrimenti.
	 */
	public boolean checkPosPoint(Point p) {
		return checkPosPoint(p.x,p.y);
	}
	
	/**
	 * Questa funzione controlla se il punto è circondato solamente da caselle con valore 0.
	 * 
	 * @param posX Coordinata x.
	 * @param posY Coordinata y.
	 * @return Ritorna true se il punto è circondato solamente da caselle con valore 0, false altrimenti.
	 */
	public boolean checkPosPoint(int posX,int posY) {
		int len = matrix.length-1;
		int ix= 1;
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
				if(x <= len && y <= len)
					if(matrix[x][y] >= 1)
						return false;	
		return true;
		
	}

	/**
	 * Data una direzione, un punto e una dimensione questa funzione controlla
	 * se la nave può essere inserita correttamente.
	 * Ovvero se per ogni punto in cui verrà inserita tale punto non ha punti adiacenti con valore diverso da 0.
	 * Viene controllato anche che l'inserimento possa essere effettuato senza superare i limiti di dimensione della matrice.
	 *
	 * 
	 * @param posX Coordinata x.
	 * @param posY Coordinata y.
	 * @param dir Direzione di posizionamento.
	 * @param dim Lunghezza della nave da posizionare.
	 * @return boolean Ritorna true se il punto è circondato solamente da caselle con valore 0, false altrimenti.
	 * 
	 */
	public boolean checkPosDirection(int posX,int posY,int dir,int dim) {
		switch(dir) {
		case 0: //verso alto
			for(int k = 0; k < dim; k++) {
				posY--;
				if(posY < 0)
					return false;
				else
					if(!checkPosPoint(posX,posY)) {
						return false;
					}
			}
		break;
		case 1: //verso destra
			for(int k = 0;k < dim;k++) {
				posX++;
				if(posX > size)
					return false;
				else
					if(!checkPosPoint(posX,posY))
						return false;
			}	
		break;
		case 2: //verso basso
			for(int k = 0;k < dim;k++) {
				posY++;
				if(posY > size)
					return false;
				else
					if(!checkPosPoint(posX,posY))
						return false;
			}
		break;
		case 3: //verso sinistra
			for(int k = 0;k < dim;k++) {
				posX--;
				if(posX < 0) 
					return false;
				else
					if(!checkPosPoint(posX,posY))
						return false;
			}
		break;
		}
		return true;//se arrivo qui la direzione è corretta
	}
	
	/**
	 * Questa funzione restituisce una rappresentazione grafica della matrice.
	 */
	public String toString() {
		String s = new String();
		for(int y = 0; y < matrix.length; y++) {
			for(int x = 0; x < matrix.length; x++)
				s = s + matrix[x][y] + " - ";
			s = s + "\n";
		}
		return s + "\n";
	 }

}
