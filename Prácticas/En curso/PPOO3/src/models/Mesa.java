package models;

import java.util.ArrayList;
import java.util.List;

public class Mesa {
	
	public Baraja baraja;

	public Mesa(Baraja baraja) {
		super();
		this.baraja = baraja;
	}
	
	public Carta robarCarta() {
		if(!baraja.isVacia())
		return baraja.robar();
		return null;
	}
	
	public List<Carta> robarVariasCartas(int n) {
		ArrayList<Carta> lista = new ArrayList<Carta>();
		for (int i = 0; i < n; i++) {
			lista.add(baraja.robar());
		}
		return lista;
	}
	
	public void addCartaBaraja(Carta carta) {
		baraja.insertarCartaPrincipio(carta);
	}
}
