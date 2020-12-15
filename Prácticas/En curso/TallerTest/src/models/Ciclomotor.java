package models;

/**
 * Clase que representa un ciclomotor. Hereda los atributos y comportamiento de
 * la clase Vehiculo.
 * 
 * @author AdriGB
 * @author Jonhy
 *
 */
public class Ciclomotor extends Vehiculo {

	/**
	 * Constructor que ingresa los atributos heredados.
	 * 
	 * @param color Color del vehículo.
	 * @param matricula Matrículo del vehículo.
	 * @param marca Marca del vehículo.
	 * @param modelo Modelo del vehículo.
	 * @param velocidadMaxima Velocidad máxma del vehículo.
	 * @param telefonoOwner Teléfono del dueño del vehículo.
	 */
	public Ciclomotor(String color, String matricula, String marca, String modelo, double velocidadMaxima,
			String telefonoOwner) {
		
		super(color, matricula, marca, modelo, velocidadMaxima, telefonoOwner);
	}

	@Override
	public String toString() {
		return "Ciclomotor[Matricula: "+ matricula
				+ ", Marca: " + marca
				+ ", Modelo: " + modelo
				+ ", Color: " + color
				+ ", Velocidad Actual: " + velocidadActual + "km/h"
				+ ", Velocidad Máxima: " + velocidadMaxima + "Km/h"
				+ ", Telefono Dueño: " + telefonoOwner + "]";
	}
}
