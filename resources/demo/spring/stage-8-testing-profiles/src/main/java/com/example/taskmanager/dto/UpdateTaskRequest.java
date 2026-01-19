package com.example.taskmanager.dto;

import jakarta.validation.constraints.Size;

public record UpdateTaskRequest(
    @Size(max = 100, message = "Title must not exceed 100 characters")
    String title,

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    String description
) {}
