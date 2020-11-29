package models.players;

import java.util.ArrayList;

import enums.EnumGame.Juegos;
import models.Carta;
import models.Mano;
import models.Mesa;

public abstract  class Player {
	
	protected String nombre;
	protected int puntos;
	protected Mano mano;
	protected Mesa mesa;
	
	public boolean plantado;
	
	public Player(String nombre, int puntos, Mano mano, Mesa mesa) {
		super();
		this.nombre = nombre;
		this.puntos = puntos;
		this.mano = mano;
		this.mesa = mesa;
	}
	
	public abstract void jugarTurno(Juegos juego);

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Carta jugarCarta() {
		return this.mano.jugarCarta(0);		
	}
	
	public int getNumeroCarta() {
		return this.mano.getNumroCartas();
	}

	@Override
	public String toString() {
		return "AbstractPlayer [nombre=" + nombre + ", puntos=" + puntos + ", mano=" + mano + ", mesa=" + mesa + "]";
	}
	
}
