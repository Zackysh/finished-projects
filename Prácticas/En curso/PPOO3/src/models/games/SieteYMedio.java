package models.games;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import enums.EnumGame.Juegos;
import models.Baraja;
import models.Mano;
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
	private double valorAlcanzar;
	private int tipoBaraja;

	
	/**
	 * Constructor.
	 */
	public SieteYMedio() {
		this.jugadores = new ArrayList<Player>();
		this.modo = Juegos.SIETEYMEDIO;
		this.finished = false;
		this.mesa = new Mesa(new Baraja(1));
		this.config = new double[2];
		// Establezco una configuración por defecto al crear el juego.
		this.config[0] = 1;
		this.config[1] = 7.5;
		// Le asigno estos valores a las variables que facilitarán su implementación.
		this.tipoBaraja = (int) config[0];
		this.valorAlcanzar = config[1];
	}
	
	public void cambiarConfig(double[] nuevaConfig) {
		config = nuevaConfig;
		tipoBaraja = (int) config[0];
		valorAlcanzar = config[1];		
	}

	public double[] obtenerConfigActual() {
		return this.config;
	}
	
	public void mostrarListaJugadores() {
		int cont = 1;
		for (Player player : jugadores) {
			System.out.println(cont++ + ". " + StringUtils.normalizarString(player.getNombre()) + ".");
		}
	}
	
	public void addPlayer(Player player) {
		jugadores.add(player);
		Collections.shuffle(jugadores);
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
	 * que el jugador presione salga del juego.
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
					+ "5. Simular partidas (testing)"
					+ "6. Salir.");
			
			// Variables de control/validación.
			String strOpcion = "";
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
				System.out.println(
						  "1. Forzar empate.\n"
						+ "2. Forzar ganador con pleno.\n"
						+ "3. Forzar ganador sin pleno.\n"
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
					System.out.println(
							  "Todos los jugadores existentes han sido borrados para lograr una simulación exitosa.\n"
							 + "También se ha restablecido la configuración (1, 7.5)\n"
							 + "Se añadirán dos jugadores. La baraja solo contendrá cuatro cartas cartas que causarán un empate.");
//					this.mesa = new Mesa
					Player simu1 = new HumanPlayer("Simu Uno", this);
					
				}
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
	
	private void mostrarMenuJugadores() {
		
		boolean salir = false;
		do {
			System.out.println("===== Jugadores =====");
			System.out.println(
					  "1. Añadir nuevo.\n"
					+ "2. Actualizar existente.\n"
					+ "3. Mostrar lista.\n"
					+ "4. Atrás.");
			
			// Variables de control/validación.
			String strOpcion = "";
			boolean esValida = false;
			do {
				System.out.print("Opción: ");
				strOpcion = sc.nextLine().trim();
				
				if (strOpcion.equals("1") || strOpcion.equals("2") || strOpcion.equals("3") || strOpcion.equals("4"))
					esValida = true;
				else
					System.out.println("Opción no válida, vuelve a intentarlo.");
				
			} while (!esValida);

			switch (strOpcion) {
			case "1":
				
				br();
				System.out.println(
						    "Nuevo Jugador:\n"
						  + "1. Añadir CPU Player.\n"
						  + "2. Añadir Human Player.\n"
						  + "3. Atrás.");
				// Variables de control/validación.
				strOpcion = ""; // Reutilizamos esta variable (no causa conflicto.
				boolean esTipo = false;
				do {
					System.out.print("Opción: ");
					strOpcion = sc.nextLine().trim();
					
					if (strOpcion.equals("1") || strOpcion.equals("2")
							|| strOpcion.equals("3"))
						esTipo = true;
					else
						System.out.println("Opción no válida, vuelve a intentarlo.");
					
				} while (!esTipo);
				
				// Declaramos esta variable fuera del switch pues se utiliza más de un caso.
				// Creo que queda más limpio
				String nombre;
				switch (strOpcion) {
				case "1":
					// Asignación de nombre (CPU Player).
					System.out.print("Nuevo jugador (CPU):\n"
							+ "Introduce un nombre para este jugador\n"
							+ "(dejar en blanco para usar un nombre aleatorio): ");
					
					nombre = sc.nextLine();
					if (nombre.isBlank())
						nombre = StringUtils.nombreAleatorio();
					
					// Añadir CPU a este juego con dicho nombre.
					addPlayer(new CPUPlayer(nombre, this));
					
					System.out.println("¡CPU Player añadido con exito! :)");
					break;
					
				case "2":
					// Asignación de nombre (Human Player).
					System.out.print("Nuevo jugador (humano):\n"
							+ "Introduce un nombre para este jugador\n"
							+ "(dejar en blanco para usar un nombre aleatorio): ");
					nombre = sc.nextLine();
					if (nombre.isBlank())
						nombre = StringUtils.nombreAleatorio();
					
					// Añadir jugador a este juego con dicho nombre.
					addPlayer(new HumanPlayer(nombre, this));
					System.out.println("¡Human Player añadido con exito! :)");
					break;
					
				case "3":
					salir = true;
				}
				br();
				break;
				
			case "2":
				
				br();
				int indiceJugador;

				boolean esIndice = false;

				do {
					// Variable que utiliza el usuario para seleccionar un jugador antes de editarlo.
					String strIndiceJugador = "";
					
					// Se muestra una lista con los jugadores. Se debe seleccionar uno para poder continuar.
					System.out.println("Jugadores:");
					this.mostrarListaJugadores();

					boolean esInt = false; // Debe ser int, pues hará referencia a un índice de una lista.
					do {
						System.out.print("Opción: ");
						strIndiceJugador = sc.nextLine().trim();
						
						esInt = Validator.validateInt(strIndiceJugador);
						
						if (!esInt)
							System.out.println("Debes introducir un número.");
						
					} while (!esInt); // No pasará hasta que sea un entero.

					indiceJugador = Integer.parseInt(strIndiceJugador) - 1;

					if (indiceJugador >= 0 && indiceJugador <= jugadores.size() - 1)
						esIndice = true;
					else
						System.out.println("Opción no válida.");

				} while (!esIndice); // No pasa hasta que se haya introducido un índice válido.
				
				// El jugador ya está seleccionado, hora de editarlo / eliminarlo.
				System.out.println(
					    "Nuevo Jugador:\n"
					  + "1. Cambiar nombre.\n"
					  + "2. Eliminar.\n"
					  + "3. Atrás.");
				
				// Reutilizamos las variables de control del anterior menú (no genera conflicto).
				strOpcion = "";
				esValida = false;
				do {
					System.out.print("Opción: ");
					strOpcion = sc.nextLine().trim();
					if (strOpcion.equals("1") || strOpcion.equals("2") || strOpcion.equals("3"))
						esValida = true;
					else
						System.out.println("Opción no válida, vuelve a intentarlo.");
				} while (!esValida);
				
				switch (strOpcion) {
				case "1":
					// Obtenemos la referencia del jugador deseado.
					Player temp = jugadores.get(indiceJugador);
					
					// Le asignamos un nuevo nombre...
					System.out.print(
							  "Nuevo nombre\n"
							+ "(dejar en blanco para usar un nombre aleatorio):");
					
					nombre = sc.nextLine();
					if (nombre.isBlank())
						nombre = StringUtils.nombreAleatorio();
					
					temp.setNombre(nombre); //  ...a través de su referencia.
					break;
					
				case "2":
					
					System.out.println("Jugador eliminado: "
							+ StringUtils.normalizarString(jugadores.get(indiceJugador).getNombre()));
					// Eliminamos el jugador al que hace referencia el índice.
					jugadores.remove(indiceJugador);
					break;
				}
				br();
				break;
				
			case "3":
				// Mostramos la lista de jugadores.
				br();
				System.out.println("Jugadores:");
				this.mostrarListaJugadores();
				br();
				break;
				
			case "4":
				salir = true;
				break;
			}
		} while (!salir); // No saldrá hasta que el usuario pulse salir.
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
	public void start(double[] config) {
		
		/*
		 * La mesa se genera después de haber elegido la configuración en el menú
		 * principal. Gracias a esto, es posible utilizar distintos tipos de barjas en
		 * cada ejecución.
		 */
		tipoBaraja = (int) config[0]; // Primer parámetro de la configuración.
		this.mesa 	   = new Mesa(new Baraja(tipoBaraja));
		
		valorAlcanzar = config[1]; // Segundo parámetro de la configuración.
		
		this.barajar(); // BARAJA la baraja que está en la mesa.
		
		System.out.println("         Comienza el juego\n");
		System.out.println(
				  "Valor a alcanzar: " + valorAlcanzar + "\n"
				+ "Tipo de baraja: " + tipoBaraja);
		br();
		do {
			for (Player player : jugadores) {
				
				if(!player.isPlantado())
					player.jugarTurno(Juegos.SIETEYMEDIO);
				
				if(player.getPuntos() >= valorAlcanzar)
					player.plantarse();
				
			}
		} while(!checkPlantados());
		
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
		// RESULTADOS
		boolean empate = false; // Si hay empate, los ganadores podrán jugar una revancha.
		Player ganador = null;
		ArrayList<Player> ganadores = null;
		
		// En caso de pleno.
		
		// Variable auxiliar.
		int numeroGanadores = 0;
		for (Player player : jugadores) {
			if(player.getPuntos() == valorAlcanzar)
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
			int mayorPuntuacion = 0;
			for (Player player : jugadores) { // Primero vemos cuál es la mayor puntuación válida.			
				if(player.getPuntos() > mayorPuntuacion && player.getPuntos() < valorAlcanzar) {
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
			} else if(numeroGanadores != 0) {
				ganadores = new ArrayList<Player>(); // Se reserva espacio solo si hay más de 1 ganador.
				for (Player player : jugadores) {
					if(player.getPuntos() == mayorPuntuacion)
						ganadores.add(player);					
				}
			}
		} // Ganadores obtenidos.
		
		System.out.println(
				  "==================================\n"
				+ "|           RESULTADOS           |\n"
				+ "==================================\n");
		if(numeroGanadores == 0)
			System.out.println("¡¡¡HAN PERDIDO TODOS!!! Esto se parece un poco al lol.");
			
		else if(numeroGanadores == 1)
			System.out.println("EL GANADOR ES...." + "¡¡¡" + ganador.getNombre() + "!!!");
		
		else {
			empate = true;
			System.out.print("LOS GANADORES SON... ¡¡¡");
			for (Player player : ganadores) {
				if(numeroGanadores > 2) {
					if (ganadores.get(jugadores.size() - 2) != player && ganadores.get(jugadores.size() - 1) != player)
						System.out.print(player.getNombre() + ", ");
					else if (ganadores.get(jugadores.size() - 2) == player)
						System.out.print(player.getNombre() + " y ");
				} else {
					if(ganadores.get(0) == player)
						System.out.println(player.getNombre() + " y ");
					else
						System.out.println(player.getNombre());
				}
			}
			System.out.print("!!!");
			br();
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
		
		// Volverá al menú del propio juego, no se borrarán los jugadores, pero sí se resetearán los valores necesarios.
		this.finish();
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

		double nuevoTipoBaraja 	 = config[0];
		double nuevoValorAlcanzar = config[1];
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
							tipoBaraja = 1;
							esValida2 = true;
							break;
						case "2":
							System.out.println("Has elegido la baraja doble (80 caras).");
							tipoBaraja = 2;
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
					valorAlcanzar = temp;
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
				+ "Tipo de baraja: " + tipoBaraja + "\n"
				+ "Valor a alcanzar: " + valorAlcanzar);
		
		double[] nuevaConfig = {nuevoTipoBaraja, nuevoValorAlcanzar};
		
		cambiarConfig(nuevaConfig);
	}
	
	/**
	 * Método que simula la etiqueta br de html.<br>
	 * Otorga legibilidad (y abrevia la sentencia System.out.println();). 
	 */
	private static void br() {
		System.out.println();		
	}
}
