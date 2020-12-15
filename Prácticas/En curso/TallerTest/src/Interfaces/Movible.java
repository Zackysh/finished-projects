package Interfaces;

/**
 * Esta interfaz se implementa para que se pase a Vehiculo y a su vez desde vehiculo lo herede a sus hijos.
 * acelerar,frenar y frenarHastaParar se implementara más adelante.
 * @author jonat
 * @author AdriGB
 *
 */

public interface Movible {
	
	public abstract void acelerar(double v);
	public abstract void frenar(double v);
	public abstract void frenarHastaParar();
}
