package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SignupDAO extends AbstractDAO {

	public SignupDAO() {

	}

	/**
	 * Method that insert a new user into user table.
	 * 
	 * @param username Username given to the new user.
	 * @param password Password given to the new user.
	 * @returns int Number of row affected.
	 * @returns 0 If its not possible to affect a row. It may be due the username already exists
	 * 			  or any field is null.
	 */
	public int registerNewUser(String username, String password) {

		try {
			Statement stmt = conn.createStatement();
			int affectedRows = stmt.executeUpdate(
					"INSERT INTO pokedb.user (Username, Password) VALUES ('" + username + "', '" + password + "')");

			return affectedRows;
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return 0; // It returns 0 if the statement or connection fail,
	}

}
