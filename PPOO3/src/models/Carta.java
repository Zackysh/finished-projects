package models;

public class Carta {

	// ATRIBUTOS
	private int palo;
	private int numero;
	private int id;

	// CONSTRUCTORES
	public Carta(int palo, int numero) {
		this.palo = palo;
		this.numero = numero;
		this.id = palo * numero;
	}

	public Carta(int id) {
		if (id % 10 == 0) {
			palo = id / 10;
			numero = 10;
		} else {
			palo = id / 10 + 1;
			numero = id % 10;
		}
	}

	// VALROR EN JUEGOS
	public int getValorTute(Carta carta) {
		int valor = 0;
		if (carta.numero == 1)
			valor = 11;
		else if (carta.numero == 3)
			valor = 10;

		return valor;
	}

	// GETTERS
	public int getPalo() {
		return palo;
	}

	public String getNombrePalo() {
		if (this.palo == 1)
			return "oros";
		else if (this.palo == 2)
			return "copas";
		return "etc";
	}

	public int getNumero() {
		return numero;
	}

	public int getId() {
		return id;
	}

}
