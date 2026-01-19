package com.example.taskservice.service;

import com.example.taskservice.exception.TaskNotFoundException;
import com.example.taskservice.model.Task;
import com.example.taskservice.model.TaskStatus;
import com.example.taskservice.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(String title, String description, Long assigneeId) {
        log.info("Creating task: {} (assignee: {})", title, assigneeId);
        Task task = new Task(title, description);
        task.setAssigneeId(assigneeId);
        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public List<Task> getAllTasks() {
        return taskRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task updateTaskStatus(Long id, TaskStatus status) {
        Task task = getTaskById(id);
        task.setStatus(status);
        log.info("Task {} status updated to {}", id, status);
        return taskRepository.save(task);
    }

    public Task assignTask(Long id, Long assigneeId) {
        Task task = getTaskById(id);
        task.setAssigneeId(assigneeId);
        log.info("Task {} assigned to user {}", id, assigneeId);
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        taskRepository.delete(task);
        log.info("Task {} deleted", id);
    }

    @Transactional(readOnly = true)
    public List<Task> getTasksByAssignee(Long assigneeId) {
        return taskRepository.findByAssigneeId(assigneeId);
    }
}
