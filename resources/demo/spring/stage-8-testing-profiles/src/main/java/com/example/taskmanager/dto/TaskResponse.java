package com.example.taskmanager.dto;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponse(
    Long id,
    String title,
    String description,
    TaskStatus status,
    LocalDateTime createdAt
) {
    public static TaskResponse fromEntity(Task task) {
        return new TaskResponse(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getStatus(),
            task.getCreatedAt()
        );
    }
}
