package models;

public class CPokemon {

	private int idCPoke;
	private Pokemon pokemon;
	private int level;
	private String nickName;

	/**
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
