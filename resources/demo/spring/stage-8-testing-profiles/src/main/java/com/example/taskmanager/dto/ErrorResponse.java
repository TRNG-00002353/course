package com.example.taskmanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
    String error,
    String message,
    List<FieldError> details,
    LocalDateTime timestamp
) {
    public ErrorResponse(String error, String message) {
        this(error, message, null, LocalDateTime.now());
    }

    public ErrorResponse(String error, String message, List<FieldError> details) {
        this(error, message, details, LocalDateTime.now());
    }

    public record FieldError(String field, String message) {}
}
