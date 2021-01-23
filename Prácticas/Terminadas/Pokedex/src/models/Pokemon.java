package models;

import java.util.ArrayList;

/**
 * A simple pokemon implementation, it stores necessary information to be able
 * to store Pokemons into a Pokedex.
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

	/**
	 * Constructor of the class, it just receives main main attributes.
	 * 
	 * @param idP (its not necessary at all, its independent from its id on DB and its not shown).
	 * @param name
	 * @param number
	 * @param description
	 * @param skill
	 * @param category
	 * @param height
	 * @param weight
	 * @param sex
	 * @param types2
	 * @param baseAtt
	 */
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

	/** GETTERS AND SETTERS */
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
	
	public void setIdP(int idP) {
		this.idP = idP;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@SuppressWarnings("unchecked")
	public void setTypes(ArrayList<PokeType> types) {
		this.types = (ArrayList<PokeType>) types.clone();
	}

	public void setBaseAtt(int[] baseAtt) {
		this.baseAtt = baseAtt;
	}

	@Override
	public String toString() {
		return "Pokemon [idP=" + idP + ", name=" + name + ", description=" + description + ", types=" + types + "]";
	}

}
