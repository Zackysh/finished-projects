package com.fav.shows.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * This class behave as an entity to store raw JSON data into
 * this project auto generated data-base.
 * 
 * @author AdriGB
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoritesJson {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer favs_id;

  @Column(columnDefinition = "JSON")
  @Lob
  private String json;

  public FavoritesJson(String json) {
    this.json = json;
  }

  public FavoritesJson() {
  }

  public String getJson() {
    return json;
  }

  @Override
  public String toString() {
    return "FavoritesJson [show_id=" + favs_id + ", json=" + json + "]";
  }

}
