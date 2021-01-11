package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that extends AbstractDAO and is oriented to extract
 * information from PokeDB.Pokemon to this application
 * 
 * @author AdriGB
 *
 */
public class DAO_Pokemon  extends AbstractDAO {

	/**
	 * Method which extracts a resultSet from PokeDB
	 * that will contain a list with all existing Pokemons.
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

}