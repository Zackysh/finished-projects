package models;

import enums.EnumCartas;
import enums.EnumCartas.Numeros;
import enums.EnumCartas.Palos;

public class Carta {

	// ATRIBUTOS
	private Palos 	palo;
	private Numeros numero;
	private int 	id;

	// CONSTRUCTORES
	/**
	 * Constructor que recibe dos tipo Enum, Palos y Numeros. Estos enumerados
	 * contienen las opciones válidas.
	 * 
	 * ¿Por qué he decidido usar Enums? Ya que es necesario que las cartas tengan un
	 * valor numérico (para poder trabajar con IDs) y alfanumérico. Con los Enums no
	 * es necesario relacionar los valores numéricos y alfanuméricos. Se define el
	 * valor alfanumérico (cada identificador: UNO, DOS, REY, OROS...) y a estos se
	 * les asigna automáticamente su valor numérico (su ordinal dentro del Enum).
	 * 
	 * Sé que la aplicación es pequeña y no necesita ser mantenible, pero sino
	 * empiezo ahora a preocuparme por estas cosas siento que no aplico lo que voy
	 * aprendiendo.
	 * 
	 * @param palo   Palo al que pertenecerá la carta.
	 * @param numero Número que tendrá la carta.
	 */
	public Carta(Palos palo, Numeros numero) {
		this.palo 	= palo;
		this.numero = numero;
		this.id 	= (palo.ordinal() * 10) + (numero.ordinal() + 1);
	}

	/**
	 * Dado un id genera la carta correspondiente.
	 * En caso de que el id no sea válido, se generará un tipo de carta por defecto.
	 * 
	 * Casos:
	 * 	id < 1: uno de oros.
	 * 	id > 40 rey de bastos.
	 * 
	 * @param id ID de la carta a crear.
	 */
	public Carta(int id) {
		if (id < 1) { // ID NO VALIDO < 1
			this.palo 	= Palos.OROS;
			this.numero = Numeros.UNO;
			this.id 	= id;
			System.out.println(
					"********************************************************************************************\n"
							+ "Error al generar nueva carta dado un identificador.                                        |\n"
							+ "El id no coincide con ninguna carta. Solo son válidos valores entre 1 y 40 incluídos.      |\n"
							+ "Se generó en su lugar una carta con id 40.                                                 |\n"
							+ this.toString() + "\n"
							+ "********************************************************************************************");
		} else if (id <= 40) { // ID VALIDO
			if (id % 10 == 0) {
				palo = EnumCartas.genPalo(id / 10);
				numero = Numeros.REY;
				this.id = id;
			} else {
				palo = EnumCartas.genPalo(id / 10 + 1);
				numero = EnumCartas.genNumero(id % 10);
				this.id = id;
			}
		} else { // ID NO VALIDO > 40
			this.palo = Palos.BASTOS;
			this.numero = Numeros.REY;
			this.id = id;
			System.out.println(
					"********************************************************************************************\n"
							+ "Error al generar nueva carta dado un identificador.                                        |\n"
							+ "El id no coincide con ninguna carta. Solo son válidos valores entre 1 y 40 incluídos.      |\n"
							+ "Se generó en su lugar una carta con id 40.                                                 |\n"
							+ this.toString() + "\n"
							+ "********************************************************************************************");
		}
	}

	// GETTERS
	public Palos getPalo() {
		return palo;
	}

	public Numeros getNumero() {
		return numero;
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		String nombre = "[" + numero + " de " + palo + "]";
		return nombre;
	}

	// GET VALOR EN JUEGOS
	// TUTE
	public int getValorTute() {
		int valor;
		switch (this.numero) {
		case SOTA:
			valor = 2;
			break;
		case CABALLO:
			valor = 3;
			break;
		case REY:
			valor = 4;
			break;
		case TRES:
			valor = 10;
			break;
		case UNO:
			valor = 11;
			break;
		default:
			valor = 0;
			break;
		}
		return valor;
	}
	// MUS
	public int getValorMus() {
		int valor;
		switch (this.numero) {
		case TRES, SOTA, CABALLO, REY:
			valor = 10;
			break;
		case UNO, DOS:
			valor = 1;
			break;
		default:
			valor = this.numero.ordinal() + 1;
			break;
		}
		return valor;
	}
	// 7yMedia
	public double getValor7yMedia() {
		double valor;
		switch (this.numero) {
		case SOTA, CABALLO, REY:
			valor = 0.5;
			break;
		default:
			valor = this.numero.ordinal() + 1;
			break;
		}
		return valor;
	}

	@Override
	public String toString() {
		return getNombre();
	}
}
