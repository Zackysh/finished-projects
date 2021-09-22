package enums;

public class EnumCartas {

	public enum Palos {
		OROS, COPAS, ESPADAS, BASTOS;
	}

	public enum Numeros {
		UNO, DOS, TRES, CUATRO, CINCO, SEIS, SIETE, SOTA, CABALLO, REY;
	}
	
	/**
	 * M�todo que recibe un n�mero y devuelve su palo correspondiente.
	 * 
	 * @param ordinal N�mero que hace referncia al palo (1=OROS,2=COPAS...).
	 * @return Palo deseado.
	 */
	public static Palos genPalo(int ordinal) {
		return Palos.values()[--ordinal];
	}

	/**
	 * 
	 * @param ordinal N�mero que hace referencia al numero de carta (1=UNO,8=SOTA...)
	 * @return Numero deseado.
	 */
	public static Numeros genNumero(int ordinal) {
		return Numeros.values()[--ordinal];
	}
}
