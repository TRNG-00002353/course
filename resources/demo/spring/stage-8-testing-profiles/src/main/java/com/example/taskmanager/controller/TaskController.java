package com.example.taskmanager.controller;

import com.example.taskmanager.dto.*;
import com.example.taskmanager.model.TaskStatus;
import com.example.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller with request logging.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskResponse> getAllTasks() {
        log.info("GET /api/tasks - Fetching all tasks");
        return taskService.getAllTasks()
                .stream()
                .map(TaskResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        log.info("GET /api/tasks/{} - Fetching task", id);
        return TaskResponse.fromEntity(taskService.getTaskById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(@Valid @RequestBody CreateTaskRequest request) {
        log.info("POST /api/tasks - Creating task: {}", request.title());
        return TaskResponse.fromEntity(
                taskService.createTask(request.title(), request.description())
        );
    }

    @PutMapping("/{id}")
    public TaskResponse updateTask(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskRequest request) {
        log.info("PUT /api/tasks/{} - Updating task", id);
        return TaskResponse.fromEntity(
                taskService.updateTask(id, request.title(), request.description())
        );
    }

    @PatchMapping("/{id}/status")
    public TaskResponse updateTaskStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStatusRequest request) {
        log.info("PATCH /api/tasks/{}/status - Updating status to {}", id, request.status());
        return TaskResponse.fromEntity(
                taskService.updateTaskStatus(id, request.status())
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        log.info("DELETE /api/tasks/{} - Deleting task", id);
        taskService.deleteTask(id);
    }

    @GetMapping("/status/{status}")
    public List<TaskResponse> getTasksByStatus(@PathVariable TaskStatus status) {
        log.info("GET /api/tasks/status/{} - Fetching tasks by status", status);
        return taskService.getTasksByStatus(status)
                .stream()
                .map(TaskResponse::fromEntity)
                .toList();
    }
}
