package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baraja extends Mazo {
	
	/**
	 * Constructor de la baraja. Genera una baraja vac�a.
	 * Puede contener varias cartas.
	 */
	public Baraja() {
		
	};
	
	/**
	 * Constructor de la baraja.
	 * Contendr� una baraja espa�ola entera o doble.
	 * Genera un tipo de baraja seg�n el entero que reciba, si recibe un entero
	 * inesperado crear� una baraja de la opci�n v�lida m�s cercana:
	 *  - 1: Baraja normal de 40 cartas.
	 *  - 2: Baraja doble de 80 cartas.
	 *  
	 * @param tipoBaraja Entero que definir� el tipo de baraja.
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
	 * M�todo que reordenar� de forma aleatoria el contenido de la lista de cartas.
	 */
	public void barajar() {
		Collections.shuffle(this.lista);
	}
	
	/**
	 * M�todo que recibir� un entero, este se�alar� una posici�n en la baraja y la
	 * cortar� por ese punto.
	 * Si la posici�n indicada no es v�lida, cortar� la baraja por la mitad.
	 * 
	 * @param interseccion Punto por el que cortar.
	 */
	public void cortar(int interseccion) {
		if(interseccion < 0 || interseccion > this.lista.size() - 1)
			interseccion = (int) ((this.lista.size() / 2));
		
		List<Carta> mitadUno = this.lista.subList(0, interseccion);
		List<Carta> mitadDos = this.lista.subList(interseccion, this.lista.size());
		
		ArrayList<Carta> barajaCortada = new ArrayList<Carta>();
		barajaCortada.addAll(mitadDos);
		barajaCortada.addAll(mitadUno);
		
		this.lista.clear();
		
		this.lista.addAll(barajaCortada);
	}
	
	/**
	 * M�todo que extraer�
	 * 
	 * @return
	 */
	public Carta robar() {
		Carta temp = this.lista.get(this.lista.size()-1);
		this.lista.remove(this.lista.size()-1);
		return temp;
	}
	
	// M�todos para INSERTAR CARTAS
	
	/**
	 * M�todo que inserta una carta al final de la baraja (arriba).
	 * 
	 * @param id_carta Recibe la carta en forma de id.
	 */
	public void insertarCartaArriba(int id_carta) {
		lista.add(new Carta(id_carta));
	}
	
	/**
	 * M�todo que inserta una carta al final de la baraja (arriba).
	 * 
	 * @param id_carta Recibe la carta en forma de objeto.
	 */
	public void insertarCartaArriba(Carta carta) {
		lista.add(carta);
	}
	
	/**
	 * M�todo que inserta una carta al principio de la baraja (abajo).
	 * 
	 * @param id_carta Recibe la carta en forma de id.
	 */
	public void insertarCartaAbajo(int id_carta) {
		this.lista.add(0, new Carta(id_carta));
	}
	
	/**
	 * M�todo que inserta una carta al principio de la baraja (abajo).
	 * 
	 * @param id_carta Recibe la carta en forma de objeto.
	 */
	public void insertarCartaAbajo(Carta carta) {
		this.lista.add(0, carta);
	}
	
	/**
	 * Metodo que devuelve el n�mero de cartas que contiene la baraja.
	 * 
	 * @return number N�mero de cartas que contiene la baraja.
	 */
	public int getNumeroCartas() {
		return this.lista.size();
	}
	
	/**
	 * M�todo que comprueba si la baraja est� vac�a.
	 * 
	 * @returns true Si la baraja est� vac�a.
	 * @returns false Si la baraja no est� vac�a.
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
