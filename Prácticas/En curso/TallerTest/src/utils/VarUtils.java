package utils;

import java.util.Scanner;

public class VarUtils {
	private static Scanner sc = new Scanner (System.in);
	
	/**
	 * Esta función lo que hace es mostrar un nombre o título que sera un String y pedirá al usuario que introduzca 
	 * por teclado un int o número entero.
	 * @param titulo
	 * @return
	 */
	
	public static int pedirInt(String titulo) {
		String aux = "";
		boolean esDouble = false;
		int num = 0;
		do {
			System.out.print(titulo + ": ");
			aux = sc.nextLine();
			try {
				num = Integer.parseInt(aux);
				esDouble = true;
			} catch (Exception e) {
				System.out.println("Debes introducir un valor numérico.");
			}
		} while (!esDouble);
        // Del bucle solo se puede salir introduciendo un número entero
        // El try catch captura el error de que no se puede introducir letras ,
		// y mostrará un mensaje diciendo, introduce un valor numérico.
		return num;
	}
	/**
	 * Esta función lo que hace es mostrar un titulo o nombre introducido por el programador y le pedira al usuario
	 * que introduzca por teclado un String.
	 * @param titulo
	 * @return
	 */
	public static String pedirString(String titulo) {
		String aux = "";
		do {
			System.out.print(titulo + ": ");
			aux = sc.nextLine();
			if(aux.isBlank())
				System.out.println("No has introducido nada, vuelve a intentarlo.");
		} while (aux.isBlank());
        // Si está vacío  no saldrás del bucle nunca.
		return aux;
	}
	/**
	 * Esta función lo que hace es mostrar un titulo o nombre que es un String que introduce enprogramador con anterioridad
	 * y le pide al usuario que introduzca por teclado un numero que sea double o int.
	 * @param titulo
	 * @return
	 */
	public static Double pedirDouble(String titulo) {
		String aux = "";
		boolean esDouble = false;
		double num = 0;
		do {
			System.out.print(titulo + ": ");
			aux = sc.nextLine();
			try {
				num = Double.parseDouble(aux);
				esDouble = true;
			} catch (Exception e) {
				System.out.println("Debes introducir un valor numérico.");
			}
		} while (!esDouble);
       //El try catch captura el error de que no se puede introducir una letra para que el programa no se rompe
	   //y en cambio te muestra un mensaje de que no es un numero.
	   //Si el dato introducido no es un valor numérico se repetira.
		return num;
	}

}
