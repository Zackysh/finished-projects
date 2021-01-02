package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Class that extends AbstractDAO and is oriented to insert
 * information into PokeDB.User.
 * 
 * @author AdriGB
 *
 */
public class DAO_SignUp extends AbstractDAO {

	/**
	 * Method that insert a new user into PoleDB.User table.
	 * 
	 * @param username Username given to the new user.
	 * @param password Password given to the new user.
	 * @returns int Number of row affected.
	 * @returns 0 If is not possible to affect a row. It may be due the username already exists.
	 */
	public int registerNewUser(String username, String password) {

		try {
			
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO pokedb.user (Username, Password) VALUES (?, ?)");
			stmt.setString(1, username);
			stmt.setString(2, password);
			
			int affectedRows = stmt.executeUpdate();
					
			return affectedRows;
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return 0; // It returns 0 if the statement or connection fail,
	}

}
