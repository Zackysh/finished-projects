package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.PokeType;
import models.Pokemon;

/**
 * Class that extends AbstractDAO and is oriented to extract information from
 * PokeDB.Pokemon to this application
 * 
 * @author AdriGB
 *
 */
public class DAO_Pokemon extends AbstractDAO {

	/**
	 * Method which extracts a resultSet from PokeDB that will contain a list with
	 * all existing Pokemons.
	 * 
	 * @return rs Result set with desired list.
	 */
	public ResultSet getPokemons() {

		try {

			String sql = "SELECT * FROM pokedb.pokemon";

			PreparedStatement stmt = conn.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();

			return rs;

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

		return null;
	}

	public void updatePokemon(int idP, String name, int number, String description, String skill, String category, double height,
			double weight, String sex, ArrayList<PokeType> types, int[] baseAtt) {
		
		PreparedStatement stmt;
		
    	String strBaseAtt = ""; // '2/1/2/1/4/5'
    	for (int i = 0; i < baseAtt.length; i++) {
    		strBaseAtt += baseAtt[i];
    		if(i != baseAtt.length - 1)
    			strBaseAtt += "/";
		}    	
    	
    	String sql = "UPDATE `pokedb`.`pokemon` "
    			+ "SET "
    			+ "`idpoke` = " + idP + ", "
    			+ "`Name` = " + name + ", "
    			+ "`Number` = " + number + ", "
    			+ "`Description` = " + description + ", "
    			+ "`Skill` = " + skill + ", "
    			+ "`Category` = " + category + ", "
    			+ "`Height` = " + height +", "
    			+ "`Weight` = " + weight + ", "
    			+ "`Sex` = " + sex + ", "
    			+ "`BaseAttributes` = " + strBaseAtt + ";";
    	
    	try {
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
		} catch (SQLException e) {}
    	
    	String sql2 = "DELETE FROM `pokedb`.`poketype` WHERE idpoke = " + idP + ";";    	
    	
    	try {
			stmt = conn.prepareStatement(sql2);
			stmt.executeUpdate();
		} catch (SQLException e) {}
    	
    	String sql3;
    	for (PokeType type : types) {
    		sql3 = "INSERT INTO `pokedb`.`poketype` (`idpoke`,`idtype`) VALUES (" + idP + ", " + type.getIdT() + ");";
    		try {
				stmt = conn.prepareStatement(sql2);
				stmt.executeUpdate();
			} catch (SQLException e) {}
		}    	
    }
}