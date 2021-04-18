package com.fav.shows.api.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fav.shows.api.entity.Show;
import com.fav.shows.api.util.CSVMocker;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ShowService {

  private CSVMocker c;

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

  /**
   * Read favorites.json and return its value as List<Show>.
   * 
   * @return favorites As List<Show>
   */
  public List<Show> getFavorites() {
    Show[] favorites;
    try {
      c = new CSVMocker();
      BufferedReader br = c.getBReaderResource("favorites.json");
      Gson gson = new Gson();
      favorites = gson.fromJson(br, Show[].class);
      if (favorites != null)
        return Arrays.asList(favorites);
      return null;
    } catch (FileNotFoundException fe) {
      fe.fillInStackTrace();
      System.out.println(fe.fillInStackTrace());
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
      FileWriter fileW = new FileWriter(FAVORITES_PATH);
      String strNewFavs = gson.toJson(newFavorites);
      fileW.write(strNewFavs);
      fileW.close(); // auto flush
    } catch (JsonIOException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
