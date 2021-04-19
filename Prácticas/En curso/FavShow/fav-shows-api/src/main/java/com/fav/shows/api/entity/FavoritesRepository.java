package com.fav.shows.api.entity;

import org.springframework.data.repository.CrudRepository;


public interface FavoritesRepository extends CrudRepository<FavoritesJson, Integer> {

}