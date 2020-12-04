package models.games;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import enums.EnumGame.Juegos;
import models.Baraja;
import models.Carta;
import models.Mesa;
import models.players.CPUPlayer;
import models.players.HumanPlayer;
import models.players.Player;
import utils.StringUtils;
import utils.Validator;

public class SieteYMedio extends Game {	
	
	private static Scanner sc = new Scanner(System.in);
	
	/**
	 * Parámetros de configuración de SieteYMedio. La configuración está implicita
	 * en el propio juego. Sin embargo, para facilitar la lectura se extraen sus
	 * valores en estas variables en el constructor.
	 */
	
	/**
	 * Constructor.
	 */
	public SieteYMedio(Mesa mesa) {
		super(Juegos.SIETEYMEDIO, mesa);
		
		// Cada juego tendrá su propia configuración
		this.config = new double[2];
		this.config[0] = 1; // Tipo de baraja
		this.config[1] = 7.5; // Valor a alcanzar
	}
	
	// LÓGICA DEL JUEGO
	@Override
	public void bienvenida() {
		System.out.println(
				  "==================================\n"
				+ "|          7 y MEDIO             |\n"
				+ "==================================\n");		
	}

	/**
	 * Método que muestra el <strong>menú principal</strong>.<br>
	 * Este método controlará el flujo del juego, desde que se inicia el menú hasta
	 * que el jugador presione salir.
	 */
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
					+ "5. Simular partidas (testing)\n"
					+ "6. Salir.");
			System.out.println("Número de cartas en la baraja: " + this.mesa.contarCartasBaraja());
			
			// Variables de control/validación.
			String strOpcion;
			boolean esValida = false;
			do {
				System.out.print("Opción: ");
				strOpcion = sc.nextLine().trim();
				
				if (strOpcion.equals("1") || strOpcion.equals("2") || strOpcion.equals("3") || strOpcion.equals("4")
				 || strOpcion.equals("5") || strOpcion.equals("6"))
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
						+ "5. Atrás.");
				
				// Variables de control/validación (las reutilizo, no causa conflicto).
				strOpcion = "";
				esValida = false;
				do {
					System.out.print("Opción: ");
					strOpcion = sc.nextLine().trim();
					
					if (strOpcion.equals("1") || strOpcion.equals("2") || strOpcion.equals("3") || strOpcion.equals("4")
					 || strOpcion.equals("5"))
						esValida = true;
					else
						System.out.println("Opción no válida, vuelve a intentarlo.");
					
				} while (!esValida); // La opción debe ser válida.
				
				switch(strOpcion) {
				case "1":
					br();
					System.out.println(
							  "Todos los jugadores existentes han sido borrados para lograr una simulación exitosa.\n"
							 + "También se ha restablecido la configuración (1, 7.5)\n\n"
							 + "Se añadirán dos jugadores y se establecerá para cada uno de ellos\n"
							 + "una puntuación que provocará empate.");
					
					this.jugadores.clear();
					
					Player simu1 = new CPUPlayer("CPU 1", this.mesa);
					Player simu2 = new CPUPlayer("CPU 2", this.mesa);

					simu1.setPuntos(7.5);
					simu2.setPuntos(7.5);
					
					jugadores.add(simu1);
					jugadores.add(simu2);

					this.start(config);
					br();
					break;
				case "2":
					System.out.println(
							"Todos los jugadores existentes han sido borrados para lograr una simulación exitosa.\n"
									+ "También se ha restablecido la configuración (1, 7.5)\n\n"
									+ "Se añadirán dos jugadores y se modificará su puntuación para que uno de los jugadores\n"
									+ "sea eliminado y el otro se plante a tiempo (con pleno).");

					this.jugadores.clear();

					simu1 = new CPUPlayer("CPU 1", this.mesa);
					simu2 = new CPUPlayer("CPU 2", this.mesa);

					simu1.setPuntos(8.5);
					simu1.eliminar();
					simu2.setPuntos(7.5);

					jugadores.add(simu1);
					jugadores.add(simu2);

					this.start(config);
					break;
				case "3":
					br();
					System.out.println(
							  "Todos los jugadores existentes han sido borrados para lograr una simulación exitosa.\n"
							 + "También se ha restablecido la configuración (1, 7.5)\n\n"
							 + "Se añadirán dos jugadores y se modificará su puntuación para que uno de los jugadores\n"
							 + "sea eliminado y el otro se plante a tiempo (sin pleno).");
					
					this.jugadores.clear();
					
					simu1 = new CPUPlayer("CPU 1", this.mesa);
					simu2 = new CPUPlayer("CPU 2", this.mesa);

					simu1.setPuntos(8.5);
					simu1.eliminar();
					simu2.setPuntos(6.5);
					
					jugadores.add(simu1);
					jugadores.add(simu2);

					this.start(config);
					br();
					break;
				case "4":
					br();
					System.out.println(
							  "Todos los jugadores existentes han sido borrados para lograr una simulación exitosa.\n"
							 + "También se ha restablecido la configuración (1, 7.5)\n\n"
							 + "Esta simulación solo es posible (en esta versión del juego), ya que antes de permitir\n,"
							 + "a los jugadores jugar su turno, debe haber almenos un jugador no-eliminado. Lo que implica\n"
							 + "al menos un ganador siempre que haya más de un jugador.\n\n"
							 + "Se añadirá un jugador y se modificará su puntuación para que pierda.");
					
					this.jugadores.clear();
					
					simu1 = new CPUPlayer("CPU 1", this.mesa);
					simu1.setPuntos(8.5);
					
					jugadores.add(simu1);

					this.start(config);
					br();
					break;
				}
				jugadores.clear();
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
	
	
	/**
	 * <h3>Método que ejecuta el juego.</h3>
	 * <p>Recibe <strong>dos parámetros</strong> como configuración
	 * mediante un <strong>arreglo de tipo double</strong>.<br>
	 * - El primer valor indicará el tipo de
	 * baraja (1 o 2).<br>
	 * - El segundo será el valor a alcanzar durante el juego.</p>
	 * 
	 * @param config Arreglo de tipo double, debe contener dos parámetros.
	 */
	@Override
	public void start(double[] configE) {
		
		config[0] = configE[0];
		
		config[1] = configE[1];
		
		this.barajar();		
		
		System.out.println("         Comienza el juego\n");
		System.out.println(
				  "Valor a alcanzar: " + config[1] + "\n"
				+ "Tipo de baraja: " + config[0]);
		br();
		if(jugadores.size() > 1) // Modo multijugador
		do { // Juegadores juegan
			System.out.println("Número de cartas en la baraja: " + this.mesa.contarCartasBaraja());
			for (Player player : jugadores) {
				
				if(!player.isPlantado() && !player.isEliminado()) {
					if(!restoEliminados(player))
						player.jugarTurno(Juegos.SIETEYMEDIO);
					else
						player.plantarse();
				}
				
				if(player.getPuntos() >= config[1]) {
					player.plantarse();
					player.eliminar();
				}
				
			}
		} while(!checkPlantados());
		
		else { // Moodo un jugador
			Player player = jugadores.get(0);
			do {
				player.jugarTurno(Juegos.SIETEYMEDIO);
				if(player.getPuntos() >= config[1]) {
					player.plantarse();
					player.eliminar();
				}
				
				System.out.println(player.isPlantado());
				System.out.println(player.isEliminado());
			} while(!player.isPlantado());
		}
		
		/**
		 * He opdato por esta forma de obtener al ganador/ganadores, me ha resultado intuitiva.
		 * No tengo tiempo para implementar otra más eficiente. Pero aquí dejo la que creo que es más eficiente:
		 * 
		 * Crear un ArryList de enteros, almacenar todos los puntos de los jugadores (con un for), ya tenemos el máximo.
		 * Añadir a otra lista todos los jugadores que tengan esa puntuación (que previamente habrá sido filtrada para
		 * no se mayor que el valor a alcanzar).
		 * Y ya tendrías los ganadores.
		 * Es similar.
		 */
		
		// OBTENIENDO GANADORES
		ArrayList<Player> ganadores = null;
		Player ganador = null;
		boolean empate = false; // Si hay empate, los ganadores podrán jugar una revancha.
		
		// En caso de pleno.		
		// Variable auxiliar.
		int numeroGanadores = 0;
		for (Player player : jugadores) {
			if(player.getPuntos() == config[1])
				numeroGanadores++;
		}
		
		if (numeroGanadores > 1) {
			ganadores = new ArrayList<Player>(); // Se reserva memoria si hay más de 1 ganador.
			for (Player player : jugadores) {
				ganadores.add(player);
			}
		} else if (numeroGanadores == 1) {			
			for (Player player : jugadores) {				
					ganador = player;
			}
		}
		
		// En caso de que nadie haya logrado un pleno.
		if(numeroGanadores == 0) {
			
			// Variable auxiliares.
			double mayorPuntuacion = 0;
			for (Player player : jugadores) { // Primero vemos cuál es la mayor puntuación válida.			
				if(player.getPuntos() > mayorPuntuacion && !player.isEliminado()) {
					mayorPuntuacion = player.getPuntos();
				}
			}
			
			for (Player player : jugadores) { // Contamos los jugadores que la han sacado.
				if(player.getPuntos() == mayorPuntuacion)
					numeroGanadores++;
			}
			
			// Con este if, evito comprobar si el número de ganadores es mayor a uno
			// por cada jugador. Escribo dos veces el for, pero ahorro tiempo de ejecución.
			if(numeroGanadores == 1)
			for (Player player : jugadores) {
				if(player.getPuntos() == mayorPuntuacion)
					ganador = player;
			} else if(numeroGanadores != 0 && numeroGanadores > 1) {
				ganadores = new ArrayList<Player>(); // Se reserva espacio solo si hay más de 1 ganador.
				for (Player player : jugadores) {
					if(player.getPuntos() == mayorPuntuacion)
						ganadores.add(player);					
				}
			}
		}
		
		// MOSTRANDO RESULTADOS
		System.out.println(
				  "==================================\n"
				+ "|           RESULTADOS           |\n"
				+ "==================================\n");
		if(numeroGanadores == 0)
			System.out.println("¡¡¡HAN PERDIDO TODOS!!! Esto se parece un poco al lol.");
			
		else if(numeroGanadores == 1)
			System.out.println("EL GANADOR ES...." + "¡¡¡" + ganador.getNombre() + " CON " + ganador.getPuntos() + " PUNTOS!!!");
		
		else if(numeroGanadores > 1) {
			empate = true;
			System.out.print("LOS GANADORES SON... ¡¡¡");
			for (Player player : ganadores) {
				if(numeroGanadores > 2) {
					if (ganadores.get(jugadores.size() - 2) != player && ganadores.get(jugadores.size() - 1) != player)
						System.out.print(player.getNombre() + " CON " + player.getPuntos() + " PUNTOS!!!" + ", ");
					else if (ganadores.get(jugadores.size() - 2) == player)
						System.out.print(player.getNombre() + " CON " + player.getPuntos() + " PUNTOS" + " y ");
				} else if(numeroGanadores == 2) {                   
					if(ganadores.get(0) == player)                  
						System.out.print(player.getNombre() + " CON " + player.getPuntos() + " PUNTOS" + " y ");
					else                                             
						System.out.print(player.getNombre() + " CON " + player.getPuntos() + " PUNTOS!!!");
				}
			}
			System.out.print("!!!");
			br();
			br();
			finish();
		}
		
		if(empate) {
			System.out.println("Como hay más de un ganador, puedes empezar la revancha con los ganadores.\n"
					+ "¿Deseas la revancha?\n"
					+ "1. Sí.\n"
					+ "2. No.");
			
			// Variables de control/validación.
			String strOpcion = "";
			boolean esValida = false;
			do {
				System.out.print("Opción: ");
				strOpcion = sc.nextLine().trim();
				if (strOpcion.equals("1") || strOpcion.equals("2"))
					esValida = true;
				else
					System.out.println("Opción no válida, vuelve a intentarlo.");
			} while (!esValida);
			
			switch(strOpcion) {
			case "1":
				this.jugadores = ganadores; // Los jugadores ahora serán los ganadores.
				this.start(config); // Empezaremos la partida directamente con la misma configuración.
				break;
			case "2":
				System.out.println("\nMejor si ganan todos :)");
			}
		}
		
		finish();
	}

	/**
	 * Método que comprueba si todos los jugadore a parte del recibido, están
	 * eliminados.
	 */
	private boolean restoEliminados(Player player) {
		for (Player players : jugadores) {
			if (players != player)
				if (!players.isEliminado())
					return false;
		}
		return true;
	}

	@Override
	public Player nextTurno() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean checkPlantados() {
		for (Player player : jugadores) {
			if(player.isPlantado() != true) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * <h3>Reglas del juego<h3>
	 * Menú que muestra las reglas del juego.
	 */
	private void mostrarReglas() {
		System.out.println("===== Reglas =====\n");
		System.out.println(""
				+ "El juego es muy simple:\n"
				+ "- Comienzas sin cartas en tu mano.\n"
				+ "- Cuando sea tu turno (en caso de haber más de un jugador) robarás una carta\n"
				+ "  de una baraja que estará boca abajo.\n"
				+ "- Colocarás las cartas en tu mano. Cada carta tiene un valor:\n"
				+ "    - El valor de las cartas del 1 al 7 equivale a su valor numérico.\n"
				+ "    - El valor de Sota, Caballo y Rey equivale a 0.5.\n"
				+ "- Tu objetivo es lograr que la suma de los valores de las cartas que tengas en tu mano sea 7.5\n"
				+ "  o el valor más cercano (sin pasarte). Para esto puedes decidir entre\n"
				+ "  robar una carta más o plantarte en cada turno.\n"
				+ "- Gana el primer jugador que consiga 7.5 o el jugador que al plantarse\n"
				+ "  tenga la puntuación más cercana a 7.5.\n\n"
				+ "Cabe destacar que este juego permite configurar el tipo de baraja\n"
				+ "y el número a alcanzar, que sustituirá a 7.5.");
	}
	
	/**
	 * <h3>Menú de configuración</h3>
	 * <p>Menú que permite configurar las <strong>relgas del juego</strong>.<br>
	 * Devolverá la configuración en su forma correspondiente para este juego,
	 * un arreglo de tipo double.</p>
	 */
	private void mostrarMenuConfiguracion() {

		String strOpcion 	 = "";
		String strOpcion2 	 = "";

		// SUBMENÚ 1
		do {
			System.out.println("===== Configuración =====\n"
					+ "1. Tipo de baraja.\n"
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
	
	/**
	 * Método que simula la etiqueta br de html.<br>
	 * Otorga legibilidad (y abrevia la sentencia System.out.println();). 
	 */
	private static void br() {
		System.out.println();		
	}
}
