package com.studyload.studyload.controller;

import com.studyload.studyload.model.Task;
import com.studyload.studyload.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Get all tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // Add a new task
    @PostMapping
    public Task addTask(@RequestBody Task task) {
        return taskService.addTask(task);
    }

    // Edit an existing task
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody Task task) {

        return taskService.updateTask(id, task)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a task
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task deleted successfully");
    }

    // Get priority of a task
    @PostMapping("/priority")
    public String calculatePriority(@RequestBody Task task) {
        return taskService.calculatePriority(task);
    }
}