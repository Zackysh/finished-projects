package Interfaces;

/**
 * Esta interfaz se implementa para que se pase a Vehiculo y a su vez desde vehiculo lo herede a sus hijos.
 * arrancar y parar se implementara más adelante. 
 * @author jonat
 *@author AdriGB
 */

public interface Arrancable {

	public abstract void arrancar();
	public abstract void parar();
	
	
}
