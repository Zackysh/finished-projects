package models;

import java.awt.Color;

/**
 * Method that simulate a Pokemon Type. It has a color attached, a name and a id.
 * 
 * @author AdriGB
 *
 */
public class PokeType implements Comparable<PokeType> {
	
	// Attributes
	private int idT;
	private String name;
	private Color color;
	
	/**
	 * Constructor that receives main attributes.
	 * 
	 * @param idT
	 * @param nombre
	 * @param color This color will define how this type will be shown in this application
	 */
	public PokeType (int idT, String nombre, Color color) {
		this.idT = idT;
		this.name = nombre;
		this.color = color;
	}
	
	/** GETTERS AND SETTERS */
	public Color getColor() {
		return color;
	}

	public int getIdT() {
		return idT;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "PokeType [idT=" + idT + ", name=" + name + "]";
	}

	@Override
	public int compareTo(PokeType o) {
		int result = this.getName().compareTo(o.getName()); 
		if (result > 0)
			return 1;
		else if (result == 0)
			return 0;
		return -1;
	}

}
