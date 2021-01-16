package models;

import java.util.ArrayList;

/**
 * A simple pokemon implementation, it stores necessary information to be able
 * to store the object into a Pokedex.
 * 
 * @author AdriGB
 *
 */
public class Pokemon {

	// Attributes
	private int idP;
	private String name;
	private int number;
	private String description;
	private String skill;
	private String category;
	private double height;
	private double weight;
	private String sex;
	private ArrayList<PokeType> types;
	private int[] baseAtt;

	public Pokemon(int idP, String name, int number, String description, String skill, String category, double height,
			double weight, String sex, ArrayList<PokeType> types2, int[] baseAtt) {

		this.idP = idP;
		this.name = name;
		this.number = number;
		this.description = description;
		this.skill = skill;
		this.category = category;
		this.height = height;
		this.weight = weight;
		this.sex = sex;
		this.types = types2;
		this.baseAtt = baseAtt;

	}

	// GETTERS AND SETTERS
	public int[] getBaseAtt() {
		return baseAtt;
	}

	public int getIdP() {
		return idP;
	}

	public String getName() {
		return name;
	}

	public int getNumber() {
		return number;
	}

	public String getDescription() {
		return description;
	}

	public String getSkill() {
		return skill;
	}

	public String getCategory() {
		return category;
	}

	public double getHeight() {
		return height;
	}

	public double getWeight() {
		return weight;
	}

	public String getSex() {
		return sex;
	}

	/**
	 * Method that returns an arrayList of PokeType objects.
	 * 
	 * @return types.
	 */
	public ArrayList<PokeType> getTypes() {
		return types;
	}

	@Override
	public String toString() {
		return "Pokemon [idP=" + idP + ", name=" + name + ", description=" + description + ", types=" + types + "]";
	}

}
