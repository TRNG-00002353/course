package com.example.taskmanager.repository;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Repository tests using @DataJpaTest.
 *
 * @DataJpaTest:
 * - Configures an embedded database (H2)
 * - Scans for @Entity classes
 * - Configures Spring Data JPA repositories
 * - Each test runs in a transaction that's rolled back
 */
@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    @DisplayName("save - should persist task and generate ID")
    void save_ValidTask_PersistsAndReturnsWithId() {
        // Arrange
        Task task = new Task("Test Task", "Description");

        // Act
        Task saved = taskRepository.save(task);

        // Assert
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("Test Task");
        assertThat(saved.getStatus()).isEqualTo(TaskStatus.PENDING);
    }

    @Test
    @DisplayName("findById - should return task when exists")
    void findById_ExistingId_ReturnsTask() {
        // Arrange
        Task task = new Task("Test Task", "Description");
        Task saved = taskRepository.save(task);

        // Act
        Optional<Task> found = taskRepository.findById(saved.getId());

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Test Task");
    }

    @Test
    @DisplayName("findById - should return empty when not exists")
    void findById_NonExistingId_ReturnsEmpty() {
        // Act
        Optional<Task> found = taskRepository.findById(999L);

        // Assert
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("findByStatus - should return matching tasks")
    void findByStatus_ExistingStatus_ReturnsMatchingTasks() {
        // Arrange
        Task pending1 = new Task("Pending 1", "Desc");
        Task pending2 = new Task("Pending 2", "Desc");
        Task completed = new Task("Completed", "Desc");
        completed.setStatus(TaskStatus.COMPLETED);

        taskRepository.save(pending1);
        taskRepository.save(pending2);
        taskRepository.save(completed);

        // Act
        List<Task> pendingTasks = taskRepository.findByStatus(TaskStatus.PENDING);
        List<Task> completedTasks = taskRepository.findByStatus(TaskStatus.COMPLETED);

        // Assert
        assertThat(pendingTasks).hasSize(2);
        assertThat(completedTasks).hasSize(1);
    }

    @Test
    @DisplayName("findAllByOrderByCreatedAtDesc - should return ordered tasks")
    void findAllByOrderByCreatedAtDesc_MultipleTasks_ReturnsOrdered() {
        // Arrange
        Task task1 = new Task("First", "Desc");
        Task task2 = new Task("Second", "Desc");
        taskRepository.save(task1);
        taskRepository.save(task2);

        // Act
        List<Task> tasks = taskRepository.findAllByOrderByCreatedAtDesc();

        // Assert
        assertThat(tasks).hasSize(2);
        // Most recent first
        assertThat(tasks.get(0).getTitle()).isEqualTo("Second");
    }

    @Test
    @DisplayName("delete - should remove task")
    void delete_ExistingTask_RemovesTask() {
        // Arrange
        Task task = new Task("To Delete", "Desc");
        Task saved = taskRepository.save(task);
        Long id = saved.getId();

        // Act
        taskRepository.delete(saved);

        // Assert
        Optional<Task> found = taskRepository.findById(id);
        assertThat(found).isEmpty();
    }
}
