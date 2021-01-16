package models.games;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import enums.EnumGame.Juegos;
import models.Mesa;
import models.players.CPUPlayer;
import models.players.HumanPlayer;
import models.players.Player;
import utils.StringUtils;
import utils.Validator;

public abstract class Game {
	
	private Scanner sc = new Scanner(System.in);

	// El juego transcurre en una mesa
	protected Mesa mesa;
	
	// Atributos importantes
	protected Juegos modo;
	protected ArrayList<Player> jugadores;
	
	// Atributos de estado
	protected double[] config;
	protected int ronda;
	protected boolean finished;
	
	public Game (Juegos modo, Mesa mesa) {
		this.modo = modo;
		this.mesa = mesa;
		this.jugadores = new ArrayList<Player>();
	}
	
	public double[] getConfig() {
		return config;
	}
	
	public Mesa getMesa() {
		return mesa;
	}

	public abstract void bienvenida();

	public abstract void menuPrincipal();

	/**
	 * Cada juego recibirá, al instanciarse, una configuración. Cada juego
	 * especificará qué valores deberá contener en su documentación.
	 */
	public abstract void start(double[] config);

	public abstract Player nextTurno();

	public void launch() {
		this.finished = false;
		bienvenida();
		menuPrincipal();
	}
	
	public void barajar() {
		this.mesa.barajarBaraja();
	}
	
	/**
	 * Método que devuleve las cartas de los jugadores a la baraja y
	 * restablece los datos de los jugadores (ver método resetPlayerState).
	 */
	public void finish() {
		for (Player player : jugadores) {
			player.devolverCartas();
			player.resetPlayerState();
		}
		this.finished = true;
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
	
	protected void mostrarMenuJugadores() {
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
					addPlayer(new CPUPlayer(nombre, this.mesa));
					
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
					addPlayer(new HumanPlayer(nombre, this.mesa));
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
						
						esInt = Validator.validateStrInt(strIndiceJugador);
						
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

					temp.setNombre(nombre); // ...a través de su referencia.
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

	private void br() {
		System.out.println();		
	}

}
