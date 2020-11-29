package models;

import java.util.ArrayList;

public class Mano extends Mazo {
	
	private Mesa mesa;
	
	public Mano(Mesa mesa) {
		this.lista = new ArrayList<Carta>();
		this.mesa = mesa;
	}
	
	/**
	 * Solo robará carta si la baraja de la mesa no está vacía.
	 */
	public void robar() {
		Carta temp = this.mesa.robarCarta();
		if(temp != null)
		this.lista.add(temp);
	}
	
	public Carta jugarCarta(int index) {
		Carta temp = this.lista.get(index);
		this.lista.remove(index);
		return temp;
	}
	
	public int getNumroCartas() {
		return this.lista.size();
	}
	
	public Carta ultimaCartaInsertada() {
		return this.lista.get(this.lista.size()-1);
	}
}
