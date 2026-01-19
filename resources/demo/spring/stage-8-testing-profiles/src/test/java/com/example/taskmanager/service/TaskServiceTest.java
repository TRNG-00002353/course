package com.example.taskmanager.service;

import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskStatus;
import com.example.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for TaskService.
 *
 * @ExtendWith(MockitoExtension.class) - Enables Mockito annotations
 * @Mock - Creates a mock instance
 * @InjectMocks - Injects mocks into the tested class
 *
 * NO Spring context is loaded - these are pure unit tests!
 */
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task sampleTask;

    @BeforeEach
    void setUp() {
        sampleTask = new Task("Test Task", "Test Description");
        sampleTask.setId(1L);
    }

    @Test
    @DisplayName("createTask - should save and return task")
    void createTask_ValidInput_ReturnsTask() {
        // Arrange
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);

        // Act
        Task result = taskService.createTask("Test Task", "Test Description");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Test Task");
        assertThat(result.getStatus()).isEqualTo(TaskStatus.PENDING);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("getTaskById - should return task when found")
    void getTaskById_ExistingId_ReturnsTask() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));

        // Act
        Task result = taskService.getTaskById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(taskRepository).findById(1L);
    }

    @Test
    @DisplayName("getTaskById - should throw exception when not found")
    void getTaskById_NonExistingId_ThrowsException() {
        // Arrange
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> taskService.getTaskById(999L))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("999");

        verify(taskRepository).findById(999L);
    }

    @Test
    @DisplayName("getAllTasks - should return list of tasks")
    void getAllTasks_ReturnsTaskList() {
        // Arrange
        Task task2 = new Task("Task 2", "Description 2");
        when(taskRepository.findAllByOrderByCreatedAtDesc())
                .thenReturn(List.of(sampleTask, task2));

        // Act
        List<Task> results = taskService.getAllTasks();

        // Assert
        assertThat(results).hasSize(2);
        verify(taskRepository).findAllByOrderByCreatedAtDesc();
    }

    @Test
    @DisplayName("updateTaskStatus - should update and return task")
    void updateTaskStatus_ValidInput_ReturnsUpdatedTask() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);

        // Act
        Task result = taskService.updateTaskStatus(1L, TaskStatus.COMPLETED);

        // Assert
        assertThat(result.getStatus()).isEqualTo(TaskStatus.COMPLETED);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    @DisplayName("deleteTask - should delete existing task")
    void deleteTask_ExistingId_DeletesTask() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
        doNothing().when(taskRepository).delete(sampleTask);

        // Act
        taskService.deleteTask(1L);

        // Assert
        verify(taskRepository).delete(sampleTask);
    }

    @Test
    @DisplayName("deleteTask - should throw exception when not found")
    void deleteTask_NonExistingId_ThrowsException() {
        // Arrange
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> taskService.deleteTask(999L))
                .isInstanceOf(TaskNotFoundException.class);

        verify(taskRepository, never()).delete(any());
    }
}
