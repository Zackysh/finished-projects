package models;

import java.util.ArrayList;
import java.util.List;

public class Mano extends Mazo {
	
	private Baraja baraja;
	/**
	 * Constructor que sigue el modelo UML.
	 * 
	 * @param baraja
	 */
	public Mano(Baraja baraja) { // No se implementa en los juegos.
		this.baraja = baraja;
	}
	
	/**
	 * Método que roba una carta de la baraja en la mesa y la añade a la mano. Solo
	 * robará carta si la baraja de la mesa no está vacía.
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
	 * Método que devolverá la carta cuyo índice coincida con el parámetro que
	 * recibe el método, borrándola en el proceso.
	 * 
	 * @param index Índice de la carta objetivo.
	 * @return carta Carta deseada. Se borra de la mano.
	 */
	public Carta jugarCarta(int index) {
		Carta temp = new Carta(1);
		temp = this.lista.get(index);
		this.lista.remove(index);
		return temp;
	}
	
	/**
	 * Método que devuelve el número de cartas que hay en la mano.
	 * 
	 * @return numero Número de cartas que quedan en la mano.
	 */
	public int getNumroCartas() {
		return this.lista.size();
	}
	
	/**
	 * Métdodo que devuelve la última carta que se ha añadido a la mano.
	 * 
	 * @return carta Última carta añadida.
	 */
	public Carta ultimaCartaInsertada() {
		return this.lista.get(this.lista.size()-1);
	}
}
