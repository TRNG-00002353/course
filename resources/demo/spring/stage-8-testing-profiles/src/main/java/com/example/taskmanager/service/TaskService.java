package com.example.taskmanager.service;

import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskStatus;
import com.example.taskmanager.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Business logic layer with SLF4J logging.
 *
 * NEW IN STAGE 8:
 * - Logger instance for structured logging
 * - Log messages at appropriate levels
 * - Parameterized log messages (no string concatenation)
 */
@Service
@Transactional
public class TaskService {

    // SLF4J Logger - one per class
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        log.info("TaskService initialized");
    }

    public Task createTask(String title, String description) {
        log.info("Creating new task with title: {}", title);

        Task task = new Task(title, description);
        Task saved = taskRepository.save(task);

        log.debug("Task created successfully with id: {}", saved.getId());
        return saved;
    }

    @Transactional(readOnly = true)
    public List<Task> getAllTasks() {
        log.debug("Fetching all tasks");
        List<Task> tasks = taskRepository.findAllByOrderByCreatedAtDesc();
        log.debug("Found {} tasks", tasks.size());
        return tasks;
    }

    @Transactional(readOnly = true)
    public Task getTaskById(Long id) {
        log.debug("Fetching task with id: {}", id);
        return taskRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Task not found with id: {}", id);
                    return new TaskNotFoundException(id);
                });
    }

    public Task updateTaskStatus(Long id, TaskStatus newStatus) {
        log.info("Updating task {} status to {}", id, newStatus);

        Task task = getTaskById(id);
        TaskStatus oldStatus = task.getStatus();
        task.setStatus(newStatus);
        Task updated = taskRepository.save(task);

        log.info("Task {} status changed: {} -> {}", id, oldStatus, newStatus);
        return updated;
    }

    public Task updateTask(Long id, String title, String description) {
        log.info("Updating task {}", id);

        Task task = getTaskById(id);

        if (title != null && !title.trim().isEmpty()) {
            log.debug("Updating title for task {}", id);
            task.setTitle(title);
        }
        if (description != null) {
            log.debug("Updating description for task {}", id);
            task.setDescription(description);
        }

        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        log.info("Deleting task with id: {}", id);

        Task task = getTaskById(id);
        taskRepository.delete(task);

        log.info("Task {} deleted successfully", id);
    }

    @Transactional(readOnly = true)
    public List<Task> getTasksByStatus(TaskStatus status) {
        log.debug("Fetching tasks with status: {}", status);
        List<Task> tasks = taskRepository.findByStatusOrderByCreatedAtDesc(status);
        log.debug("Found {} tasks with status {}", tasks.size(), status);
        return tasks;
    }
}
