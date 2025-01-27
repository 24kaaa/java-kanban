package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class EpicTest {
    private Epic epic1;
    private Epic epic2;

    @BeforeEach
    void setUp() {
        epic1 = new Epic("Эпик 1", "Описание эпика 1");
        epic2 = new Epic("Эпик 2", "Описание эпика 2");
    }

    @Test
    void shouldReturnTrueIfIdsAreEquals() {
        assertEquals(epic1, epic2);
    }
}