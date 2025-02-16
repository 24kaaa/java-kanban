package controllers;

import exceptions.ManagerValidateException;
import model.Status;
import model.Epic;
import model.Task;
import model.Subtask;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest  extends TaskManagerTest<InMemoryTaskManager> {
    @Override
    public InMemoryTaskManager createTaskManager() {
        return new InMemoryTaskManager();
    }

    @Test
    void shouldEpicStatusBeInProgress() {
        Epic epic = new Epic("Epic Title", "Epic Description",LocalDateTime.now(),
                Duration.ofMinutes(30));
        taskManager.add(epic);

        Subtask subtask1 = new Subtask("Subtask 1", "Description", epic.getId(), Status.IN_PROGRESS,LocalDateTime.now(), Duration.ofMinutes(30));
        Subtask subtask2 = new Subtask("Subtask 2", "Description", epic.getId(), Status.DONE,  LocalDateTime.now().plus(Duration.ofMinutes(30)), Duration.ofMinutes(30));

        taskManager.add(subtask1);
        taskManager.add(subtask2);

        taskManager.updateEpicStatus(epic);

        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    @Test
    void shouldEpicStatusBeDone() {
        Epic epic = new Epic("Epic Title", "Epic Description",LocalDateTime.now(),
                Duration.ofMinutes(30));
        taskManager.add(epic);

        Subtask subtask1 = new Subtask("Subtask 1", "Description", epic.getId(), Status.DONE,LocalDateTime.now(), Duration.ofMinutes(30));
        Subtask subtask2 = new Subtask("Subtask 2", "Description", epic.getId(), Status.DONE,LocalDateTime.now().plus(Duration.ofMinutes(30)), Duration.ofMinutes(30));

        taskManager.add(subtask1);
        taskManager.add(subtask2);

        taskManager.updateEpicStatus(epic);

        assertEquals(Status.DONE, epic.getStatus());
    }

    @Test
    void shouldAddPrioritizedTaskWhenNoIntersections() {
        Task task = new Task(
                "Test task",
                Status.NEW,
                "Test task description",
                LocalDateTime.of(2024, Month.MARCH, 27, 10, 0),
                Duration.ofMinutes(30));
        assertDoesNotThrow(() -> taskManager.addPrioritizedTask(task));
    }

    @Test
    void shouldThrowExceptionWhenAddingIntersectingTask() {
        Task task = new Task("Test task",
                Status.NEW,
                "Test task description 1",
                LocalDateTime.of(2024, Month.MARCH, 27, 10, 0),
                Duration.ofMinutes(30));
        taskManager.add(task);
        Task task2 = new Task("Test task 2",
                Status.NEW,
                "Test task description 2",
                LocalDateTime.of(2024, Month.MARCH, 27, 10, 15),
                Duration.ofMinutes(30));
        assertThrows(ManagerValidateException.class, () -> taskManager.add(task2));
        assertEquals(1, taskManager.getAllTasks().size());
    }

    @Test
    void shouldReturnPrioritizedTasksInCorrectOrder() {
        Task task = new Task("Test task",
                Status.NEW,
                "Test description task",
                LocalDateTime.of(2024, Month.MARCH, 27, 10, 0),
                Duration.ofMinutes(30));
        Task task2 = new Task("Test task task 2",
                Status.NEW,
                "description 2",
                LocalDateTime.of(2024, Month.MARCH, 27, 9, 0),
                Duration.ofMinutes(30));
        taskManager.add(task);
        taskManager.add(task2);

        List<Task> prioritizedTasks = taskManager.getPrioritizedTasks();

        assertEquals(2, prioritizedTasks.size());
        assertEquals(task2, prioritizedTasks.get(0));
        assertEquals(task, prioritizedTasks.get(1));
    }

    @Test
    void shouldNotIncludeTasksWithNullStartTime() {
        Task task = new Task("Test task",
                Status.NEW,
                "Test description 1",
                null,
                Duration.ofMinutes(30));
        Task task2 = new Task("Test task 2",
                Status.NEW,
                "Test description 2",
                LocalDateTime.of(2024, Month.MARCH, 27, 9, 0),
                Duration.ofMinutes(30));
        taskManager.add(task);
        taskManager.add(task2);

        List<Task> prioritizedTasks = taskManager.getPrioritizedTasks();

        assertEquals(1, prioritizedTasks.size());
        assertEquals(task2, prioritizedTasks.getFirst());
    }
}
