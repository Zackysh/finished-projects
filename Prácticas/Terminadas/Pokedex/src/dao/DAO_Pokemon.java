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
			ex.fillInStackTrace();
		}
		return null;
	}
	
	/**
	 * Method that inserts given Pokemon into PokeDB.
	 * 
	 * @param pokemonToInsert
	 */
	public void insertPokemon(Pokemon pokemonToInsert) {

		int idP = pokemonToInsert.getIdP();
		String name = pokemonToInsert.getName();
		int number = pokemonToInsert.getNumber();
		String description = pokemonToInsert.getDescription();
		String skill = pokemonToInsert.getSkill();
		String category = pokemonToInsert.getCategory();
		double height = pokemonToInsert.getHeight();
		double weight = pokemonToInsert.getWeight();
		String sex = pokemonToInsert.getSex();
		ArrayList<PokeType> types = pokemonToInsert.getTypes();
		int[] baseAtt = pokemonToInsert.getBaseAtt();
		PreparedStatement stmt;

		String strBaseAtt = ""; // '2/1/2/1/4/5'
		for (int i = 0; i < baseAtt.length; i++) {
			strBaseAtt += baseAtt[i];
			if (i != baseAtt.length - 1)
				strBaseAtt += "/";
		}
		
		String presql = "ALTER TABLE pokemon AUTO_INCREMENT = " + pokemonToInsert.getIdP() + ";";
		
		try {
			stmt = conn.prepareStatement(presql);
			stmt.executeUpdate();
		} catch (SQLException e) {
		}

		String sql = "INSERT INTO `pokedb`.`pokemon` " + "("
				+ "`Name`, `Number`, `Description`, `Skill`, `Category`, `Height`, `Weight`, `Sex`, `BaseAttributes`) "
				+ "VALUES " + "(" + "'" + name + "'," + number + "," + "'" + description + "', " + "'" + skill + "',"
				+ "'" + category + "'," + height + "," + weight + "," + "'" + sex + "'," + "'" + strBaseAtt + "');";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.fillInStackTrace();
			System.err.println(e.getMessage());
		}

		String sql3;
		for (PokeType type : types) {
			sql3 = "INSERT INTO `pokedb`.`poketype` (`idpoke`,`idtype`) VALUES (" + idP + ", " + type.getIdT() + ");";
			try {
				stmt = conn.prepareStatement(sql3);
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.fillInStackTrace();
				System.err.println(e.getMessage());
			}
		}

	}

	/**
	 * Method that removes given pokemon in pokeDB.
	 * 
	 * @param pokemonToRemove
	 */
	public void deletePokemon(Pokemon pokemonToRemove) {
		PreparedStatement stmt;
		
		String sql = "DELETE FROM `pokedb`.`poketype` WHERE idpoke = " + pokemonToRemove.getIdP() + ";";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.fillInStackTrace();
			System.err.println(e.getMessage());
		}
		String sql2 = "DELETE FROM pokemon WHERE idpoke = " + pokemonToRemove.getIdP() + ";";
		try {
			stmt = conn.prepareStatement(sql2);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.fillInStackTrace();
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Method that updates given Pokemon in pokeDB.
	 * 
	 * @param pokemonToUpdate
	 */
	public void updatePokemon(Pokemon pokemonToUpdate) {
		int idP = pokemonToUpdate.getIdP();
		String name = pokemonToUpdate.getName();
		int number = pokemonToUpdate.getNumber();
		String description = pokemonToUpdate.getDescription();
		String skill = pokemonToUpdate.getSkill();
		String category = pokemonToUpdate.getCategory();
		double height = pokemonToUpdate.getHeight();
		double weight = pokemonToUpdate.getWeight();
		String sex = pokemonToUpdate.getSex();
		ArrayList<PokeType> types = pokemonToUpdate.getTypes();
		int[] baseAtt = pokemonToUpdate.getBaseAtt();
		PreparedStatement stmt;

		String strBaseAtt = ""; // '2/1/2/1/4/5'
		for (int i = 0; i < baseAtt.length; i++) {
			strBaseAtt += baseAtt[i];
			if (i != baseAtt.length - 1)
				strBaseAtt += "/";
		}
		String sql = "UPDATE `pokedb`.`pokemon` " + "SET " + "`Name` = '" + name + "', " + "`Number` = " + number + ", "
				+ "`Description` = '" + description + "', " + "`Skill` = '" + skill + "', " + "`Category` = '"
				+ category + "', " + "`Height` = " + height + ", " + "`Weight` = " + weight + ", " + "`Sex` = '" + sex
				+ "', " + "`BaseAttributes` = '" + strBaseAtt + "' " + "WHERE idpoke = " + idP + ";";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.fillInStackTrace();
			System.err.println(e.getMessage());
		}
		String sql2 = "DELETE FROM `pokedb`.`poketype` WHERE idpoke = " + idP + ";";
		try {
			stmt = conn.prepareStatement(sql2);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.fillInStackTrace();
			System.err.println(e.getMessage());
		}
		String sql3;
		for (PokeType type : types) {
			sql3 = "INSERT INTO `pokedb`.`poketype` (`idpoke`,`idtype`) VALUES (" + idP + ", " + type.getIdT() + ");";
			try {
				stmt = conn.prepareStatement(sql3);
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.fillInStackTrace();
				System.err.println(e.getMessage());
			}
		}
	}
}