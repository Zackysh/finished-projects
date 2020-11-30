package models;

import java.util.ArrayList;

public class Mano extends Mazo {
	
	/**
	 * He optado por relacionar la mano con la mesa, para que pueda interactuar
	 * directamente con cualquier elemento (jugador, baraja...) en lugar de solo con
	 * la baraja.
	 * 
	 * Para que esta clase cumpliese con el diagrama original, debería relacionarse
	 * solo con la baraja. En el costructor recibiría la
	 * baraja en lugar de la mesa.
	 * 
	 * private Baraja baraja; en lugar de private Mesa mesa;
	 */	 
	private Mesa mesa;
	
	/**
	 * Constructor que sigue el modelo UML.
	 * 
	 * @param baraja
	 */
	public Mano(Baraja baraja) { // No se implementa en los juegos.
		
	}
	
	/**
	 * Costructor de mano. Recibirá la mesa en la que jugará y contendrá los
	 * elemetnos con los que interactuará cuando sea necesario.
	 * 
	 * @param mesa Mesa de juego.
	 */
	public Mano(Mesa mesa) { // Se implementa en cada juego.
		this.lista = new ArrayList<Carta>();
		this.mesa = mesa;
	}
	
	/**
	 * Método que roba una carta de la baraja en la mesa y la añade a la mano. Solo
	 * robará carta si la baraja de la mesa no está vacía.
	 */
	public void insertarCarta(Carta carta) {
		if(carta != null)
			this.lista.add(carta);
	}

	/**
	 * Método que devolverá la carta cuyo índice coincida con el parámetro que
	 * recibe el método, borrándola en el proceso.
	 * 
	 * @param index Índice de la carta objetivo.
	 * @return carta Carta deseada. Se borra de la mano.
	 */
	public Carta jugarCarta(int index) {
		Carta temp = this.lista.get(index);
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
