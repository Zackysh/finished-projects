package models.players;

import java.util.Scanner;

import enums.EnumGame.Juegos;
import models.Mesa;
import utils.StringUtils;

public class HumanPlayer extends Player {
	
	private static Scanner sc = new Scanner(System.in);	
	
	/**
	 * Constructor. Recibe un nombre y la mesa en la que jugará el jugador.
	 * Se le asignará autoáticamente una mano. Esta mano recibirá la mesa también.
	 * Comenzrá con una puntuación igual a cero.
	 * Por último se iniciará el boolean plantado a falso.
	 * 
	 * Realmente no lo implemento, pero sería útil para intercambiar cartas
	 * entre jugadores, por ejemplo.
	 * 
	 * @param nombre String con el nombre del jugador.
	 * @param mesa Mesa en la que jugará.
	 */
	public HumanPlayer(String nombre, Mesa mesa) {
		super(nombre, mesa);
	}
	
	/**
	 * El jugador jugará de una forma diferente en cada juego.
	 */
	@Override
	public void jugarTurno(Juegos juego) {
		
		switch(juego) {
		case SIETEYMEDIO:
			String strOpcion = "";
			
			System.out.print(
					  "Jugador: " + this.getNombre() + "\n"
					+ "¿Qué deseas?:\n"
					+ "1. Robar.\n"
					+ "2. Plantarte.\n"
					+ "Puntuación actual: " + this.puntos + "\n"
					+ "Opción: ");
			boolean esValida = false;
			do {
				strOpcion = sc.nextLine().trim();
				if (strOpcion.equals("1") || strOpcion.equals("2"))
					esValida = true;
				else
					System.out.println("Opcion no válida, vuelve a intentarlo.");
			} while (!esValida);
			
			StringUtils.br();
			switch (strOpcion) {
			case "1":
				this.robar();
				this.puntos += this.mano.ultimaCartaInsertada().getValor7yMedia();
				System.out.println(
					  "Carta robada: " + this.mano.ultimaCartaInsertada() + "\n"
					+ "Valor que suma: " + this.mano.ultimaCartaInsertada().getValor7yMedia() + "\n"
					+ "Nueva puntuación: " + this.puntos);
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