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

    private HistoryManager historyManager;

    @BeforeEach
    public void setUp() {
        historyManager = Managers.getDefaultHistory();
    }

    @Test
    public void shouldAddAndGetHistory() {
        Task task1 = new Task("Задача 1","Описание задачи 1", 1, Status.NEW);
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1", 2);
        Subtask subtask1 = new Subtask("Подзадача 1",
                "Описание подзадачи 1", Status.NEW, "Описание", 2);
        historyManager.addTask(task1);
        historyManager.addTask(epic1);
        historyManager.addTask(subtask1);


        List<Task> history = historyManager.getHistory();

        assertEquals(3, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(epic1, history.get(1));
        assertEquals(subtask1, history.get(2));
    }

    @Test
    public void shouldRemoveTaskIfItIsViewedAgain() {
        Task task1 = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1", 2);
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", Status.NEW, "Описание", 2);

        historyManager.addTask(task1);
        historyManager.addTask(epic1);
        historyManager.addTask(subtask1);

        Task task2 = new Task("Задача 2", "Описание задачи 2", 3, Status.NEW);
        historyManager.addTask(task2);


        List<Task> history = historyManager.getHistory();
        assertEquals(4, history.size());
        assertEquals(task2, history.get(3));


        historyManager.addTask(task1);


        history = historyManager.getHistory();
        assertEquals(4, history.size());
        assertEquals(task1, history.get(3));
        assertEquals(task2, history.get(2));
        assertEquals(subtask1, history.get(1));
        assertEquals(epic1, history.get(0));
    }
}
