package utils;

public class StringUtils {

	/**
	 * Método que recibe un String cualquiera y lo devuelve con el siguiente
	 * "formato": (charAt(0).toUppercase-charAt(>0):LowerCase...). Tiene en cuenta
	 * si está vacío antes de procesar, devolverá el propio string.
	 * 
	 * No he encontrado ninguna librería que lo haga por mi, estoy triste.
	 * 
	 * @param cadena
	 * @return temp String procesado.
	 */
	public static String normalizarString(String cadena) {
		String temp = "";
		if (cadena.length() == 0)
			return cadena;
		if (cadena.length() == 1)
			return cadena.toUpperCase();
		if (cadena.length() > 1) {
			for (int i = 0; i < cadena.length(); i++) {
				if (i == 0)
					temp += cadena.toUpperCase().charAt(i);
				else
					temp += cadena.toLowerCase().charAt(i);
			}
		}
		return temp;
	}
	
	public static void br() {
		System.out.println();
	}

	public static String nombreAleatorio() {
		String[] nombres = { "Pipo", "Bowser", "GLaDOS", "The Joker", "Ganon", "Gary Oak", "Sephiroth", "Inky" };
		return nombres[(int) (Math.random() * (nombres.length))];
	}
}
