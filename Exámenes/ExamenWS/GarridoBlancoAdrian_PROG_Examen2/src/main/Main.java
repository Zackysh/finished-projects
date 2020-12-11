package main;

import java.util.Scanner;

import models.Tienda;

public class Main {
	
	private static Scanner sc = new Scanner(System.in);
	
	public static void br() {
		System.out.println();
	}
	
	public static void main(String[] args) {
		
		Tienda aliexNotas = new Tienda();
		
		System.out.println(
				  "==================================\n"
				+ "|    ¡BIENVENIDO A ALIEXNOTAS!    |\n"
				+ "==================================\n");
		
		boolean salir = false;
		do {

			System.out.println("------------- Inicio -------------\n"
					+ "1. Añadir instrumento al stock.\n"
					+ "2. Listar instrumento.\n"
					+ "3. Vender instrumento.\n"
					+ "4. Mostrar caja.\n"
					+ "5. Salir.");
			
			
			String strOpcion = "";
			boolean esValida = false;
			do {
				System.out.print("Opción: ");
				strOpcion = sc.nextLine().trim();
				if (strOpcion.equals("1") || strOpcion.equals("2") || strOpcion.equals("3") || strOpcion.equals("4") || strOpcion.equals("5"))
					esValida = true;
				else
					System.out.println("Opción no válida, vuelve a intentarlo.");
			} while (!esValida);

			switch (strOpcion) {
			case "1":
				br();
				aliexNotas.insertarInstrumento();
				br();
				break;
			case "2":
				br();
				aliexNotas.listarInstumentos();
				br();
				break;
			case "3":
				br();
				aliexNotas.venderInstrumento();
				br();
				break;
			case "4":
				br();
				aliexNotas.mostrarCaja();
				br();
				break;
			case "5":
				br();
				br();
				salir = true;
				System.out.print("\n" 
						+ "================================\n"
						+ "|     ¡Hasta la próxima!       |\n"
						+ "================================\n");
			}
		} while (!salir);
	}
}
