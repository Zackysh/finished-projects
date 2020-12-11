package models;

public class Bateria extends InstrumentoMusical {

	public Bateria(double precio, String marca, String modelo) {
		super(precio, marca, modelo);
	}

	@Override
	public String tocarInstrumento() {
		return "Tumtumpá";
	}

	@Override
	public String toString() {
		return "Bateria [Marca: " + marca + ", Modelo: " + modelo + ", Precio: " + precio + "€]";
	}
}
