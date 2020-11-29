package models.players;

import java.util.Scanner;

import enums.EnumGame.Juegos;
import models.Mano;
import models.Mesa;

public class HumanPlayer extends Player {
	
	private static Scanner sc = new Scanner(System.in);	

	public HumanPlayer(String nombre, int puntos, Mano mano, Mesa mesa) {
		super(nombre, puntos, mano, mesa);
		super.plantado = false;
	}
	
	@Override
	public void jugarTurno(Juegos juego) {
		switch(juego) {
		case SIETEYMEDIO:
			String strOpcion = "";
			do {
				System.out.print(
						  "¿Qué deseas?:\n"
						+ "1. Robar.\n"
						+ "2. Plantarte.\n"
						+ "Opción: ");
				boolean esValida = false;
				do {
					strOpcion = sc.nextLine().trim();
					if(strOpcion.equals("1") || strOpcion.equals("2"))
						esValida = true;
					else
						System.out.println("Opcion no válida, vuelve a intentarlo.");
				} while(!esValida);
				
				switch(strOpcion) {
				case "1":
					mano.robar(); // this.mano.robar();
					this.puntos += this.mano.ultimaCartaInsertada().getValor7yMedia();
					System.out.println(
							  "Carta robada:   " + this.mano.ultimaCartaInsertada() + "\n"
							+ "Valor que suma: " + this.mano.ultimaCartaInsertada().getValor7yMedia() + "\n"
							+ "Puntuación actual: " + this.puntos);
					break;
				case "2":
					plantado = true;
				}
			} while(strOpcion.equals("1") && valor < valorAlcanzar);
			break;
		case MUS:
			break;
		case TUTE:
			break;			
		}
		
	}
	
}