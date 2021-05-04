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

//  String json = "[{\"show_id\":\"s1248\",\"type\":\"Movie\",\"title\":\"Cats \\u0026 Dogs: The Revenge of Kitty Galore\",\"director\":\"Brad Peyton\",\"cast\":\"\\\"James Marsden, Nick Nolte, Christina Applegate, Katt Williams, Bette Midler, Neil Patrick Harris, Sean Hayes, Wallace Shawn, Roger Moore, Joe Pantoliano, Michael Clarke Duncan, Chris O\\u0027Donnell, Jack McBrayer, Kiernan Shipka\\\"\",\"country\":\"\\\"United States, Australia\\\"\",\"date_added\":\"\\\"February 1, 2019\\\"\",\"release_year\":\"2010\",\"rating\":\"PG\",\"duration\":\"82 min\",\"listed_in\":\"\\\"Children \\u0026 Family Movies, Comedies\\\"\",\"description\":\"Cats and dogs must set aside their differences and merge animal instincts when Kitty Galore decides to claw her way to global domination\"},{\"show_id\":\"s667\",\"type\":\"Movie\",\"title\":\"Babel\",\"director\":\"Alejandro G. IÃ±Ã¡rritu\",\"cast\":\"\\\"Brad Pitt, Cate Blanchett, Gael GarcÃ­a Bernal, Koji Yakusho, Adriana Barraza, Rinko Kikuchi, Said Tarchani, Boubker Ait El Caid, Elle Fanning, Nathan Gamble, Mohamed Akhzam\\\"\",\"country\":\"\\\"France, United States, Mexico\\\"\",\"date_added\":\"\\\"January 1, 2019\\\"\",\"release_year\":\"2006\",\"rating\":\"R\",\"duration\":\"143 min\",\"listed_in\":\"\\\"Dramas, Independent Movies\\\"\",\"description\":\"\\\"When an American couple vacationing in Morocco falls victim to a random act of violence, a series of events unfolds across four countries.\"},{\"show_id\":\"s233\",\"type\":\"Movie\",\"title\":\"A Stoning in Fulham County\",\"director\":\"Larry Elikann\",\"cast\":\"\\\"Ken Olin, Jill Eikenberry, Maureen Mueller, Gregg Henry, Nicholas Pryor, Noble Willingham, Peter Michael Goetz, Ron Perlman, Theodore Bikel, Olivia Burnette, Bill Allen, Brad Pitt\\\"\",\"country\":\"United States\",\"date_added\":\"\\\"October 1, 2011\\\"\",\"release_year\":\"1988\",\"rating\":\"TV-14\",\"duration\":\"95 min\",\"listed_in\":\"Dramas\",\"description\":\"\\\"After reckless teens kill an Amish child, a prosecutor attempts to bring the youths to justice despite the condemnation he faces from the community.\"},{\"show_id\":\"s89\",\"type\":\"Movie\",\"title\":\"2307: Winter\\u0027s Dream\",\"director\":\"Joey Curtis\",\"cast\":\"\\\"Paul Sidhu, Arielle Holmes, Branden Coles, Kelcey Watson, Anne-Solenne Hatte, Brad Potts, Timothy Lee DePriest, Fernando Argosino, Duchess Dukes, Harwood Gordon, Stormi Henley\\\"\",\"country\":\"United States\",\"date_added\":\"\\\"March 1, 2018\\\"\",\"release_year\":\"2016\",\"rating\":\"TV-MA\",\"duration\":\"101 min\",\"listed_in\":\"\\\"Action \\u0026 Adventure, Independent Movies, Sci-Fi \\u0026 Fantasy\\\"\",\"description\":\"\\\"In the frozen tundra of a futuristic Arizona where humans have been forced underground, a soldier hunts the bioengineered leader of a rebellion.\"},{\"show_id\":\"s1072\",\"type\":\"Movie\",\"title\":\"Brad Paisley\\u0027s Comedy Rodeo\",\"director\":\"Jay Chapman\",\"cast\":\"\\\"Brad Paisley, Nate Bargatze, John Heffron, Jon Reep, Sarah Tiana, Mike E. Winfield\\\"\",\"country\":\"United States\",\"date_added\":\"\\\"August 15, 2017\\\"\",\"release_year\":\"2017\",\"rating\":\"TV-MA\",\"duration\":\"63 min\",\"listed_in\":\"Stand-Up Comedy\",\"description\":\"\\\"Country music star Brad Paisley hosts a night of music and laughs with comics Nate Bargatze, John Heffron, Jon Reep, Sarah Tiana and Mike E. Winfield.\"}]";
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
    System.out.println("JSON Favorites: " + JSONObject);
    if (JSONObject == null)
      return "[]";
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
