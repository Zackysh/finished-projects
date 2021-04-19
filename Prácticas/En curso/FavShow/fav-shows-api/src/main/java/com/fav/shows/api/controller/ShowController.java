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

import com.fav.shows.api.DemoApplication;
import com.fav.shows.api.entity.FavoritesJson;
import com.fav.shows.api.entity.Show;
import com.fav.shows.api.links.TaskLinks;
import com.fav.shows.api.service.ShowService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;

@Slf4j
@RepositoryRestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ShowController {
  @Autowired
  private ShowService showService;

  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping(path = TaskLinks.SHOWS, produces = "application/json")
  @ResponseBody
  public String getShows() throws FileNotFoundException {
    List<Show> showList = showService.getShows();
    try {
      GsonBuilder gsonBuilder = new GsonBuilder();
      Gson gson = gsonBuilder.create();
      String JSONObject = gson.toJson(showList);
      System.out.println(showService.updateFavorites(null));
      return JSONObject;
    } catch (Exception e) {
      e.fillInStackTrace();
      return null;
    }
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping(path = TaskLinks.FAVORITES, produces = "application/json")
  @ResponseBody
  public String getFavs() {
    List<Show> favoritesList = showService.getFavorites();
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();
    String JSONObject = gson.toJson(favoritesList);
    return JSONObject;
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @PostMapping(path = TaskLinks.FAVORITES, consumes = "application/json", produces = "application/json")
  @ResponseBody
  public String updateFavs(@RequestBody List<Show> newFavorites) {
    showService.updateFavorites(newFavorites);
    return "All right";
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @PostMapping(path = TaskLinks.RESTART)
  @ResponseBody
  public void restart() {
    System.out.println("RESTARTING......");
    DemoApplication.restart();
  }

}
