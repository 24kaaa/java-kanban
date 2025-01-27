package controllers;

import model.Epic;
import model.Subtask;
import model.Task;
import model.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {
    private FileBackedTaskManager taskManager;
    private final String testFilePath = "test_tasks.csv";

    @BeforeEach
    void setUp() {
        taskManager = new FileBackedTaskManager(new File(testFilePath));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(testFilePath));
    }

    @Test
    void testAddAndSaveTasks() {
        Task task1 = new Task("Task 1", Status.NEW, "Description 1");
        task1.setId(1);
        taskManager.add(task1);

        Epic epic1 = new Epic("Epic 1", "Epic Description 1");
        epic1.setId(2);
        taskManager.add(epic1);

        Subtask subtask1 = new Subtask("Subtask 1", "Subtask Description 1", 2, Status.NEW);
        subtask1.setId(3);
        taskManager.add(subtask1);

        taskManager.save();

        FileBackedTaskManager newTaskManager = new FileBackedTaskManager(new File(testFilePath));

        assertEquals(1, newTaskManager.getAllTasks().size());
        assertEquals("Task 1", newTaskManager.getAllTasks().get(0).getNameTask());
        assertEquals(Status.NEW, newTaskManager.getAllTasks().get(0).getStatus());

        assertEquals(1, newTaskManager.getAllEpics().size());
        assertEquals("Epic 1", newTaskManager.getAllEpics().get(0).getNameTask());

        assertEquals(1, newTaskManager.getAllSubtasks().size());
        assertEquals("Subtask 1", newTaskManager.getAllSubtasks().get(0).getNameTask());
    }

    @Test
    void testLoadFromFileWithEmptyFile() {
        FileBackedTaskManager newTaskManager = new FileBackedTaskManager(new File(testFilePath));

        assertEquals(0, newTaskManager.getAllTasks().size());
        assertEquals(0, newTaskManager.getAllEpics().size());
        assertEquals(0, newTaskManager.getAllSubtasks().size());
    }

    @Test
    void testLoadFromFileWithNonExistentFile() {
        FileBackedTaskManager newTaskManager = new FileBackedTaskManager(new File("non_existent_file.csv"));

        assertEquals(0, newTaskManager.getAllTasks().size());
        assertEquals(0, newTaskManager.getAllEpics().size());
        assertEquals(0, newTaskManager.getAllSubtasks().size());
    }
}