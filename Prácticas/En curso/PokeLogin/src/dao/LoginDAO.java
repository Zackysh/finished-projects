package dao;

import java.sql.*;

public class LoginDAO extends AbstractDAO {

	/**
	 * Method that check if there's an user registered with
	 * given user name and password.
	 * çd
	 * @param username.
	 * @param password.
	 * @returns true If desired user exist.
	 * @returns false If desired user doesn't exist.
	 */
	public boolean login(String username, String password) {

		try {
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM pokedb.user WHERE username = '" + username + "' AND password = '"
					+ password + "'";
			ResultSet rs = stmt.executeQuery(sql);
			
			return rs.next();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return false;
	}

}