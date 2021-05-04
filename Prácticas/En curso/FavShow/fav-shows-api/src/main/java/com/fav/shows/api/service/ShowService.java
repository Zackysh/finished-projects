package com.fav.shows.api.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fav.shows.api.entity.FavoritesJson;
import com.fav.shows.api.entity.FavoritesRepository;
import com.fav.shows.api.entity.Show;
import com.fav.shows.api.util.CSVMocker;
import com.google.gson.Gson;

  
/**
 * This service provide necessary tools to write, read and store JSON files
 * between remote client, files and data-base.
 * 
 * @author AdriGB
 */
@Service
public class ShowService {

  private CSVMocker c;

  @Autowired
  private FavoritesRepository repository;

  /**
   * Map CSV Shows and return them as List<Show>.
   * 
   * @return allShows As List<Show>
   */
  public List<Show> getShows() {
    c = new CSVMocker();
    List<Show> shows = new ArrayList<Show>();
    shows = c.mockShowsCSV("netflix_titles.csv", 2, ",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
    return shows;
  }

  /**
   * Method that fetches favorites JSON from database. - Write fetched JSON in a
   * temporary JSON (FileWriter).
   * 
   * - Read temporary JSON (BufferedReader) to extract favorites.
   * - Returns favorites as an ArrayList<Show>.
   * 
   * @return favorites ArrayList<Show>
   */
  public List<Show> getFavorites() {
    // Fetch JSON from database
    // JSON is stored always on registry with id = 0
    Optional<FavoritesJson> op = repository.findById(0);

    FavoritesJson favs = null;
    if (!op.isPresent())
      return new ArrayList<Show>(); // empty list if there's no JSON

    favs = op.get();
    String favsJSON = favs.getJson(); // Here we have desired JSON
    File tempFile;
    try {
      // Write fetched favorites in temporary JSON
      tempFile = writeJSONToTempFile(favsJSON, "favorites");
      // Read and return favorites from temporary JSON
      return readFavoritesFromTemp(tempFile);
    } catch (IOException e) {
      e.printStackTrace();
      return new ArrayList<Show>(); // empty list JSON couldn't be read
    }
  }

  /**
   * Method that sets new favorites both in database and temporary JSON file.
   * Read comments for deeper understanding.
   * 
   * @param newFavs List which will be stored
   * @returns true if favorites were updated
   * @returns false if favorites weren't updated
   */
  public boolean updateFavorites(List<Show> newFavs) {
    // verify data integrity
    if (newFavs == null)
      return false;

    try {
      // Generate JSON from List
      Gson gson = new Gson();
      String newFavsJSON = gson.toJson(newFavs);
      // Write JSON into temporal file
      File tempJSON = writeJSONToTempFile(newFavsJSON, "favorites");
      // Read stored temporary file
      newFavsJSON = readFavsJSONFromTemp(tempJSON);
      // Store read JSON on data-base to achieve persistence
      // because Spring isn't very suitable for file persistence
      repository.updateFavorites(0, newFavsJSON);
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Read given file (that should contain some shows as JSON) and return its
   * content as List<Show>.
   * 
   * @return favorites As List<Show>
   */
  private List<Show> readFavoritesFromTemp(File json) {
    Gson gson = new Gson();
    Show[] favorites;
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(json)));
      favorites = gson.fromJson(br, Show[].class); // GSON reads from BufferedReader and mock to Show[]
      if (favorites != null)
        return Arrays.asList(favorites);
      return null;
    } catch (FileNotFoundException e) {
      e.fillInStackTrace();
      return null;
    }
  }

  /**
   * Read given file (that should contain some shows as JSON) and return its
   * content as String (JSON).
   * 
   * @return favorites As String (JSON)
   */
  private String readFavsJSONFromTemp(File json) {
    String favorites = "";
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(json)));
      String line = br.readLine();
      while (line != null) {
        favorites += line;
        line = br.readLine();
      }
      br.close();
      return favorites;
    } catch (IOException e) {
      e.fillInStackTrace();
      return null;
    }
  }

  /**
   * Method which writes given String (JSON) into a new file on client system
   * default temporary folder.
   * $TMPDIR on MAC and /TEMP on windows.
   * 
   * @param JSONText JSON text to write
   * @return new/updated temporary file
   * @throws IOException
   */
  private File writeJSONToTempFile(String JSONText, String fileName) throws IOException {
    File tmpFile = File.createTempFile(fileName, ".json");
    FileWriter writer = new FileWriter(tmpFile);
    writer.write(JSONText);
    writer.close();

    return tmpFile;
  }

}
