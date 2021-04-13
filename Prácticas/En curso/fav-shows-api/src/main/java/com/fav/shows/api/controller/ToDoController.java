package com.fav.shows.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.fav.shows.api.dto.TaskDTO;
import com.fav.shows.api.entity.Show;
import com.fav.shows.api.links.TaskLinks;
import com.fav.shows.api.service.ShowService;

@RequestMapping("/todo/")
public class ToDoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ToDoController.class);

    @Autowired
    private ShowService taskService;

    @GetMapping(path = TaskLinks.TASKS)
    public ResponseEntity<?> getTasks() {
        List<Show> todoList = taskService.getShows();
        return ResponseEntity.ok(todoList);
    }

    @GetMapping(path = TaskLinks.TASK)
    public ResponseEntity<?> getTask(@PathVariable("id") String taskId) {
        try {
            LOGGER.info("TasksController::: " + taskId);
            Show task = taskService.getShow(taskId);

            return ResponseEntity.ok(task);
        }catch (RuntimeException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Resource Not Found", exc);
        }
    }

    @PostMapping(path = TaskLinks.CREATE_TASK)
    public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDTO) {
        LOGGER.info("TasksController: " + taskDTO);
        Show task = taskService.saveTask(taskDTO);

        return ResponseEntity.ok(task);
    }

    @PutMapping(path = TaskLinks.UPDATE_TASK)
    public ResponseEntity<?> updateTask(@RequestBody TaskDTO taskDTO) {
        LOGGER.info("TasksController: " + taskDTO);
        Show task = taskService.updateTask(taskDTO);

        return ResponseEntity.ok(task);
    }

    @DeleteMapping(path = TaskLinks.DELETE_TASK)
    public ResponseEntity<?> deleteTask(@PathVariable("id") String taskId) {
        LOGGER.info("TasksController: " + taskId);
        String result = taskService.deleteTask(taskId);

        return ResponseEntity.ok(result);
    }
}