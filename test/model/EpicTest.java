package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
class EpicTest {
    private Epic epic1;
    private Epic epic2;

    @BeforeEach
    void setUp() {
        epic1 = new Epic("Эпик 1", "Описание эпика 1", LocalDateTime.now()
                , Duration.ofMinutes(30));
        epic2 = new Epic("Эпик 2", "Описание эпика 2",LocalDateTime.now()
                , Duration.ofMinutes(30));
    }

    @Test
    void shouldReturnTrueIfIdsAreEquals() {
        assertEquals(epic1, epic2);
    }
}