package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baraja extends Mazo {
	
	/**
	 * Constructor de la baraja. Genera una baraja vacía.
	 * Puede contener varias cartas.
	 */
	public Baraja() {
		
	};
	
	/**
	 * Constructor de la baraja.
	 * Contendrá una baraja española entera o doble.
	 * Genera un tipo de baraja según el entero que reciba, si recibe un entero
	 * inesperado creará una baraja de la opción válida más cercana:
	 *  - 1: Baraja normal de 40 cartas.
	 *  - 2: Baraja doble de 80 cartas.
	 *  
	 * @param tipoBaraja Entero que definirá el tipo de baraja.
	 */
	public Baraja(int tipoBaraja) {
		if (tipoBaraja <= 1) {
			lista = new ArrayList<Carta>(40);
			for (int i = 1; i <= 40; i++) {
				lista.add(new Carta(i));
			}
		} else if (tipoBaraja >= 2) {
			lista = new ArrayList<Carta>(80);
			int cont = 1;
			for (int i = 1; i <= 80; i++) {
				lista.add(new Carta(cont++));
				if (cont > 40) cont = 1;
			}
		}
	}
	
	/**
	 * Método que reordenará de forma aleatoria el contenido de la lista de cartas.
	 */
	public void barajar() {
		Collections.shuffle(this.lista);
	}
	
	/**
	 * Método que recibirá un entero, este señalará una posición en la baraja y la
	 * cortará por ese punto.
	 * Si la posición indicada no es válida, cortará la baraja por la mitad.
	 * 
	 * @param interseccion Punto por el que cortar.
	 */
	public void cortar(int interseccion) {
		if(interseccion < 0 || interseccion > this.lista.size() - 1)
			interseccion = (int) ((this.lista.size() / 2));
		
		List<Carta> mitadUno = this.lista.subList(0, interseccion);
		List<Carta> mitadDos = this.lista.subList(interseccion, this.lista.size());
		
		ArrayList<Carta> mitadUno2 = new ArrayList<Carta>();
		mitadUno2.addAll(mitadUno);
		ArrayList<Carta> mitadDos2 = new ArrayList<Carta>();
		mitadDos2.addAll(mitadDos);
		
		this.lista.clear();
		
		for (Carta carta : mitadDos2) {
			this.lista.add(carta);
		}
		
		for (Carta carta : mitadUno2) {
			this.lista.add(carta);
		}
	}
	
	/**
	 * Método que extraerá
	 * 
	 * @return
	 */
	public Carta robar() {
		Carta temp = this.lista.get(this.lista.size()-1);
		this.lista.remove(this.lista.size()-1);
		return temp;
	}
	
	// Métodos para INSERTAR CARTAS
	
	/**
	 * Método que inserta una carta al final de la baraja (arriba).
	 * 
	 * @param id_carta Recibe la carta en forma de id.
	 */
	public void insertarCartaArriba(int id_carta) {
		lista.add(new Carta(id_carta));
	}
	
	/**
	 * Método que inserta una carta al final de la baraja (arriba).
	 * 
	 * @param id_carta Recibe la carta en forma de objeto.
	 */
	public void insertarCartaArriba(Carta carta) {
		lista.add(carta);
	}
	
	/**
	 * Método que inserta una carta al principio de la baraja (abajo).
	 * 
	 * @param id_carta Recibe la carta en forma de id.
	 */
	public void insertarCartaAbajo(int id_carta) {
		ArrayList<Carta> copia = new ArrayList<Carta>(this.lista);
		this.lista.clear();
		this.lista.add(new Carta(id_carta));
		for (Carta carta : copia) {
			this.lista.add(carta);
		}
	}
	
	/**
	 * Método que inserta una carta al principio de la baraja (abajo).
	 * 
	 * @param id_carta Recibe la carta en forma de objeto.
	 */
	public void insertarCartaAbajo(Carta carta) {
		ArrayList<Carta> copia = new ArrayList<Carta>(this.lista);
		this.lista.clear();
		this.lista.add(carta);
		for (Carta carta2 : copia) {
			this.lista.add(carta2);
		}
	}
	
	/**
	 * Metodo que devuelve el número de cartas que contiene la baraja.
	 * 
	 * @return number Número de cartas que contiene la baraja.
	 */
	public int getNumeroCartas() {
		return this.lista.size();
	}
	
	/**
	 * Método que comprueba si la baraja está vacía.
	 * 
	 * @returns true Si la baraja está vacía.
	 * @returns false Si la baraja no está vacía.
	 */
	public boolean isVacia() {
		if(this.lista.isEmpty()) return true;
		else return false;
	}
	
	@Override
	public String toString() {
		String strLista = "";	
		for (Carta carta : this.lista) {
			strLista += carta + "\n";
		}
		return "Baraja: \n" + strLista;
	}
}
