package com.fav.shows.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fav.shows.api.dto.TaskDTO;
import com.fav.shows.api.entity.Show;

@Service
public class ShowService {

    public List<Show> getShows() {
        return null; // TODO devolver shows
    }

    public Show getShow(String showId) {
        return null;
    }

    public Show saveTask(TaskDTO taskDTO) {
        return null;
    }

    public Show updateTask(TaskDTO taskDTO) {
        return null;
    }

    public String deleteTask(String taskId) {
        return null;
    }
}