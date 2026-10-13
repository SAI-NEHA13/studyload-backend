package com.studyload.studyload.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.studyload.studyload.model.Task;
import com.studyload.studyload.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // =========================
    // GET ALL TASKS
    // =========================

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // =========================
    // ADD NEW TASK
    // =========================

    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    // =========================
    // DELETE TASK
    // =========================

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    // =========================
    // CALCULATE PRIORITY
    // =========================

    public String calculatePriority(Task task) {

        int score = task.getPriorityScore();

        if (score >= 70) {
            return "HIGH";
        } else if (score >= 40) {
            return "MEDIUM";
        } else {
            return "LOW";
        }
    }

    // =========================
    // GET PRIORITY SCORE
    // =========================

    public int getPriorityScore(Task task) {
        return task.getPriorityScore();
    }

    // Update an existing task
public Optional<Task> updateTask(Long id, Task updatedTask) {
    return taskRepository.findById(id).map(existingTask -> {
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setType(updatedTask.getType());
        existingTask.setDeadline(updatedTask.getDeadline());
        existingTask.setEstimatedHours(
                updatedTask.getEstimatedHours()
        );
        existingTask.setDifficulty(updatedTask.getDifficulty());

        return taskRepository.save(existingTask);
    });
}
}