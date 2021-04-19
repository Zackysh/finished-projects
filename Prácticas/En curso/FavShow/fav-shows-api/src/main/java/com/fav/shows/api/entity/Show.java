package com.fav.shows.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Show {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private String show_id;

  private String type, title, director, cast, country, date_added, release_year, rating, duration, listed_in,
      description;

  public Show(String show_id, String type, String title, String director, String cast, String country, String date_added,
      String release_year, String rating, String duration, String listed_in, String description) {
    this.show_id = show_id;
    this.type = type;
    this.title = title;
    this.director = director;
    this.cast = cast;
    this.country = country;
    this.date_added = date_added;
    this.release_year = release_year;
    this.rating = rating;
    this.duration = duration;
    this.listed_in = listed_in;
    this.description = description;
  }

  @Override
  public String toString() {
    return ("Show [show_id=" + show_id + ", type=" + type + ", title=" + title + ", director=" + director + ", cast="
        + cast + ", country=" + country + ", date_added=" + date_added + ", release_year=" + release_year + ", rating="
        + rating + ", duration=" + duration + ", listed_in=" + listed_in + ", description=" + description + "]");
  }

}
