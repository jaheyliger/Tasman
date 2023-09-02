package com.jayvon.Tasman.controllers;

import com.jayvon.Tasman.entities.Task;
import com.jayvon.Tasman.requests.CreateTaskInput;
import com.jayvon.Tasman.requests.UpdateTaskInput;
import com.jayvon.Tasman.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {
    public TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/tasks")
    public ResponseEntity<Task> createTask(@RequestBody CreateTaskInput createTaskInput) {
        Task taskCreated = taskService.create(createTaskInput.toTask());

        return new ResponseEntity<>(taskCreated, HttpStatus.CREATED);
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> allTasks() {
        List<Task> tasks = taskService.findAll();

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<Task> oneTask(@PathVariable int id) {
        Optional<Task> optionalTask = taskService.findById(id);

        if(optionalTask.isPresent()) {
            return new ResponseEntity<>(optionalTask.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/task/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody UpdateTaskInput updateTaskInput) {
        Optional<Task> optionalTask = taskService.findById(id);

        if (optionalTask.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Task taskToUpdate = optionalTask.get();

        taskToUpdate.setStatus(updateTaskInput.status());
        taskToUpdate.setDueDate(updateTaskInput.dueDate());

        Task taskUpdated = taskService.update(taskToUpdate);

        return new ResponseEntity<>(taskUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable int id) {
        taskService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
