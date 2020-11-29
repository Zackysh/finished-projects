package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baraja extends Mazo {
	
	/**
	 * Constructor que genera una baraja vacía.
	 */
	public Baraja() {
		lista = new ArrayList<Carta>();
	};
	
	/**
	 * Constructor Genera un tipo de baraja según el entero que reciba, si recibe un entero
	 * inesperado creará una baraja de la opción válida más cercana:
	 *  - 1: Baraja normal de 40 cartas.
	 *  - 2: Baraja doble de 80 cartas.	 * 
	 * @param tipoBaraja
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
	
	// Modificar orden de la baraja
	public void barajar() {
		Collections.shuffle(this.lista);
	}
	
	public void cortar(int interseccion) {
		List<Carta> mitadUno = this.lista.subList(0, interseccion);
		List<Carta> mitadDos = this.lista.subList(interseccion, this.lista.size());
		System.out.println(mitadDos);
		System.out.println();
		System.out.println(mitadUno);
	}
	
	// Extraer
	public Carta robar() {
		Carta temp = this.lista.get(1);
		this.lista.remove(1);
		return temp;
	}
	
	// Insertar
	public void insertarCartarFinal(int id_carta) {
		lista.add(new Carta(id_carta));
	}
	
	public void insertarCartaFinal(Carta carta) {
		lista.add(carta);
	}
	
	public void insertarCartaPrincipio(int id_carta) {
		ArrayList<Carta> copia = new ArrayList<Carta>(this.lista);
		this.lista.clear();
		this.lista.add(new Carta(id_carta));
		for (Carta carta : copia) {
			this.lista.add(carta);
		}
	}
	
	public void insertarCartaPrincipio(Carta carta) {
		ArrayList<Carta> copia = new ArrayList<Carta>(this.lista);
		this.lista.clear();
		this.lista.add(carta);
		for (Carta carta2 : copia) {
			this.lista.add(carta2);
		}
	}
	
	public int getNumeroCartas() {return this.lista.size();}
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
