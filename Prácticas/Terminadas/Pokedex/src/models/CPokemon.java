package models;

/**
 * This class is used to provide users their own Pokemon team. This application
 * behave like a Pokedex, so its necessary to create specific/independent instances of each pokemon
 * to "fill" user teams.
 * 
 * CPokemon = Caught Pokemon.
 * 
 * @author AdriGB
 *
 */
public class CPokemon {

	private int idCPoke;
	private Pokemon pokemon;
	private int level;
	private String nickName;

	/**
	 * Constructor of the class. It just receive its attributes.
	 * 
	 * @param idCPoke
	 * @param pokemon
	 * @param level
	 * @param nickName
	 */
	public CPokemon(int idCPoke, Pokemon pokemon, int level, String nickName) {
		this.idCPoke = idCPoke;
		this.pokemon = pokemon;
		this.level = level;
		this.nickName = nickName;
	}
	
	/** getters and setters */ 
	public int getIdCPoke() {
		return idCPoke;
	}

	public Pokemon getPokemon() {
		return pokemon;
	}

	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}

	public String getNickName() {
		return nickName;
	}
	
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Override
	public String toString() {
		return "CPokemon [idCPoke=" + idCPoke + ", pokemon=" + pokemon + ", level=" + level + ", nickName=" + nickName
				+ "]";
	}
}
