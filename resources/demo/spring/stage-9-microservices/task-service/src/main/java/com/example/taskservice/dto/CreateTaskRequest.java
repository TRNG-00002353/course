package com.example.taskservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTaskRequest(
    @NotBlank(message = "Title is required")
    @Size(max = 100)
    String title,

    String description,

    Long assigneeId
) {}
