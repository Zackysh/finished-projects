package models.players;

import java.util.Scanner;

import enums.EnumGame.Juegos;
import models.Mesa;
import utils.StringUtils;

public class HumanPlayer extends Player {
	
	private static Scanner sc = new Scanner(System.in);	
	
	/**
	 * Constructor. Recibe un nombre y la mesa en la que jugar� el jugador.
	 * Se le asignar� auto�ticamente una mano. Esta mano recibir� la mesa tambi�n.
	 * Comenzr� con una puntuaci�n igual a cero.
	 * Por �ltimo se iniciar� el boolean plantado a falso.
	 * 
	 * Realmente no lo implemento, pero ser�a �til para intercambiar cartas
	 * entre jugadores, por ejemplo.
	 * 
	 * @param nombre String con el nombre del jugador.
	 * @param mesa Mesa en la que jugar�.
	 */
	public HumanPlayer(String nombre, Mesa mesa) {
		super(nombre, mesa);
	}
	
	/**
	 * El jugador jugar� de una forma diferente en cada juego.
	 */
	@Override
	public void jugarTurno(Juegos juego) {
		
		switch(juego) {
		case SIETEYMEDIO:
			String strOpcion = "";
			
			System.out.print(
					  "Jugador: " + this.getNombre() + "\n"
					+ "�Qu� deseas?:\n"
					+ "1. Robar.\n"
					+ "2. Plantarte.\n"
					+ "Puntuaci�n actual: " + this.puntos + "\n"
					+ "Opci�n: ");
			boolean esValida = false;
			do {
				strOpcion = sc.nextLine().trim();
				if (strOpcion.equals("1") || strOpcion.equals("2"))
					esValida = true;
				else
					System.out.println("Opcion no v�lida, vuelve a intentarlo.");
			} while (!esValida);
			
			StringUtils.br();
			switch (strOpcion) {
			case "1":
				this.robar();
				this.puntos += this.mano.ultimaCartaInsertada().getValor7yMedia();
				System.out.println(
					  "Carta robada: " + this.mano.ultimaCartaInsertada() + "\n"
					+ "Valor que suma: " + this.mano.ultimaCartaInsertada().getValor7yMedia() + "\n"
					+ "Nueva puntuaci�n: " + this.puntos);
				break;
				case "2":
					this.plantarse();
					break;
				}
			StringUtils.br();
			break;
		case MUS:
			break;
		case TUTE:
			break;			
		}
		
	}
	
}