package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class which contains a JDBC connection with PokeDB.
 * This connection is protected so its objective is establish a common connection for different DAO classes.
 * 
 * This class contains some methods which help to manage issues while manipulating/accessing tables.
 * 
 * @author AdriGB
 *
 */
public abstract class AbstractDAO {

	// "Global" connection
	protected Connection conn;

	// Constructor trying to establish connection with PokeDB
	public AbstractDAO() {
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/pokedb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"admin", "admin");
		} catch (SQLException sqle) {
			System.out.println(sqle.fillInStackTrace());
		}
	}

	/**
	 * Method which check if given user-name exist in PokeDB.
	 * 
	 * @param username User-name to check.
	 * @returns true If given user-name exist in PokeDB.
	 * @returns false If given user-name doesn't exist in PokeDB.
	 */
	public boolean checkUsername(String username) {

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM pokedb.user WHERE username = '" + username + "'");
			return rs.next();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return false;
	}

	/**
	 * Check if the connection is null or if its linked with PokeDB.
	 * 
	 * @returns false If conn is null.
	 * @returns true If conn isn't null.
	 */
	public boolean checkConnection() {
		if (this.conn == null)
			return false;
		else
			return true;
	}
}
