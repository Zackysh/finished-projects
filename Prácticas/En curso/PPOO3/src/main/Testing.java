package main;

import models.games.Game;
import models.games.SieteYMedio;
import models.players.CPUPlayer;
import models.players.Player;

public class Testing {
	public static void main(String[] args) {
		
		System.out.println("Creando jugador:");
		
		Player player = new CPUPlayer("Test", new SieteYMedio());
		
		player.robar();
		
		player.eliminar();
		
		
	}
}
