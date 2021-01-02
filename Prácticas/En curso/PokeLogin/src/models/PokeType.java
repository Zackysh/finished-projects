package models;

import java.awt.Color;

public class PokeType {
	
	private int idT;
	private String name;
	private Color color;
	
	public PokeType (int idT, String nombre, Color color) {
		this.idT = idT;
		this.name = nombre;
		this.color = color;
	}
	
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
		
	
}
