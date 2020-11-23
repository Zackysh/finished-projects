package models;

import java.time.LocalDate;

import Enums.Enums.EspeciePajaro;
import utils.DateHelper;

public class Pajaro extends Animal {
	
	private boolean cantor;
	private EspeciePajaro especie;

	public Pajaro(String nombre, LocalDate fechaNacimiento, double peso, EspeciePajaro especie, boolean cantor) {
		super(nombre, fechaNacimiento, peso);
		this.especie = especie;
		this.cantor = cantor;
	}
	
	@Override
	public String toString() {
		return "Ficha Pájaro\n"
			 + "Nombre: " + this.nombre
			 + "\nRaza: " + this.especie.dameEspecie()
			 + "\nFecha de nacimiento: " + this.fechaNacimiento
			 + "\nPeso: " + this.peso + "Kg"
			 + "\nEs cantor: " + this.cantor
			 + "\nComentarios: " + this.comentarios;
	}
	
}
