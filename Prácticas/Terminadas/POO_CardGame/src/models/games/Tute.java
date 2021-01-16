package models.games;

import java.util.Scanner;

import enums.EnumGame.Juegos;
import models.Mesa;
import models.players.CPUPlayer;
import models.players.Player;

public class Tute extends Game {
	
	private Scanner sc = new Scanner(System.in);

	public Tute(Mesa mesa) {
		super(Juegos.TUTE, mesa);
		
		// Cada juego tendrá su propia configuración
		this.config = new double[1];
		this.config[0] = 1; // Número de rondas.
//		this.config[1] = 7.5;
	}

	@Override
	public void bienvenida() {
		System.out.println(
				  "==================================\n"
				+ "|             TUTE               |\n"
				+ "==================================\n");	
	}

	@Override
	public void menuPrincipal() {
		
		// Variable de control.
		boolean salir = false;
		do {
			System.out.println("===== Menu principal =====\n"
					+ "1. Comenzar el juego.\n"
					+ "2. Configurar reglas de juego.\n"
					+ "3. Añadir/Quitar jugadores.\n"
					+ "4. Cómo se juega.\n"
					+ "5. Simular partidas (testing)"
					+ "6. Salir.");
			System.out.println("Número de cartas en la baraja: " + this.mesa.contarCartasBaraja());
			
			// Variables de control/validación.
			String strOpcion;
			boolean esValida = false;
			do {
				System.out.print("Opción: ");
				strOpcion = sc.nextLine().trim();
				
				if (strOpcion.equals("1") || strOpcion.equals("2") || strOpcion.equals("3") || strOpcion.equals("4") || strOpcion.equals("5"))
					esValida = true;
				else
					System.out.println("Opción no válida, vuelve a intentarlo.");
				
			} while (!esValida); // La opción debe ser válida.

			switch (strOpcion) {
			case "1":
				br();
				if(jugadores.isEmpty())
					System.out.println(
							  "No hay jugadores suficientes para iniciar el juego.\n"
							+ "Como mínimo debe haber 1 jugador.");
				else if(jugadores.size() < 2 || jugadores.size() > 4)
					System.out.println("El mínimo de jugadores es 2, el máximo es 4. Actualmente hay " + jugadores.size() + " jugadores.");
				else
					start(config);
				br();
				break;
			case "2":
				br();
				mostrarMenuConfiguracion();				
				br();
				break;
			case "3":
				br();
				mostrarMenuJugadores();
				br();
				break;
			case "4":
				br();
				mostrarReglas();
				br();
				break;
			case "5":
				br();
				System.out.println(
						  "Los jugadores serán CPUs, la simulación modifica la\n"
						+ "baraja para forzar las situaciones:\n"
						+ "1. Forzar empate.\n"
						+ "2. Forzar ganador con pleno.\n"
						+ "3. Forzar ganador sin pleno.\n"
						+ "4. Forzar partida sin ganador (un solo jugador)\n"
						+ "4. Atrás.");
				
				// Variables de control/validación (las reutilizo, no causa conflicto).
				strOpcion = "";
				esValida = false;
				do {
					System.out.print("Opción: ");
					strOpcion = sc.nextLine().trim();
					
					if (strOpcion.equals("1") || strOpcion.equals("2") || strOpcion.equals("3") || strOpcion.equals("4"))
						esValida = true;
					else
						System.out.println("Opción no válida, vuelve a intentarlo.");
					
				} while (!esValida); // La opción debe ser válida.
				
				switch(strOpcion) {
				case "1":
					break;
				}
				br();
				break;
			case "6":
				salir = true;
				System.out.print("\n" 
						+ "================================\n"
						+ "|     ¡Hasta la próxima!       |\n"
						+ "================================\n");
			}
		} while (!salir); // Mostrará el menú hasta que el usuario pulse "5" (salir).
		
		this.finished = true; // El juego habrá terminado.
		
	}
	
