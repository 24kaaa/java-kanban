package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    @Test
    void shouldReturnTrueIfIdsAreEquals() {
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        Subtask subtask1 = new Subtask("Подзадача 1",
                "Описание подзадачи 1", Status.NEW, "Описание", 0);
        Subtask subtask2 = new Subtask("Подзадача 2",
                "Описание подзадачи 2", Status.NEW, "Описание", 0);
        assertEquals(subtask1, subtask2);
    }
}