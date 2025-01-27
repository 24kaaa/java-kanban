package controllers;

import model.Status;
import model.Epic;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest {
    private TaskManager taskManager;
    private Task task1;
    private Task task2;
    private Epic epic1;
    private Epic epic2;

    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefault();
        task1 = new Task("Задача 1", Status.NEW, "Описание задачи 1");
        task2 = new Task("Задача 2", Status.NEW, "Описание задачи 2");
        epic1 = new Epic("Эпик 1", "Описание эпика 1");
        epic2 = new Epic("Эпик 2", "Описание эпика 2");
    }

    @Test
    void shouldRemoveAllTasksAndRemoveFromHistory() {
        taskManager.add(task1);
        taskManager.add(task2);
        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task2.getId());
        taskManager.deleteAllTasks();

        List<Task> tasks = taskManager.getAllTasks();
        List<Task> historyTasks = taskManager.getHistory();

        assertTrue(tasks.isEmpty(), "Список задач не должен быть пустым");
        assertTrue(historyTasks.isEmpty(), "История задач не должна быть пустой");
    }
}
