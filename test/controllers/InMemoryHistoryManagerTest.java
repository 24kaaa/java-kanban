package controllers;

import model.Status;
import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class InMemoryHistoryManagerTest {

    private HistoryManager imhm;

    @BeforeEach
    public void setUp() {
        imhm = Managers.getDefaultHistory();

    }

    @Test
    public void shouldAddAndGetHistory() {
        Task task1 = new Task("Задача 1", Status.NEW, "Описание задачи 1");
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        Subtask subtask1 = new Subtask("Подзадача 1",
                "Описание подзадачи 1", Status.NEW, "Описание", epic1.getId());
        imhm.addTask(task1);
        imhm.addTask(epic1);
        imhm.addTask(subtask1);


        List<Task> history = imhm.getHistory();

        assertEquals(3, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(epic1, history.get(1));
        assertEquals(subtask1, history.get(2));
    }

    @Test
    public void shouldRemoveTaskIfItIsViewedAgain() {
        Task task1 = new Task("Задача 1", Status.NEW, "Описание задачи 1");
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        Subtask subtask1 = new Subtask("Подзадача 1",
                "Описание подзадачи 1", Status.NEW, "Описание", epic1.getId());
        imhm.addTask(task1);
        imhm.addTask(epic1);
        imhm.addTask(subtask1);
        Task task2 = new Task("Задача 2", Status.NEW, "Описание задачи 2");
        imhm.addTask(task2);
        List<Task> history = imhm.getHistory();
        assertEquals(3, history.size());
        assertEquals(task2, history.get(2));
    }
}
