package models.players;

import java.util.ArrayList;

import enums.EnumGame.Juegos;
import models.Carta;
import models.Mano;
import models.Mesa;
import utils.StringUtils;

public abstract class Player {

	// Juega en una mesa y tiene una mano
	protected Mesa mesa;
	protected Mano mano;

	// Atributos
	protected String nombre;
	protected double puntos;
	// Atributos de estado
	protected boolean plantado;
	protected boolean eliminado;

	public Player(String nombre, Mesa mesa) {
		this.nombre = nombre;
		this.mesa = mesa;
		this.mano = new Mano(this.mesa.getBaraja());
		this.puntos = 0;
		this.eliminado = false;
		this.plantado = false;
	}

	public abstract void jugarTurno(Juegos juego);

	public void resetPlayerState() {
		this.puntos = 0;
		this.plantado = false;
		this.eliminado = false;
	}

	public void robar() {
		this.mano.insertarCarta(this.mesa.robarCarta());
	}

	public void devolverCartas() {
		this.mesa.insertarVariasCartas(this.mano.devolverPrimeraCarta());
	}

	public Carta jugarCarta(int index) {
		return this.mano.jugarCarta(index);
	}

	public int getNumeroCartas() {
		return this.mano.getNumroCartas();
	}

	public double getPuntos() {
		return this.puntos;
	}
	
	/**
	 * Este setter es utilizado en las simulaciones exclusivamente.
	 * @param nuevaPuntuacion
	 */
	public void setPuntos(double nuevaPuntuacion) {
		this.puntos = nuevaPuntuacion;
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

	public void eliminar() {
		eliminado = true;
	}

	public boolean isEliminado() {
		return eliminado;
	}

	@Override
	public String toString() {
		return "AbstractPlayer [nombre=" + nombre + ", puntos=" + puntos + ", mano=" + mano + ", mesa=" + mesa + "]";
	}

}
