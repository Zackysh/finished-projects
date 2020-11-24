package models;

import java.time.LocalDate;

import Enums.Enums.RazaPerro;
import utils.DateHelper;

public class Perro extends Animal {
	
	private RazaPerro raza;
	private String chip;
	
	public Perro(String nombre, LocalDate fechaNacimiento, double peso, RazaPerro raza, String chip) {
		super(nombre, fechaNacimiento, peso);
		this.raza = raza;
		this.chip = chip;
	}

	@Override
	public String toString() {
		return "Ficha Perro\n"
			 + "Nombre: " + this.nombre
			 + "\nRaza: " + this.raza.dameRaza()
			 + "\nFecha de nacimiento: " + this.fechaNacimiento
			 + "\nPeso: " + this.peso + "Kg"
			 + "\nMicrochip: " + this.chip
			 + "\nComentarios: " + this.comentarios
			 + "\n";
	}
	
}
