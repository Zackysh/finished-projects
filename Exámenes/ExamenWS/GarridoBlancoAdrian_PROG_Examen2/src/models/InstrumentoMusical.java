package models;

import interfaces.Playable;

public abstract class InstrumentoMusical implements Playable {
	
	protected double precio;
	protected String marca;
	protected String modelo;
	
	// CONSTRUCTOR
	public InstrumentoMusical(double precio, String marca, String modelo) {
		this.precio = precio;
		this.marca = marca;
		this.modelo = modelo;
	}
	
	// GETTERS AND SETTERS
	public double getPrecio() {
		return precio;
	}



	public void setPrecio(double precio) {
		this.precio = precio;
	}



	public String getMarca() {
		return marca;
	}



	public void setMarca(String marca) {
		this.marca = marca;
	}



	public String getModelo() {
		return modelo;
	}



	public void setModelo(String modelo) {
		this.modelo = modelo;
	}



	@Override
	public abstract String tocarInstrumento();
	
}
