package com.example.taskmanager.controller;

import com.example.taskmanager.dto.CreateTaskRequest;
import com.example.taskmanager.exception.GlobalExceptionHandler;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskStatus;
import com.example.taskmanager.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Controller tests using @WebMvcTest.
 *
 * @WebMvcTest:
 * - Loads only the web layer (no full context)
 * - Auto-configures MockMvc
 * - Services must be mocked with @MockBean
 */
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    @Test
    @DisplayName("GET /api/tasks - should return list of tasks")
    void getAllTasks_ReturnsTaskList() throws Exception {
        // Arrange
        Task task1 = new Task("Task 1", "Desc 1");
        task1.setId(1L);
        Task task2 = new Task("Task 2", "Desc 2");
        task2.setId(2L);
        when(taskService.getAllTasks()).thenReturn(List.of(task1, task2));

        // Act & Assert
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Task 1")))
                .andExpect(jsonPath("$[1].title", is("Task 2")));

        verify(taskService).getAllTasks();
    }

    @Test
    @DisplayName("GET /api/tasks/{id} - should return task when found")
    void getTaskById_ExistingId_ReturnsTask() throws Exception {
        // Arrange
        Task task = new Task("Test Task", "Description");
        task.setId(1L);
        when(taskService.getTaskById(1L)).thenReturn(task);

        // Act & Assert
        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test Task")))
                .andExpect(jsonPath("$.status", is("PENDING")));
    }

    @Test
    @DisplayName("GET /api/tasks/{id} - should return 404 when not found")
    void getTaskById_NonExistingId_Returns404() throws Exception {
        // Arrange
        when(taskService.getTaskById(999L)).thenThrow(new TaskNotFoundException(999L));

        // Act & Assert
        mockMvc.perform(get("/api/tasks/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("NOT_FOUND")));
    }

    @Test
    @DisplayName("POST /api/tasks - should create task and return 201")
    void createTask_ValidInput_ReturnsCreatedTask() throws Exception {
        // Arrange
        Task task = new Task("New Task", "Description");
        task.setId(1L);
        when(taskService.createTask(any(), any())).thenReturn(task);

        CreateTaskRequest request = new CreateTaskRequest("New Task", "Description");

        // Act & Assert
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("New Task")));

        verify(taskService).createTask("New Task", "Description");
    }

    @Test
    @DisplayName("POST /api/tasks - should return 400 when title is blank")
    void createTask_BlankTitle_Returns400() throws Exception {
        // Arrange
        CreateTaskRequest request = new CreateTaskRequest("", "Description");

        // Act & Assert
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("VALIDATION_ERROR")))
                .andExpect(jsonPath("$.details[0].field", is("title")));

        verify(taskService, never()).createTask(any(), any());
    }

    @Test
    @DisplayName("DELETE /api/tasks/{id} - should return 204 when deleted")
    void deleteTask_ExistingId_Returns204() throws Exception {
        // Arrange
        doNothing().when(taskService).deleteTask(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNoContent());

        verify(taskService).deleteTask(1L);
    }

    @Test
    @DisplayName("GET /api/tasks/status/{status} - should return filtered tasks")
    void getTasksByStatus_ValidStatus_ReturnsFilteredTasks() throws Exception {
        // Arrange
        Task task = new Task("Completed Task", "Desc");
        task.setId(1L);
        task.setStatus(TaskStatus.COMPLETED);
        when(taskService.getTasksByStatus(TaskStatus.COMPLETED)).thenReturn(List.of(task));

        // Act & Assert
        mockMvc.perform(get("/api/tasks/status/COMPLETED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].status", is("COMPLETED")));
    }
}
