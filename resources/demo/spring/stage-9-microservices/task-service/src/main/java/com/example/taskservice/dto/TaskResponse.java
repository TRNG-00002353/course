package com.example.taskservice.dto;

import com.example.taskservice.model.Task;
import com.example.taskservice.model.TaskStatus;
import java.time.LocalDateTime;

public record TaskResponse(
    Long id,
    String title,
    String description,
    TaskStatus status,
    Long assigneeId,
    LocalDateTime createdAt
) {
    public static TaskResponse fromEntity(Task task) {
        return new TaskResponse(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getStatus(),
            task.getAssigneeId(),
            task.getCreatedAt()
        );
    }
}
