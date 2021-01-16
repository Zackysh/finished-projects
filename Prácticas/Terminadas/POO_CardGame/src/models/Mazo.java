package models;

import java.util.ArrayList;

public abstract class Mazo {
	
	protected ArrayList<Carta> lista;
	
	public Mazo() {
		this.lista = new ArrayList<Carta>();
	}
	
}
