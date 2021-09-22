package models;

import java.util.ArrayList;
import java.util.List;

public class Mano extends Mazo {
	

	/**
	 * Constructor de la clase. No recibe nada.
	 * 
	 * @param baraja
	 */
	public Mano() {		
	}
	
	/**
	 * M�todo que roba una carta de la baraja en la mesa y la a�ade a la mano. Solo
	 * robar� carta si la baraja de la mesa no est� vac�a.
	 */
	public void insertarCarta(Carta carta) {
		if(carta != null)
			this.lista.add(carta);
	}
	
	public ArrayList<Carta> devolverPrimeraCarta() {
		List<Carta> cartas = this.lista;
		ArrayList<Carta> cartas2 = new ArrayList<Carta>();
		cartas2.addAll(cartas);
		this.lista.clear();
		return cartas2;
	}

	/**
	 * M�todo que devolver� la carta cuyo �ndice coincida con el par�metro que
	 * recibe el m�todo, borr�ndola en el proceso.
	 * 
	 * @param index �ndice de la carta objetivo.
	 * @return carta Carta deseada. Se borra de la mano.
	 */
	public Carta jugarCarta(int index) {
		Carta temp = new Carta(1);
		temp = this.lista.get(index);
		this.lista.remove(index);
		return temp;
	}
	
	/**
	 * M�todo que devuelve el n�mero de cartas que hay en la mano.
	 * 
	 * @return numero N�mero de cartas que quedan en la mano.
	 */
	public int getNumroCartas() {
		return this.lista.size();
	}
	
	/**
	 * M�tdodo que devuelve la �ltima carta que se ha a�adido a la mano.
	 * 
	 * @return carta �ltima carta a�adida.
	 */
	public Carta ultimaCartaInsertada() {
		return this.lista.get(this.lista.size()-1);
	}
}
