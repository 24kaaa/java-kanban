package model;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    @Test
    void shouldReturnTrueIfIdsAreEquals() {
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1", LocalDateTime.now()
                , Duration.ofMinutes(30));
        Subtask subtask1 = new Subtask("Подзадача 1",
                "Описание подзадачи 1", 0, Status.NEW,
                LocalDateTime.now(), Duration.ofMinutes(30));
        Subtask subtask2 = new Subtask("Подзадача 2",
                "Описание подзадачи 2",0, Status.NEW,
                LocalDateTime.now(), Duration.ofMinutes(30));
        assertEquals(subtask1, subtask2);
    }
}