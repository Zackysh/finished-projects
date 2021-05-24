package com.fav.shows.api.controller;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fav.shows.api.entity.Show;
import com.fav.shows.api.links.TaskLinks;
import com.fav.shows.api.service.ShowService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This class is the main controller of this API.
 * It contains resources and handlers calls.
 * 
 * @author AdriGB
 *
 */
@CrossOrigin(origins = "http://localhost:3000")
@RepositoryRestController
@RequestMapping("/api/")
public class ShowController {

  /**
   * ShowService interacts with netflix.csv and favorites.json (temporal file) to
   * serve shows and favorites to this controller requests.
   */
  @Autowired
  private ShowService showService;

  /**
   * This GET mapping returns shows extracted from netflix.csv parsed into JSON
   * format. This process involves reading a CSV with java libraries before serve
   * its content.
   * 
   * @return shows In JSON format (response body)
   * @throws FileNotFoundException request will fail if netflix.csv doesn't exists
   */
  @GetMapping(path = TaskLinks.SHOWS, produces = "application/json")
  @ResponseBody
  public String getShows() throws FileNotFoundException {
    List<Show> showList = showService.getShows();
    try {
      GsonBuilder gsonBuilder = new GsonBuilder();
      Gson gson = gsonBuilder.create();
      String JSONObject = gson.toJson(showList);
      System.out.println("GET request to /shows completed successfully.");
      return JSONObject;
    } catch (Exception e) {
      e.fillInStackTrace();
      System.out.println("GET request to /shows failed.");
      return null;
    }
  }

  /**
   * This GET mapping returns favorites (which are stored both in "data-base" as
   * "JSON".
   * 
   * This process involves "writing" and "reading" those favorites into a
   * temporary file on system default temporary folder with java libraries
   * before serve them.
   * 
   * @return favorites In JSON format (response body)
   */
  @GetMapping(path = TaskLinks.FAVORITES, produces = "application/json")
  @ResponseBody
  public String getFavs() {
    List<Show> favoritesList = showService.getFavorites();
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();
    String JSONObject = gson.toJson(favoritesList);
    
    System.out.println("GET request to /favorites completed successfully.");
    return JSONObject;
  }

  /**
   * This POST mapping receives new favorites in JSON format. Those new favorites
   * are directly stored on "data-base" overriding currently stored ones.
   * 
   * This process involves "writing" and "reading" those favorites into a
   * temporary file with java libraries before save them.
   * 
   * @param newFavorites JSON with new favorites to be stored
   */
  @PostMapping(path = TaskLinks.FAVORITES, consumes = "application/json", produces = "application/json")
  @ResponseBody
  public void updateFavs(@RequestBody List<Show> newFavorites) {
    showService.updateFavorites(newFavorites);
    System.out.println("POST request to /favorites completed successfully.");
  }

}
