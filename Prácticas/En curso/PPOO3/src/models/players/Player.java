package models.players;

import enums.EnumGame.Juegos;
import models.Carta;
import models.Mano;
import models.Mesa;
import models.games.Game;
import utils.StringUtils;

public abstract  class Player {
	
	protected String nombre;
	protected int puntos;
	protected Mano mano;
	protected Game game;
	protected Mesa mesa;
	protected boolean plantado;
	
	public Player(String nombre, int puntos, Mano mano, Game game) {
		super();
		this.nombre = nombre;
		this.puntos = puntos;
		this.mano = mano;
		this.mesa = game.getMesa();
	}
	
	public abstract void jugarTurno(Juegos juego);

	public void resetPlayerState() {
		this.puntos = 0;
		this.plantado = false;
	}
	
	public void robar() {
		this.mano.insertarCarta(this.mesa.robarCarta());
	}
	
	public Carta jugarCarta() {
		return this.mano.jugarCarta(0);		
	}
	
	public int getNumeroCartas() {
		return this.mano.getNumroCartas();
	}
	
	public int getPuntos() {
		return this.puntos;	
	}
	
	public String getNombre() {
		return StringUtils.normalizarString(nombre);
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void plantarse() {
		this.plantado = true;
	}
	
	public boolean isPlantado() {
		return plantado;
	}
		
	@Override
	public String toString() {
		return "AbstractPlayer [nombre=" + nombre + ", puntos=" + puntos + ", mano=" + mano + ", mesa=" + mesa + "]";
	}
	
}
