package com.fav.shows.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fav.shows.api.entity.Show;
import com.fav.shows.api.util.CSVMocker;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ShowService {

	public List<Show> getShows() {
		List<Show> list = new ArrayList<Show>();
		CSVMocker c = new CSVMocker();
		
		list = c.mockShowsCSV("netflix_titles.csv", 2, ",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
		
		System.out.println("Something works");
		return list;
	}

}