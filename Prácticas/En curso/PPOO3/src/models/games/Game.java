package models.games;

import java.util.ArrayList;
import java.util.Collections;

import enums.EnumGame.Juegos;
import models.Mesa;
import models.players.Player;
import utils.StringUtils;

public abstract class Game {

	protected boolean finished;
	protected Juegos modo;
	protected ArrayList<Player> jugadores;
	protected Mesa mesa;
	protected int ronda;
	
	public Game(Mesa mesa) {
		this.mesa = mesa;
		jugadores = new ArrayList<Player>();
	}

	public abstract void bienvenida();
	public abstract void menuPrincipal();
	public abstract Player nextTurno();
	public abstract void start(double[] config);
	
	public void launch() {
		bienvenida();
		menuPrincipal();
	}
	
	public void barajar() {
		this.mesa.baraja.barajar();
	}
	
	public void finish() {
		for (Player player : jugadores) {
			for (int i = 0; i < player.getNumeroCarta(); i++) {
				mesa.addCartaBaraja(player.jugarCarta());				
			}
		}
	}
}
