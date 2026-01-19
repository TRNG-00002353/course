package com.example.taskservice.controller;

import com.example.taskservice.dto.CreateTaskRequest;
import com.example.taskservice.dto.TaskResponse;
import com.example.taskservice.model.TaskStatus;
import com.example.taskservice.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskResponse> getAllTasks() {
        return taskService.getAllTasks().stream()
                .map(TaskResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        return TaskResponse.fromEntity(taskService.getTaskById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(@Valid @RequestBody CreateTaskRequest request) {
        return TaskResponse.fromEntity(
            taskService.createTask(request.title(), request.description(), request.assigneeId())
        );
    }

    @PatchMapping("/{id}/status")
    public TaskResponse updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        TaskStatus status = TaskStatus.valueOf(body.get("status"));
        return TaskResponse.fromEntity(taskService.updateTaskStatus(id, status));
    }

    @PatchMapping("/{id}/assign")
    public TaskResponse assignTask(@PathVariable Long id, @RequestBody Map<String, Long> body) {
        return TaskResponse.fromEntity(taskService.assignTask(id, body.get("assigneeId")));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @GetMapping("/assignee/{assigneeId}")
    public List<TaskResponse> getTasksByAssignee(@PathVariable Long assigneeId) {
        return taskService.getTasksByAssignee(assigneeId).stream()
                .map(TaskResponse::fromEntity)
                .toList();
    }
}
