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
public class FavoritesJson {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer show_id;

  private String json;

  public FavoritesJson(String json) {
    this.json = json;
  }
  
  public String getJson() {
    return json;
  }

  @Override
  public String toString() {
    return "FavoritesJson [show_id=" + show_id + ", json=" + json + "]";
  }

}
