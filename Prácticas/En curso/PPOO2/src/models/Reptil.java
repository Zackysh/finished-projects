package models;

import java.time.LocalDate;

import Enums.Enums.EspecieReptil;
import utils.DateHelper;

public class Reptil extends Animal {
	
	private boolean venenoso;
	private EspecieReptil especie;
	
	public Reptil(String nombre, LocalDate fechaNacimiento, double peso, EspecieReptil especie, boolean venenoso) {
		super(nombre, fechaNacimiento, peso);
		this.especie = especie;
		this.venenoso = venenoso;
	}
	
	@Override
	public String toString() {
		return "Ficha Reptil\n"
			 + "Nombre: " + this.nombre
			 + "\nEspecie: " + this.especie.dameEspecie()
			 + "\nFecha de nacimiento: " + this.fechaNacimiento
			 + "\nPeso: " + this.peso + "Kg"
			 + "\nEs venenoso: " + this.venenoso
			 + "\nComentarios: " + this.comentarios;
	}
	
}
