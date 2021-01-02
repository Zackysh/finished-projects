package models;

import java.util.ArrayList;

public class Pokemon {

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

	public Pokemon(int int1, String string, String string2, double double1, double double2, String string3,
			String string4, ArrayList<PokeType> tiko) {
		// TODO Auto-generated constructor stub
	}
	
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

	public ArrayList<PokeType> getTypes() {
		return types;
	}

	@Override
	public String toString() {
		return "Pokemon [idP=" + idP + ", name=" + name + ", description=" + description + ", types=" + types + "]";
	}

}
