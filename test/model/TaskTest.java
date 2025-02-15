package model;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    void shouldReturnTrueIfIdsAreEquals() {
        Task task1 = new Task("Задача 1", Status.NEW, "Описание задачи 1", LocalDateTime.now()
                , Duration.ofMinutes(30));
        Task task2 = new Task("Задача 2", Status.NEW, "Описание задачи 2",LocalDateTime.now()
                , Duration.ofMinutes(30));
        assertEquals(task1, task2);
    }
}