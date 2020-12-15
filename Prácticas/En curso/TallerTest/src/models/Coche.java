package models;

/**
 * Clase que representa un coche.
 * Hereda los atributos y comportamiento de la clase Vehiculo.
 * 
 * Además tendrá un atributo propio, será la "estadoVentanillas".
 * 
 * @author AdriGB
 * @author Jonhy
 *
 */
public class Coche extends Vehiculo {
	
	// Atributo propio.
	private String estadoVentanillas;

	/**
	 * Constructor que ingresa los atributos heredados y, además establece el estado
	 * inicial de las ventanillas.
	 * 
	 * @param color Color del vehículo.
	 * @param matricula Matrícula del vehículo.
	 * @param marca Marca del vehículo.
	 * @param modelo Modelo del vehículo.
	 * @param velocidadMaxima Velocidad máxima del vehículo.
	 * @param telefonoOwner Teléfono del dueño del vehículo.
	 */
	public Coche(String color, String matricula, String marca, String modelo, double velocidadMaxima,
			String telefonoOwner) {
		
		super(color, matricula, marca, modelo, velocidadMaxima, telefonoOwner);
		// Las ventanillas estarán bajadas en un principio.
		this.estadoVentanillas = "Subidas";
	}

	/**
	 * Método que establece un nuevo estado (valor String) a las ventinllas,
	 * "Subidas", en caso de no tener ya este valor.
	 */
	public void subirVentanillas() {
		if (estadoVentanillas == "Subidas") {
			System.out.println("Las ventanillas ya estaban subidas.");
		} else {
			this.estadoVentanillas = "Subidas";
			System.out.println("Se han subido las ventanillas.");
		}
	}

	/**
	 * Método que establece un nuevo estado (valor String) a las ventinllas,
	 * "Bajadas", en caso de no tener ya este valor.
	 */
	public void bajarVentanillas() {
		if (estadoVentanillas == "Bajadas") {
			System.out.println("Las ventanillas ya estaban bajadas.");
		} else {
			this.estadoVentanillas = "Bajadas";
			System.out.println("Se han bajado las ventanillas.");
		}
	}

	@Override
	public String toString() {
		return "Coche[Matricula: " + matricula + ", Marca: " + marca + ", Modelo: " + modelo + ", Ventanillas: "
				+ estadoVentanillas + ", Color: " + color + ", Velocidad Actual: " + velocidadActual + "km/h"
				+ ", Velocidad Máxima: " + velocidadMaxima + "Km/h" + ", Telefono Dueño: " + telefonoOwner + "]";
	}

}
