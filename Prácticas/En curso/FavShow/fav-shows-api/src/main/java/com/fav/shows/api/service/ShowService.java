package com.fav.shows.api.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import com.google.gson.JsonIOException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ShowService {

  private CSVMocker c;

  @Autowired
  private FavoritesRepository repository;

  private final String FAVORITES_PATH = "./src/main/resources/favorites.json";

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

  public List<Show> getFavorites() {
    Iterable<FavoritesJson> it = repository.findAll();
    FavoritesJson a = it.iterator().next();
    String json = a.getJson();
    File tempFile;
    try {
      tempFile = writeFileToTemp(json); // Write with java
      return getFavoritesFromJSON(tempFile); // Read with gson
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Read favorites.json and return its value as List<Show>.
   * 
   * @return favorites As List<Show>
   */
  private List<Show> getFavoritesFromJSON(File json) {
    Gson gson = new Gson();
    c = new CSVMocker();
    Show[] favorites;
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(json)));
      favorites = gson.fromJson(br, Show[].class); // gson reads from BufferedReader
      if (favorites != null)
        return Arrays.asList(favorites);
      return null;
    } catch (FileNotFoundException e) {
      e.fillInStackTrace();
      return null;
    }
  }

  public boolean updateFavorites(List<Show> newFavorites) {
    if (newFavorites == null)
      return false;
    setNewFavorites(newFavorites);
    return true;
  }

  private void setNewFavorites(List<Show> newFavorites) {
    Gson gson = new Gson();
    try {
      // Generate JSON
      String strNewFavs = gson.toJson(newFavorites);
      // Write json into temporal file
      writeFileToTemp(strNewFavs); // demostrative
      // Send readed JSON to repository
      FavoritesJson newFavs = new FavoritesJson(strNewFavs);
      repository.save(newFavs);

    } catch (JsonIOException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private File writeFileToTemp(String toWrite) throws IOException {
    File tmpFile = File.createTempFile("favorites.json", ".tmp");
    FileWriter writer = new FileWriter(tmpFile);
    writer.write(toWrite);
    writer.close();

    return tmpFile;
  }

}
