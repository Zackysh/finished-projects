package models.players;

import enums.EnumGame.Juegos;
import models.Mesa;
import utils.StringUtils;

public class CPUPlayer extends Player {

	public CPUPlayer(String nombre, Mesa mesa) {
		super(nombre, mesa);
	}

	@Override
	public void jugarTurno(Juegos juego) {
		System.out.print(
				    "Jugador: " + this.getNombre() + "\n"
				  + "Puntuaci�n actual: " + this.puntos + "\n");
		StringUtils.br();
		if(this.puntos < 6) {
			System.out.println("Ha decidido robar");
			this.robar();
			System.out.println("puntos" + this.mano.ultimaCartaInsertada().getValor7yMedia());
			this.puntos += this.mano.ultimaCartaInsertada().getValor7yMedia();
			System.out.println("puntos" + this.mano.ultimaCartaInsertada().getValor7yMedia());
			
			System.out.println(
					  "Carta robada: " + this.mano.ultimaCartaInsertada() + "\n"
					+ "Valor que suma: " + this.mano.ultimaCartaInsertada().getValor7yMedia() + "\n"
					+ "Nueva puntuaci�n: " + this.puntos);
		} else {
			System.out.println("Ha decidido plantarse con " + this.puntos);
			this.plantarse();
		}
		
		StringUtils.br();
	}
}