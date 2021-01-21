package utils;

public class StringUtils {

	/**
	 * Method that receives any String and returns it with the following "format":
	 * (charAt (0) .toUppercase-charAt (> 0): LowerCase ...). Take into account if
	 * empty before processing it will return the string itself.
	 * 
	 * @param string
	 * @return temp Processed string.
	 */
	public static String normalizarString(String chain) {
		String temp = "";
		if (chain.length() == 0)
			return chain;
		if (chain.length() == 1)
			return chain.toUpperCase();
		if (chain.length() > 1) {
			for (int i = 0; i < chain.length(); i++) {
				if (i == 0)
					temp += chain.toUpperCase().charAt(i);
				else
					temp += chain.toLowerCase().charAt(i);
			}
		}
		return temp;
	}

	/**
	 * Checks if given string can be parsed to integer.
	 * 
	 * @param str
	 * @returns true if its possible
	 * @returns false if its not possible
	 */
	public static boolean checkInt(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Checks if given string can be parsed to double.
	 * 
	 * @param str
	 * @returns true if its possible
	 * @returns false if its not possible
	 */
	public static boolean checkDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Generate a random name.
	 * 
	 * @return string with a random name
	 */
	public static String nombreAleatorio() {
		String[] nombres = { "Pipo", "Bowser", "GLaDOS", "The Joker", "Ganon", "Gary Oak", "Sephiroth", "Inky" };
		return nombres[(int) (Math.random() * (nombres.length))];
	}
}
