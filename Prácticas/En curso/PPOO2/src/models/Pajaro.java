package models;

import java.util.Date;

public class Pajaro extends Animal {
	
	public enum EspeciEPajaro {CANARIO("Canario"), PERIQUITO("Periquito"), AGAP("Agapornis");
		private String especie;
	
		EspeciEPajaro(String especie) {
			this.especie = especie;
		}
		
		public String dameEspecie() {
			return especie;
		}
	}
	
	private boolean cantor;
	private EspeciEPajaro especie;

	public Pajaro(String nombre, Date fechaNacimiento, double peso, EspeciEPajaro especie, boolean cantor) {
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
