package main;

import enums.EnumGame.Juegos;
import models.Baraja;
import models.Mesa;
import models.players.CPUPlayer;
import models.players.Player;

public class Testing {
	public static void main(String[] args) {
		
		Baraja baraja = new Baraja();
		for (int i = 1; i <= 6; i++) {
			baraja.insertarCartaArriba(i);
		}
		Mesa mesa = new Mesa(baraja);
		Player player = new CPUPlayer("CPU 1", mesa);
		
		
		System.out.println("CARTAS del jugador: " + player.getNumeroCartas());
		System.out.println("CARTAS en mesa: " + mesa.contarCartasBaraja());
		
		System.out.println(baraja);
		
		player.robar();
		player.robar();
		player.robar();
		player.robar();
		
		
		System.out.println("CARTAS del jugador: " + player.getNumeroCartas());
		System.out.println("CARTAS en mesa: " + mesa.contarCartasBaraja());
		
		System.out.println(baraja);
		
		player.devolverCartas();
		
		
		System.out.println("CARTAS del jugador: " + player.getNumeroCartas());
		System.out.println("CARTAS en mesa: " + mesa.contarCartasBaraja());
		
		System.out.println(baraja);
	}
}
