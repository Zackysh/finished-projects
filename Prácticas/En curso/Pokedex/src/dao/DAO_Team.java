package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.CPokemon;
import models.Pokemon;

public class DAO_Team extends AbstractDAO {
	
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
