package models;

import java.util.Date;

public class Reptil extends Animal {

	public enum EspecieReptil {TORTUGA("Tortuga"), IGUANA("Iguana"), DRAGONK("Dragón de Komodo");
		private String especie;
	
		EspecieReptil(String especie) {
			this.especie = especie;
		}
		
		public String dameEspecie() {
			return especie;
		}
	}
	
	private boolean venenoso;
	private EspecieReptil especie;
	public Reptil(String nombre, Date fechaNacimiento, double peso, EspecieReptil especie, boolean venenoso) {
		super(nombre, fechaNacimiento, peso);
		this.especie = especie;
		this.venenoso = venenoso;
	}
	
	@Override
	public String toString() {
		return "Ficha Reptil\n"
			 + "Nombre: " + this.nombre
			 + "\nRaza: " + this.especie.dameEspecie()
			 + "\nFecha de nacimiento: " + this.fechaNacimiento
			 + "\nPeso: " + this.peso + "Kg"
			 + "\nEs venenoso: " + this.venenoso
			 + "\nComentarios: " + this.comentarios;
	}
	
}
