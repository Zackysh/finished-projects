package models;

/**
 * Clase que representa una motocicleta. Hereda los atributos y comportamientos
 * de Vehiculo.
 * 
 * Además tendrá un atributo propio, será la marca del escape.
 * 
 * @author AdriGB
 * @author Jonhy
 *
 */
public class Motocicleta extends Vehiculo {
	
	// Atributo propio.
	private String marcaEscape;

	/**
	 * Constructor que ingresa los atributos heredados y, además, ingresa la marca
	 * del escape.
	 * 
	 * @param color Color del vehículo.
	 * @param matricula Matrícula del vehículo.
	 * @param marca Marca del vehículo.
	 * @param modelo Modelo del vehículo.
	 * @param velocidadMaxima Velocidad máxima del vehículo.
	 * @param telefonoOwner Teléfono del dueño del vehículo.
	 * @param marcaEscape Marca del escape que tendrá la moto.
	 */
	public Motocicleta(String color, String matricula, String marca, String modelo, double velocidadMaxima,
			String telefonoOwner, String marcaEscape) {
		super(color, matricula, marca, modelo, velocidadMaxima, telefonoOwner);
		this.marcaEscape = marcaEscape;
	}

	/**
	 * Devuelve el atributo "marcaEscape" de esta clase.
	 * 
	 * @return marcaEscape.
	 */
	public String getMarcaEscape() {
		return marcaEscape;
	}

	/**
	 * Establece el atributo "marcaEscape" de esta clase.
	 * 
	 * @param nuevaMarca Nueva marca que tendrá el escape.
	 */
	public void cambiarEscape(String nuevaMarca) {
		System.out.println(
				  "El escape desechado es: " + getMarcaEscape()
				+ "\nEl nuevo escape es: " + nuevaMarca);
		System.out.println();
		marcaEscape = nuevaMarca;
		System.out.println();
	}

	@Override
	public String toString() {
		return "Motocicleta[Matricula: " + matricula
				+ ", Marca: " + marca
				+ ", Modelo: " + modelo
				+ ", Marca Escape: " + marcaEscape
				+ ", Color: " + color
				+ ", Velocidad Actual: " + velocidadActual + "km/h"
				+ ", Velocidad Máxima: " + velocidadMaxima + "Km/h"
				+ ", Telefono Dueño: " + telefonoOwner + "]";
	}

}
