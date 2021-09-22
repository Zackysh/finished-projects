package main;

import java.util.Scanner;

import enums.EnumGame.Juegos;
import models.Baraja;
import models.Mesa;
import models.games.Game;
import models.games.SieteYMedio;
import utils.StringUtils;
import utils.Validator;

public class Mainapp {
	
	private static Scanner sc = new Scanner(System.in);
	
	private static void br() {
		System.out.println();
	}
	
	public static void main(String[] args) {	

		Baraja barajaDeJuego = new Baraja(1);
		Mesa mesaDeJuego = new Mesa(barajaDeJuego);
		Juegos juegoSeleccionado = Juegos.SIETEYMEDIO;
		Game game = new SieteYMedio(mesaDeJuego);
		
		boolean salir = false;
		do {
			System.out.println(
					  "==================================\n"
					+ "|    �BIENVENIDO A CardTeam!    |\n"
					+ "==================================\n");
			System.out.println("------------- Inicio -------------\n"
					+ "Juego seleccionado: " + juegoSeleccionado.toString() + "\n"
					+ "1. �Jugar!.\n"
					+ "2. Seleccionar CardJuegos.\n"
					+ "3. Rebajas de navidad.\n"
					+ "4. Qu� es CardTeam.\n"
					+ "5. Salir.");
			
			
			String strOpcion = "";
			boolean esValida = false;
			do {
				System.out.print("Opci�n: ");
				strOpcion = sc.nextLine().trim();
				if (strOpcion.equals("1") || strOpcion.equals("2") || strOpcion.equals("3") || strOpcion.equals("4") || strOpcion.equals("5"))
					esValida = true;
				else
					System.out.println("Opci�n no v�lida, vuelve a intentarlo.");
			} while (!esValida);

			switch (strOpcion) {
			case "1":
				
				if(juegoSeleccionado == Juegos.SIETEYMEDIO)
					game = new SieteYMedio(mesaDeJuego);
				if(juegoSeleccionado == Juegos.TUTE || juegoSeleccionado == Juegos.MUS)
					System.out.println("Solo est� implementado SieteYMedio.");
				
				if(game != null)
					game.launch();
				else
					System.out.println("Muy mal :/\n");
				
				break;
			case "2":
				br();
				juegoSeleccionado = mostrarBiblioteca(juegoSeleccionado);
				br();
				break;
			case "3":
				br();
				try {
					mostrarRebajas();
				} catch (Exception e) {
					e.printStackTrace();
				}
				br();
				break;
			case "4":
				br();
				mostrarInfo();
				br();
				break;
			case "5":
				salir = true;
				System.out.print("\n" 
						+ "================================\n"
						+ "|     �Hasta la pr�xima!       |\n"
						+ "================================\n");
			}
		} while (!salir);
		
	}

	private static void mostrarInfo() {
		System.out.println(
				  "==================================\n"
				+ "|      ACERCA DE ESTA APP        |\n"
				+ "==================================\n");	
		System.out.println(
					  "Agradezco su inter�s acerca de esta marivillosa aplicaci�n.\n"
					+ "Sin embargo no me pagan lo suficiente, as� que eso es todo. <3"
				);		
	}

	private static void mostrarRebajas() throws Exception {
		System.out.println(
				  "==================================\n"
				+ "|        POLLO PARA TODOS        |\n"
				+ "==================================\n");
		boolean salir = false;
		do {
			String strOpcion = "";
			System.out.println("------------- Inicio -------------\n"
					+ "1. Pollo frito: 4�\n"
					+ "2. Pollo asado: 12�\n"
					+ "3. Pollo: 0�\n"
					+ "4. Atr�s. (recomendado)");
			boolean esValida = false;
			do {
				System.out.print("Opci�n: ");
				strOpcion = sc.nextLine().trim();
				if (strOpcion.equals("1") || strOpcion.equals("2") || strOpcion.equals("3") || strOpcion.equals("4"))
					esValida = true;
				else
					System.out.println("Opci�n no v�lida, vuelve a intentarlo.");
			} while (!esValida);

			switch (strOpcion) {
			case "1", "2", "3":
				System.out.println("Enviando debajo de tu almohada...\n" + "A puntito: escribe algo:");
				sc.nextLine();
				throw new Exception("Te han timado t�o <3");
			case "4":
				salir = true;
			}
		} while (!salir);
		
	}

	private static Juegos mostrarBiblioteca(Juegos juegoActual) {
		
		Juegos modoElegido = juegoActual; // En caso de no seleccionar ning�n juego, devolver� el ya seleccionado.
		
		System.out.println(
				  "==================================\n"
				+ "|           BIBLIOTECA           |\n"
				+ "==================================\n");
		
		boolean salirBiblioteca = false;
		do {
			String strOpcion = "";
			int opcion = -1;
			
			System.out.println("CardJuegos disponibles:\n");
			// Genera una opci�n por cada modo de juego en ModoJuego
			for (Juegos modojuego : Juegos.values()) {
				System.out.println(modojuego.ordinal()+1 + ". " + modojuego.toString().charAt(0) + modojuego.toString().substring(1).toLowerCase() + ".");
			}
			// Genera una �ltima opci�n para salir sin seleccionar un juego.
			System.out.println(Juegos.values().length+1 + ". Atr�s.");
			
			// Selecci�n de juego
			boolean esOpcion = false;
			do {
				boolean esInt = false;
				do {
					System.out.print("Opci�n: ");
					strOpcion = sc.nextLine().trim();
					esInt = Validator.validateStrInt(strOpcion);
					if(!esInt) System.out.println("Debes introducir un n�mero.");
				} while (!esInt);
				
				opcion = Integer.parseInt(strOpcion); // Opci�n v�lida, ahora toca decidir.
				
				// Si la opci�n est� entre 1 y 4, proceder�, sino se repetir� el proceso.
				if (opcion >= 1 && opcion <= Juegos.values().length + 1) {
					esOpcion = true;
					// Si la opci�n es 4, ("Atr�s") saldr� de la biblioteca.
					if (opcion == Juegos.values().length + 1)
						salirBiblioteca = true;
					// Si la opci�n no es 4, deber� ser 1, 2 o 3, por lo que seleccionar� dicho juego.
					else {
						modoElegido = Juegos.values()[opcion - 1];
						System.out.println(
									  "Juego seleccionado: " + StringUtils.normalizarString(modoElegido.toString()) + "\n"
									+ "Si ahora quiere volver al men� principal, presiona 4.");
					}
						
				} else
					System.out.println("Opci�n no v�lida, vuelve a intentarlo.");
			} while(!esOpcion);
			
			
			br();
			
		} while (!salirBiblioteca);
		
		return modoElegido;
	}
}
