package upo.battleship.zanolo.oggettiAusiliari;

import java.io.Serializable;



/**
 * Ship è una classe che rappresenta una nave, ne indica le dimensioni, il nome e un id univoco per 
 * ciascuna tipologia. 
 * 
 * @author luca
 *
 */
public class Ship implements Serializable{

	private static final long serialVersionUID = -2857475013380879836L;
	private int size;
	private String name;
	private int ID;

	/**
	 * Questa classe inizializza gli attributi dell'oggetto ship ai valori in input e ne
	 * genera il corrispondente id.
	 * 
	 * @param n Lunghezza della nave
	 * @param s Nome della nave
	 */
	public Ship(int n, String s) {
		this.size = n;
		this.name = s;
		switch(name) {
		case "Cacciatorpediniere":
			this.ID = 0;
		break;
		case "Sottomarino": 
			this.ID = 1;
		break;
		case "Corazzata": 
			this.ID = 2;
		break;
		case "Portaerei":
			this.ID = 3;
		break;
		}	
	}
	
	/**
	 * Ritorna la lunghezza della nave corrispondente.
	 * 
	 * @return int Intero rappresentante la lunghezza della nave.
	 */
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Ritorna l'id della nave corrispondente.
	 *  
	 * @return int Intero rappresentante l'id della nave.
	 */
	public int getId() {
		return this.ID;
	}

	/**
	 * Ritorna il nome della nave.
	 * 
	 * @return String Stringa indicante il nome della nave.
	 */
	public String getShipName(){
		return this.name;
	}
}