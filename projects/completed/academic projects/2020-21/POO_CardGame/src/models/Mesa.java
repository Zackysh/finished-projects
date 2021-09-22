package models;

import java.util.ArrayList;
import java.util.List;

public class Mesa {
	
	private Baraja baraja;

	/**
	 * Constructor de la mesa.
	 * Contendr� una baraja.
	 * 
	 * @param baraja Contendr� una baraja.
	 */
	public Mesa(Baraja baraja) {
		super();
		this.baraja = baraja;
	}
	
	public int contarCartasBaraja() {
		return this.baraja.getNumeroCartas();
	}
	
	/**
	 * M�todo que llama al m�todo barajar de la baraja. No ser�a correcto declarar
	 * la lista de cartas como p�blica, ya que la mesa tendr�a cartas y una baraja
	 * en lugar de una baraja. Se romper�a la estructura.
	 */
	public void barajarBaraja() {
		baraja.barajar();
	}
	
	/**
	 * Si la baraja no est� vac�a, robar� una carta y la devolver�.
	 * 
	 * @return carta Robada desde el m�todo robar de la baraja.
	 */
	public Carta robarCarta() {
		if(!baraja.isVacia())
		return baraja.robar();
		return null;
	}
	
	/**
	 * Si la baraja no est� vac�a robar� el n�mero de cartas que es recibido como
	 * par�metro. correspondiente y las devolver� en una lista.
	 * 
	 * @param numeroCartas El n�mero de cartas a robar. Puede ser 1.
	 * @returns lista Si la baraja no est� vac�a. Lista de cartas robadas.
	 * @returns null Si la baraja est� vac�a.
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
	 * M�todo que a�ade una carta a la baraja. Se insertar� abajo, para no ser
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
