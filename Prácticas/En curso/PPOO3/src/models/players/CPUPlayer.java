package models.players;

import enums.EnumGame.Juegos;
import models.Mano;
import models.Mesa;

public class CPUPlayer extends Player{

	public CPUPlayer(String nombre, int puntos, Mano mano, Mesa mesa) {
		super(nombre, puntos, mano, mesa);
	}

	@Override
	public void jugarTurno(Juegos juego) {
		// TODO Auto-generated method stub
		
	}	
}