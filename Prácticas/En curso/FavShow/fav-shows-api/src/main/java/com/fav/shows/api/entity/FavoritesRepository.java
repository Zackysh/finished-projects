package com.fav.shows.api.entity;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * This repository is a connector between entity's and data-base.
 * 
 * @author AdriGB
 */
public interface FavoritesRepository extends CrudRepository<FavoritesJson, Integer> {

  /**
   * This method provides a way to update existing JSON registry's on data-base.
   * 
   * @param favs_id Identifyer
   * @param json JSON text
   * @returns number of affected rows
   * @returns -1 if anything went wrong
   */
  @Transactional
  @Modifying
  @Query("UPDATE FavoritesJson f SET f.json = :json WHERE f.favs_id = :favs_id")
  int updateFavorites(@Param("favs_id") int favs_id, @Param("json") String json);

}