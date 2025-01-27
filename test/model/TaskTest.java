package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    void shouldReturnTrueIfIdsAreEquals() {
        Task task1 = new Task("Задача 1", Status.NEW, "Описание задачи 1");
        Task task2 = new Task("Задача 2", Status.NEW, "Описание задачи 2");
        assertEquals(task1, task2);
    }
}