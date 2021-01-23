package dao;

import java.sql.*;

/**
 * Class that extends AbstractDAO and is oriented to extract information from
 * PokeDB.User to this application.
 * 
 * @author AdriGB
 *
 */
public class DAO_Login extends AbstractDAO {

	/**
	 * Method that check if there's an user registered with given user name and
	 * password.
	 * 
	 * @param username.
	 * @param password.
	 * @returns true If desired user exist.
	 * @returns false If desired user doesn't exist.
	 */
	public boolean login(String username, String password) {

		try {
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM pokedb.user WHERE username = '" + username + "' AND password = '" + password
					+ "'";
			ResultSet rs = stmt.executeQuery(sql);

			return rs.next();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
			ex.fillInStackTrace();
		}
		return false;
	}
	
	public int getUserId(String userName) {
		try {
			String sql = "SELECT idUser FROM pokedb.user WHERE userName like '" + userName + "'";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			return rs.getInt("idUser");
		} catch (SQLException ex) {
			ex.fillInStackTrace();
		}
		return 0;
	}

}