package models;

import java.time.LocalDate;
import java.util.Date;

public abstract class Animal {

	protected String nombre;
	protected LocalDate fechaNacimiento;
	protected double peso;
	protected String comentarios;

	public Animal(String nombre, LocalDate fechaNacimiento, double peso) {
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

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	@Override
	public String toString() {
		return "nombre=" + nombre + ", fechaNacimiento=" + this.fechaNacimiento + ", peso=" + peso + ", comentarios="
				+ comentarios + ", ";
	}

}
