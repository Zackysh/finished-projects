package models.games;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
	
	public SieteYMedio(Mesa mesa) {
		super(mesa);
		super.modo = Juegos.SIETEYMEDIO;
		super.finished = false;
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
		double[] config = {1, 7.5};
		boolean salir = false;
		do {
			String strOpcion = "";
			System.out.println("===== Menu principal =====\n"
					+ "1. Comenzar el juego.\n"
					+ "2. Configurar reglas de juego.\n"
					+ "3. Añadir/Quitar jugadores.\n"
					+ "4. Cómo se juega.\n"
					+ "5. Salir.");
			
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
				config = mostrarMenuConfiguracion(config);
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
				salir = true;
				System.out.print("\n" 
						+ "================================\n"
						+ "|     ¡Hasta la próxima!       |\n"
						+ "================================\n");
			}
		} while (!salir);
		
		this.finished = true;		
	}
	
	private void mostrarMenuJugadores() {
		
		boolean salir = false;
		do {
			String strOpcion = "";
			System.out.println("===== Jugadores =====");
			System.out.println(
					  "1. Añadir nuevo.\n"
					+ "2. Actualizar existente.\n"
					+ "3. Mostrar lista.\n"
					+ "4. Atrás.");
			
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
						  + "2. Añadir jugador humano.\n"
						  + "3. Atrás.");
				String strOpcionTipoPlayer;

				boolean esTipo = false;
				do {
					System.out.print("Opción: ");
					strOpcionTipoPlayer = sc.nextLine().trim();
					if (strOpcionTipoPlayer.equals("1") || strOpcionTipoPlayer.equals("2")
							|| strOpcionTipoPlayer.equals("3"))
						esTipo = true;
					else
						System.out.println("Opción no válida, vuelve a intentarlo.");
				} while (!esTipo);
				String nombre;
				switch (strOpcionTipoPlayer) {
				case "1":
					System.out.print("Nuevo jugador (CPU):\n"
							+ "Introduce un nombre para este jugador (dejar en blanco para usar un nombre aleatorio): ");
					nombre = sc.nextLine();
					if (nombre.isBlank())
						nombre = StringUtils.nombreAleatorio();
					addPlayer(new CPUPlayer(nombre, 0, new Mano(mesa), mesa));
					System.out.println("¡CPU Player añadido con exito! :)");
					break;
				case "2":
					System.out.print("Nuevo jugador (humano):\n"
							+ "Introduce un nombre para este jugador (dejar en blanco para usar un nombre aleatorio): ");
					nombre = sc.nextLine();
					if (nombre.isBlank())
						nombre = StringUtils.nombreAleatorio();
					addPlayer(new HumanPlayer(nombre, 0, new Mano(mesa), mesa));
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
				do { // Se muestra una lista con los jugadores. Se debe seleccionar uno para poder continuar.
					String strIndiceJugador = "";

					System.out.println("Jugadores:");
					this.mostrarListaJugadores();

					boolean esInt = false;
					do {
						System.out.print("Opción: ");
						strIndiceJugador = sc.nextLine().trim();
						esInt = Validator.validateInt(strIndiceJugador);
						if (!esInt)
							System.out.println("Debes introducir un número.");
					} while (!esInt);

					indiceJugador = Integer.parseInt(strIndiceJugador) - 1;

					if (indiceJugador >= 0 && indiceJugador <= jugadores.size() - 1)
						esIndice = true;
					else
						System.out.println("Opción no válida.");

				} while (!esIndice);
				System.out.println("BIEEEEEEEEEEEEEEN");
				
				System.out.println(
					    "Nuevo Jugador:\n"
					  + "1. Cambiar nombre.\n"
					  + "2. Eliminar.\n"
					  + "3. Atrás.");

				String strOpcion2;
				boolean esValida2 = false;
				do {
					System.out.print("Opción: ");
					strOpcion2 = sc.nextLine().trim();
					if (strOpcion2.equals("1") || strOpcion2.equals("2") || strOpcion2.equals("3"))
						esValida2 = true;
					else
						System.out.println("Opción no válida, vuelve a intentarlo.");
				} while (!esValida2);
				switch (strOpcion2) {
				case "1": // Al cambiar el nombre sí es posible dejarlo vacío.
					Player temp = jugadores.get(indiceJugador);
					System.out.print("Nuevo nombre: ");
					nombre = sc.nextLine();
					if (nombre.isBlank())
						nombre = StringUtils.nombreAleatorio();
					temp.setNombre(nombre);
					break;
				case "2":
					System.out.println("Jugador eliminado: "
							+ StringUtils.normalizarString(jugadores.get(indiceJugador).getNombre()));
					jugadores.remove(indiceJugador);
					break;
				}
				br();
				break;
			case "3":
				br();
				System.out.println("Jugadores:");
				this.mostrarListaJugadores();
				br();
				break;
			case "4":
				salir = true;
				break;
			}
		} while (!salir);
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
		// La mesa se genera después de haber elegido la configuración en el menú
		// principal. Gracias a esto, es posible utilizar distintos tipos de barjas en
		// cada ejecución.
		int tipoBaraja = (int) config[0]; // Primer parámetro de la configuración.
		this.mesa 	   = new Mesa(new Baraja(tipoBaraja));
		
		double valorAlcanzar = config[1]; // Segundo parámetro de la configuración.
		
		this.barajar(); // BARAJA la baraja que está en la mesa.
		
		System.out.println("         Comienza el juego\n");
		System.out.println(
				  "Valor a alcanzar: " + valorAlcanzar + "\n"
				+ "Tipo de baraja: " + tipoBaraja);
		
		for (Player player : jugadores) {
			if(player)
			player.jugarTurno(Juegos.SIETEYMEDIO);
		}
		
		
