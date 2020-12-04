package models;

import java.util.ArrayList;
import java.util.List;

public class Mesa {
	
	private Baraja baraja;

	/**
	 * Constructor de la mesa.
	 * Contendrá una baraja.
	 * 
	 * @param baraja Contendrá una baraja.
	 */
	public Mesa(Baraja baraja) {
		super();
		this.baraja = baraja;
	}
	
	public int contarCartasBaraja() {
		return this.baraja.getNumeroCartas();
	}
	
	/**
	 * Método que llama al método barajar de la baraja. No sería correcto declarar
	 * la lista de cartas como pública, ya que la mesa tendría cartas y una baraja
	 * en lugar de una baraja. Se rompería la estructura.
	 */
	public void barajarBaraja() {
		baraja.barajar();
	}
	
	/**
	 * Si la baraja no está vacía, robará una carta y la devolverá.
	 * 
	 * @return carta Robada desde el método robar de la baraja.
	 */
	public Carta robarCarta() {
		if(!baraja.isVacia())
		return baraja.robar();
		return null;
	}
	
	/**
	 * Si la baraja no está vacía robará el número de cartas que es recibido como
	 * parámetro. correspondiente y las devolverá en una lista.
	 * 
	 * @param numeroCartas El número de cartas a robar. Puede ser 1.
	 * @returns lista Si la baraja no está vacía. Lista de cartas robadas.
	 * @returns null Si la baraja está vacía.
	 */
	public List<Carta> robarVariasCartas(int numeroCartas) {
		if (!baraja.isVacia()) {
			ArrayList<Carta> lista = new ArrayList<Carta>();
			for (int i = 0; i < numeroCartas; i++) {
				lista.add(baraja.robar());
			}
			return lista;
		} else
			return null;
	}
	
	public void insertarVariasCartas(ArrayList<Carta> cartas) {
		this.baraja.lista.addAll(cartas);
	}

	/**
	 * Método que añade una carta a la baraja. Se insertará abajo, para no ser
	 * robada de nuevo al siguiente turno.
	 * 
	 * @param carta La carta a devolver a la baraja.
	 */
	public void addCartaBaraja(Carta carta) {
		baraja.insertarCartaAbajo(carta);
	}
	
	public Baraja getBaraja() {
		return this.baraja;
	}
}
