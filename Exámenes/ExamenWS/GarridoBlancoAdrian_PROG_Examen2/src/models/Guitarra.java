package models;
/**
 * Clase que genera un InstrumentoMusical de tipo guitarra.
 * 
 * Implementará el método tocarInstrumento()
 * 
 * @author alumnado AdriGB
 */
public class Guitarra extends InstrumentoMusical {

	public Guitarra(double precio, String marca, String modelo) {
		super(precio, marca, modelo);
	}

	@Override
	public String tocarInstrumento() {
		return "Triiing";
	}

	@Override
	public String toString() {
		return "Guitarra [Marca: " + marca + ", Modelo: " + modelo + ", Precio: " + precio + "€]";
	}
}
