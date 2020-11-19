package models;

import java.util.Date;

public abstract class Animal {

	protected String nombre;
	protected Date fechaNacimiento;
	protected double peso;
	protected String comentarios;

	public Animal(String nombre, Date fechaNacimiento, double peso) {
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.peso = peso;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public String getNombre() {
		return nombre;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	@Override
	public String toString() {
		return "nombre=" + nombre + ", fechaNacimiento=" + fechaNacimiento + ", peso=" + peso + ", comentarios="
				+ comentarios + ", ";
	}

}
