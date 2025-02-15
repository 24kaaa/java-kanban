package controllers;

import exceptions.ManagerSaveException;
import org.junit.jupiter.api.Test;
import model.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;



class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {

    @Override
    public FileBackedTaskManager createTaskManager() {
        return new FileBackedTaskManager(new File("test.csv"));
    }

    @Test
    void shouldThrowExceptionWhenFileNotExists() {
        File file = new File("nonexistent_file.csv");
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);
        assertThrows(ManagerSaveException.class, () -> taskManager.loadFromFile(file));
    }

    @Test
    void shouldThrowExceptionWhenFileHasIncorrectData() throws IOException {
        File file = new File("incorrect_data.csv");
        Files.write(Paths.get(file.getPath()), "id,type,name,status,description\n1,TASK,Test task,NEW,Test task description,incorrect_date".getBytes());
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);
        assertThrows(ManagerSaveException.class, () -> taskManager.loadFromFile(file));
        Files.delete(Paths.get(file.getPath()));
    }


    @Test
    void shouldLoadTasksFromFile() throws IOException {
        File file = new File("test_load.csv");
        String content = """
                id,type,name,status,description,startTime,duration,epic
                1,TASK,Test task,NEW,Test task description,2024-03-27T10:00,30,
                2,EPIC,Test epic,NEW,Test epic description,null,0,
                3,SUBTASK,Test subtask,NEW,Test subtask description,2024-03-27T10:30,15,2
                """;
        Files.write(Paths.get(file.getPath()), content.getBytes());

        FileBackedTaskManager taskManager = FileBackedTaskManager.loadFromFile(file);

        assertEquals(1, taskManager.getAllTasks().size());
        assertEquals(1, taskManager.getAllEpics().size());
        assertEquals(1, taskManager.getAllSubtasks().size());

        Task task = taskManager.getTaskById(1);
        assertEquals("Test task", task.getNameTask());

        Files.delete(Paths.get(file.getPath()));
    }
}

