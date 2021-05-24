package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.CPokemon;
import models.Pokemon;

/**
 * Class that extends AbstractDAO and is oriented to extract information from
 * PokeDB.Team and PokeDB.CPokemon to this application.
 * 
 * @author AdriGB
 *
 */
public class DAO_Team extends AbstractDAO {
	
	/**
	 * This method return existing CPokemons (they belong to a team).
	 * 
	 * @param idUser Get pokemons from given user team (user by id)
	 * @return all CPokemons that belongs given user team
	 */
	public ResultSet getCPokemons(int idUser) {
		try {
			String sql = "SELECT * FROM pokedb.cpokemon WHERE "
					   + "idTeam = (SELECT idTeam FROM pokedb.team WHERE idUser = '" + idUser + "');";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			return rs;
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
			ex.fillInStackTrace();
		}
		return null;
	}
	
	/**
	 * Create a team for a new user (It's very important to create it at
	 * the same time as user, it could cause fatal errors).
	 * 
	 * @param idUser
	 * @param teamName
	 */
	public void createTeamForUser(int idUser, String teamName) {
		String sql = "INSERT INTO `pokedb`.`team` " + "("
				+ "`name`, `idUser`) "
				+ "VALUES ('" + teamName + "', " + idUser +");";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.fillInStackTrace();
		}
	}
	
	/**
	 * Check if given nickname (of cpokemon) is free, as they must
	 * have an unique nickname.
	 * 
	 * @param nickname
	 * @returns true if it's not free
	 * @returns false if it's free
	 */
	public boolean checkNickname(String nickname) {
		try {
			String sql = "SELECT * FROM pokedb.cpokemon WHERE "
					   + "nickname = '" + nickname + "';";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			return rs.next();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
			ex.fillInStackTrace();
		}
		return true;
	}

	/**
	 * Update nickname and level of given CPokemon. It's supposed that given
	 * Pokemon java object has already updated attributes.
	 * 
	 * @param cpokemon
	 */
	public void updateCPokemon(CPokemon cpokemon) {
		try {
			String sql = "UPDATE `pokedb`.`cpokemon` SET `nickname` = '" + cpokemon.getNickName() + "', `level` = "
					+ cpokemon.getLevel() + " WHERE `idCPoke` = " + cpokemon.getIdCPoke();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
			ex.fillInStackTrace();
		}
	}
	
	/**
	 * Method that caught (insert a caught pokemon) into given user team.
	 * 
	 * @param cpokemon
	 * @param idUser
	 */
	public void caughtPokemon(CPokemon cpokemon, int idUser) {
		int idTeam = getUserTeam(idUser);
		String sql = "INSERT INTO `pokedb`.`cpokemon` " + "("
				+ "`nickname`, `level`, `idPoke`, `idTeam`) "
				+ "VALUES ('" + cpokemon.getNickName() + "', " + cpokemon.getLevel() + ", " + cpokemon.getPokemon().getIdP() + ", " + idTeam + ");";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.fillInStackTrace();
		}
	}
	
	/**
	 * This method checks if given Pokemon appears on a team.
	 * 
	 * @param pokemon
	 * @returns false if given Pokemon doensn't appear on a team
	 * @returns true if given Pokemon appear on a team
	 */
	public boolean checkPokemon(Pokemon pokemon) {
		try {
			String sql = "SELECT * FROM pokedb.cpokemon WHERE "
					   + "idPoke = " + pokemon.getIdP() + ";";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			return rs.next();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
			ex.fillInStackTrace();
		}
		return true;
	}
	
	/**
	 * Check if given team name is free.
	 * 
	 * @param name
	 * @returns true if it's not free
	 * @returns false if it's free
	 */
	public boolean checkTeamName(String name) {
		try {
			String sql = "SELECT * FROM pokedb.team WHERE "
					   + "name = '" + name + "';";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			return rs.next();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
			ex.fillInStackTrace();
		}
		return true;
	}
	
	/**
	 * Method that releases given Pokemon, it must belong to a team,
	 * so it will be released from it.
	 * 
	 * @param cpokemon
	 */
	public void releasePokemon(CPokemon cpokemon) {
		System.out.println("Released");
		String sql = "DELETE FROM `pokedb`.`cpokemon` "
				+ "WHERE idcpoke = " + cpokemon.getIdCPoke() + ";";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.fillInStackTrace();
		}
	}
	
	/**
	 * Method that returns user idTeam.
	 * 
	 * @param idUser
	 * @return idTeam of given user
	 */
	public int getUserTeam(int idUser) {
		try {
			String sql = "SELECT idTeam FROM pokedb.team WHERE "
					   + "idUser = " + idUser + ";";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			return rs.getInt("idTeam");
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
			ex.fillInStackTrace();
		}
		return 0;
	}
}
