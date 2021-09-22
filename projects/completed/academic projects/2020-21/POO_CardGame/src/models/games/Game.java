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
	 * Cada juego recibir�, al instanciarse, una configuraci�n. Cada juego
	 * especificar� qu� valores deber� contener en su documentaci�n.
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
	 * M�todo que devuleve las cartas de los jugadores a la baraja y
	 * restablece los datos de los jugadores (ver m�todo resetPlayerState).
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
					  "1. A�adir nuevo.\n"
					+ "2. Actualizar existente.\n"
					+ "3. Mostrar lista.\n"
					+ "4. Atr�s.");
			
			// Variables de control/validaci�n.
			String strOpcion = "";
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
			case "1":
				
				br();
				System.out.println(
						    "Nuevo Jugador:\n"
						  + "1. A�adir CPU Player.\n"
						  + "2. A�adir Human Player.\n"
						  + "3. Atr�s.");
				// Variables de control/validaci�n.
				strOpcion = ""; // Reutilizamos esta variable (no causa conflicto.
				boolean esTipo = false;
				do {
					System.out.print("Opci�n: ");
					strOpcion = sc.nextLine().trim();
					
					if (strOpcion.equals("1") || strOpcion.equals("2")
							|| strOpcion.equals("3"))
						esTipo = true;
					else
						System.out.println("Opci�n no v�lida, vuelve a intentarlo.");
					
				} while (!esTipo);
				
				// Declaramos esta variable fuera del switch pues se utiliza m�s de un caso.
				// Creo que queda m�s limpio
				String nombre;
				switch (strOpcion) {
				case "1":
					// Asignaci�n de nombre (CPU Player).
					System.out.print("Nuevo jugador (CPU):\n"
							+ "Introduce un nombre para este jugador\n"
							+ "(dejar en blanco para usar un nombre aleatorio): ");
					
					nombre = sc.nextLine();
					if (nombre.isBlank())
						nombre = StringUtils.nombreAleatorio();
					
					// A�adir CPU a este juego con dicho nombre.
					addPlayer(new CPUPlayer(nombre, this.mesa));
					
					System.out.println("�CPU Player a�adido con exito! :)");
					break;
					
				case "2":
					// Asignaci�n de nombre (Human Player).
					System.out.print("Nuevo jugador (humano):\n"
							+ "Introduce un nombre para este jugador\n"
							+ "(dejar en blanco para usar un nombre aleatorio): ");
					nombre = sc.nextLine();
					if (nombre.isBlank())
						nombre = StringUtils.nombreAleatorio();
					
					// A�adir jugador a este juego con dicho nombre.
					addPlayer(new HumanPlayer(nombre, this.mesa));
					System.out.println("�Human Player a�adido con exito! :)");
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

					boolean esInt = false; // Debe ser int, pues har� referencia a un �ndice de una lista.
					do {
						System.out.print("Opci�n: ");
						strIndiceJugador = sc.nextLine().trim();
						
						esInt = Validator.validateStrInt(strIndiceJugador);
						
						if (!esInt)
							System.out.println("Debes introducir un n�mero.");
						
					} while (!esInt); // No pasar� hasta que sea un entero.

					indiceJugador = Integer.parseInt(strIndiceJugador) - 1;

					if (indiceJugador >= 0 && indiceJugador <= jugadores.size() - 1)
						esIndice = true;
					else
						System.out.println("Opci�n no v�lida.");

				} while (!esIndice); // No pasa hasta que se haya introducido un �ndice v�lido.
				
				// El jugador ya est� seleccionado, hora de editarlo / eliminarlo.
				System.out.println(
					    "Nuevo Jugador:\n"
					  + "1. Cambiar nombre.\n"
					  + "2. Eliminar.\n"
					  + "3. Atr�s.");
				
				// Reutilizamos las variables de control del anterior men� (no genera conflicto).
				strOpcion = "";
				esValida = false;
				do {
					System.out.print("Opci�n: ");
					strOpcion = sc.nextLine().trim();
					if (strOpcion.equals("1") || strOpcion.equals("2") || strOpcion.equals("3"))
						esValida = true;
					else
						System.out.println("Opci�n no v�lida, vuelve a intentarlo.");
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

					temp.setNombre(nombre); // ...a trav�s de su referencia.
					break;

				case "2":

					System.out.println("Jugador eliminado: "
							+ StringUtils.normalizarString(jugadores.get(indiceJugador).getNombre()));
					// Eliminamos el jugador al que hace referencia el �ndice.
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
		} while (!salir); // No saldr� hasta que el usuario pulse salir.
	}

	private void br() {
		System.out.println();		
	}

}
