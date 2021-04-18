package com.fav.shows.api.links;

import org.springframework.stereotype.Component;

@Component
public class TaskLinks {
  public static final String SHOWS = "/shows";
  public static final String FAVORITES = "/favorites";
  public static final String RESTART = "/restart";
  
  
  /*
   * public static final String SHOW = "/show/{id}"; public static final String
   * CREATE_SHOW = "/show"; public static final String UPDATE_SHOW = "/show";
   * public static final String DELETE_SHOW = "/show/{id}";
   */

  /*
   * public Link getCancelLink(Event event) { return
   * entityLinks.linkForSingleResource(event).slash(CANCEL_EVENT).withRel(
   * CANCEL_REL); }
   */
}