	private void mostrarReglas() {
		System.out.println("===== Reglas =====\n");
		System.out.println(
				    "La versión del juego es una simplificada, tiene en cuenta las siguientes reglas:\n"
				  + "- Se partirá una determinada cantidad de cartas según el número de jugadores.\n"
				  + "   - 2 jugadores: 8 cartas cada uno.\n"
				  + "   - 3 jugadores: 12 cartas cada uno.\n"
				  + "   - 4 jugadores: 10 cartas cada uno.\n"
				  + "- Cada carta tiene un valor, el objetivo es sumar el máximo posible:\n"
				  + "   - As: 11 puntos.\n"
				  + "   - Tres: 10 punto.s\n"
				  + "   - Rey: 4 puntos.\n"
				  + "   - Caballo: 3 puntos.\n"
				  + "   - Sota: 2 puntos.\n"
				  + "   - Resto sin valor.\n"
				  + "- La última carta que se raparta será mostrada. El palo al que pertenezca se convertirá\n"
				  + "  en el triunfo. Este palo vencerá al resto sin importar su valor.\n"
				  + "- El primer jugador en jugar pondrá sobre la mesa una carta cualquiera.\n"
				  + "- El resto de jugadores jugarán una carta que deberá ser del mismo palo.\n"
				  + "- En caso de no tener una carta del mismo palo, se deberá \"fallar\", poner obligatoriamente\n"
				  + "  un triunfo.\n"
				  + "- En caso de no tener un triunfo, se puede lanzar cualquier carta.\n"
				  + "- En caso de tener que \"fallar\" y que otro jugador haya fallado justo antes, se deberá fallar\n"
				  + "  solo en caso de tener un triunfo de mayor valor. En caso contrario n"
				  + "- "
				  + "- ");
		
	}

	private void mostrarMenuConfiguracion() {

		String strOpcion 	 = "";
		String strOpcion2 	 = "";

		// SUBMENÚ 1
		do {
			System.out.println("===== Configuración =====\n"
					+ "1. Número de rondas.\n"
					+ "2. Valor objetivo (7.5, 15, etc).\n"
					+ "3. Atrás.");
			boolean esValida = false;
			do {
				System.out.print("Opción: ");
				strOpcion = sc.nextLine().trim();
				br();
				switch (strOpcion) {
				case "1": // SUBMENÚ 1-1
					System.out.print(
					  "Barajas disponibles:\n"
					+ "1. Baraja común (40 cartas).\n"
					+ "2. Baraja doble (80 cartas).\n");
					boolean esValida2 = false;
					do {
						System.out.print("Opción: ");
						strOpcion2 = sc.nextLine().trim();

						switch (strOpcion2) {
						case "1":
							System.out.println("Has elegido la baraja común (40 cartas).");
							config[0] = 1;
							esValida2 = true;
							break;
						case "2":
							System.out.println("Has elegido la baraja doble (80 caras).");
							config[0] = 2;
							esValida2 = true;
							break;
						default:
							System.out.println("Opción no válida, vuelve a intentarlo.");
						}
					} while (!esValida2);
					esValida = true;
					br();
					break;
				case "2": // SUBMENÚ 1-2
					System.out.println(
							  "Estás a punto de establecer un nuevo valor objetivo, debe\n"
							+ "cumplir las siguientes condiciones:\n"
							+ "- Ser menor a 29.5.\n"
							+ "- Ser múltiplo de 0.5.");
					double temp = 0;
					boolean esValida3 = false;
					do {
						esValida2 = false;
						do {
							System.out.print("Opción: ");
							strOpcion2 = sc.nextLine().trim();
							try {
								temp = Double.parseDouble(strOpcion2);
								esValida2 = true;
							} catch (Exception e) {
								System.out.println("¡Debes introducir un número!");
								esValida2 = false;
							}
						} while (!esValida2);
						if (temp <= 29.5)
							if (temp % 0.5 == 0) {
								System.out.println("Has elegido el valor " + temp + " como nuevo valor a alcanzar.");
								esValida3 = true;
							} else
								System.out.println("El valor no es múltiplo de 0.5.");
						else
							System.out.println("El valor es mayor a 29.5.");
					} while (!esValida3);
					config[1] = temp;
					esValida = true;
					br();
					break;
				case "3": // Atrás
					esValida = true;
					break;
				default:
					System.out.println("Opción no válida, vuelve a intentarlo.");
				}

			} while (!esValida);
		} while (!strOpcion.equals("3")); // Hasta que la opcíon del SUBMENÚ 1 no sea "3"
		br();
		System.out.println(""
				+ "Tipo de baraja: " + config[0] + "\n"
				+ "Valor a alcanzar: " + config[1]);
	}

	private void br() {
		System.out.println();
	}

	@Override
	public void start(double[] config) {
		
		
	}

	@Override
	public Player nextTurno() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