//		System.out.println("Tu puntuación final es " + valor);
//		if(valor == valorAlcanzar)
//			System.out.print("\n"
//					+ "================================\n"
//					+ "|         ¡Has GANADO!         |\n"
//					+ "================================\n");
//		else
//			System.out.print("\n"
//					+ "================================\n"
//					+ "|         ¡Has PERDIDO!        |\n"
//					+ "================================\n");
		
	}

	@Override
	public Player nextTurno() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * <h3>Reglas del juego<h3>
	 * Menú que muestra las reglas del juego.
	 */
	private static void mostrarReglas() {
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
	private static double[] mostrarMenuConfiguracion(double[] eConfig) {

		double tipoBaraja 	 = eConfig[0];
		double valorAlcanzar = eConfig[1];
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
		
		double[] config = { tipoBaraja, valorAlcanzar };
		return config;
	}
	
	/**
	 * Método que simula la etiqueta br de html.<br>
	 * Otorga legibilidad (y abrevia la sentencia System.out.println();). 
	 */
	private static void br() {
		System.out.println();		
	}
	
//	public boolean lanzarJuego(double eConfig[]) {
//		boolean salir = false;
//		double[] config = eConfig;
//		do {
//
//			System.out.println(
//					  "==================================\n"
//					+ "|          7 y MEDIO             |\n"
//					+ "==================================\n");
//			String strOpcion = "";
//			System.out.println("===== Menu principal =====\n"
//					+ "1. Comenzar el juego.\n"
//					+ "2. Configurar reglas de juego.\n"
//					+ "3. Cómo se juega.\n"
//					+ "4. Salir.");
//			boolean esValida = false;
//			do {
//				System.out.print("Opción: ");
//				strOpcion = sc.nextLine().trim();
//				if (strOpcion.equals("1") || strOpcion.equals("2") || strOpcion.equals("3") || strOpcion.equals("4"))
//					esValida = true;
//				else
//					System.out.println("Opción no válida, vuelve a intentarlo.");
//			} while (!esValida);
//
//			switch (strOpcion) {
//			case "1":
//				br();
//				start(config);
//				br();
//				break;
//			case "2":
//				br();
//				config = mostrarMenuConfiguracion(config);
//				br();
//				break;
//			case "3":
//				br();
//				mostrarReglas();
//				br();
//				break;
//			case "4":
//				salir = true;
//				System.out.print("\n" 
//						+ "================================\n"
//						+ "|     ¡Hasta la próxima!       |\n"
//						+ "================================\n");
//			}
//		} while (!salir);
//		return salir;	
//	}
	
//	private static void comenzarJuego(double[] config) {
//		
//		int tipoBaraja 		 = (int) config[1];
//		double valorAlcanzar = config[2];
//		
//		Baraja baraja 	 = new Baraja((int) tipoBaraja);
//		Mano   mano   	 = new Mano();
//		double valor  	 = 0;
//		String strOpcion = "";
//		
//		baraja.barajar();
//		
//		System.out.println("         Comienza el juego\n");
//		System.out.println(
//				  "Valor a alcanzar: " + valorAlcanzar + "\n"
//				+ "Tipo de baraja: " + tipoBaraja);
//		System.out.print("Escribe algo para robar: ");
//		sc.nextLine();
//		mano.robar(baraja.robar());
//		valor += mano.ultimaCartaInsertada().getValor7yMedia();
//		
//		br();
//		System.out.println(
//				  "Carta robada:   " + mano.ultimaCartaInsertada() + "\n"
//				+ "Valor que suma: " + mano.ultimaCartaInsertada().getValor7yMedia() + "\n"
//				+ "Puntuación actual: " + valor);
//		br();
//		do {
//			System.out.print(
//					  "¿Qué deseas?:\n"
//					+ "1. Robar.\n"
//					+ "2. Plantarte.\n"
//					+ "Opción: ");
//			boolean esValida = false;
//			do {
//				strOpcion = sc.nextLine().trim();
//				if(strOpcion.equals("1") || strOpcion.equals("2"))
//					esValida = true;
//				else
//					System.out.println("Opcion no válida, vuelve a intentarlo.");
//			} while(!esValida);
//			
//			switch(strOpcion) {
//			case "1":
//				mano.robar(baraja.robar());
//				valor += mano.ultimaCartaInsertada().getValor7yMedia();
//				System.out.println(
//						  "Carta robada:   " + mano.ultimaCartaInsertada() + "\n"
//						+ "Valor que suma: " + mano.ultimaCartaInsertada().getValor7yMedia() + "\n"
//						+ "Puntuación actual: " + valor);
//				br();
//				break;
//			case "2":
//			}
//		} while(strOpcion.equals("1") && valor < valorAlcanzar);
//		System.out.println("Tu puntuación final es " + valor);
//		if(valor == valorAlcanzar)
//			System.out.print("\n"
//					+ "================================\n"
//					+ "|         ¡Has GANADO!         |\n"
//					+ "================================\n");
//		else
//			System.out.print("\n"
//					+ "================================\n"
//					+ "|         ¡Has PERDIDO!        |\n"
//					+ "================================\n");	
//	}
		
//	public static void main(String[] args) {
//		boolean seguir = true;
//		double[] config = {1, 7.5};
//		do {
//			seguir = lanzarJuego(config);
//		} while(seguir);
//		
//	}
}
