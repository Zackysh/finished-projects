package models;

import java.util.Date;

public class Gato extends Animal {
	
	public enum RazaGato {COMUN("Pastor alemán"), SIAMES("Siamés"), PERSA("Persa"), ANGORA("Angora"), SCOTTISH("ScottishFold");
		private String raza;
	
		RazaGato(String raza) {
			this.raza = raza;
		}
		
		public String dameRaza() {
			return raza;
		}
	}
	
	private String chip;
	private RazaGato raza;

	public Gato(String nombre, Date fechaNacimiento, double peso, RazaGato raza, String chip) {
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
