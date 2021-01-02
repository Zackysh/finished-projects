package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class that extends AbstractDAO and is oriented to extract
 * information from PokeDB.Types and PokeDB.PokeType to this application
 * 
 * @author AdriGB
 *
 */
public class DAO_Type extends AbstractDAO {
	
	/**
	 * Method which extracts a resultSet from PokeDB
	 * that will contain a list with all existing Types.
	 * 
	 * @return rs Result set with desired list.
	 */
	public ResultSet getTypes() {

		try {

			String sql = "SELECT * FROM type ORDER BY idtype";

			PreparedStatement stmt = conn.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();

			return rs;

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

		return null;
	}

	/**
	 * Method which extracts a resultSet from PokeDB
	 * that will contain a list with all existing Types associated
	 * to a Pokemon id (given as an integer parameter)
	 * 
	 * @return rs Result set with desired list.
	 */
	public ResultSet getTypesFromPoke(int idPoke) {

		try {

			Statement stmt = conn.createStatement();	
			String sql = "SELECT type.name FROM poketype, type "
					+ "WHERE poketype.idtype = type.idtype "
					+ "AND poketype.idpoke = " + idPoke;

			ResultSet rs = stmt.executeQuery(sql);
			
			return rs;

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

		return null;

	}
}
