package models;

public class Perro {
	
	public enum RazaPerro {E1("PASTOR_ALEMAN"), E2("HUSKY"), E3("DALMATA");
		private String raza;
	
		RazaPerro(String raza) {	
			this.raza = raza;
		}
		
		public String dameRaza() {
			return raza;
		}
	}
	
	private RazaPerro raza;
	private String nombre;
	
	public Perro(String nombre, RazaPerro raza) {
		super();
		this.raza = raza;
		this.nombre = nombre;
	}
	
	@Override
	public String toString() {
		return "Perro [raza=" + raza.dameRaza() + ", nombre=" + nombre + "]";
	}	
	
}
