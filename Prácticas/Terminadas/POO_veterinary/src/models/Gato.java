package models;

import java.time.LocalDate;

import Enums.Enums.RazaGato;
import utils.DateHelper;

public class Gato extends Animal {	
	
	private String chip;
	private RazaGato raza;

	public Gato(String nombre, LocalDate fechaNacimiento, double peso, RazaGato raza, String chip) {
		super(nombre, fechaNacimiento, peso);
		this.raza = raza;
		this.chip = chip;
	}

	@Override
	public String toString() {
		return "Ficha Gato\n"
			 + "Nombre: " + this.nombre
			 + "\nRaza: " + this.raza.dameRaza()
			 + "\nFecha de nacimiento: " + this.fechaNacimiento
			 + "\nPeso: " + this.peso + "Kg"
			 + "\nMicrochip: " + this.chip
			 + "\nComentarios: " + this.comentarios;
	}
}
