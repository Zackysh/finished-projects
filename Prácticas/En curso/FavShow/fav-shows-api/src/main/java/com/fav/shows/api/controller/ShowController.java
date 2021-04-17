package com.fav.shows.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fav.shows.api.entity.Show;
import com.fav.shows.api.links.TaskLinks;
import com.fav.shows.api.service.ShowService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RepositoryRestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ShowController {

	@Autowired
	private ShowService taskService;

	
	
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(path = TaskLinks.SHOWS, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String getShows() {
		List<Show> showList = taskService.getShows();
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		String JSONObject = gson.toJson(showList);
		return JSONObject;
	}

}