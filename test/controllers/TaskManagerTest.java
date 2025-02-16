package controllers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import model.Status;
import controllers.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.Epic;
import model.Subtask;
import model.Task;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest <T extends TaskManager>{
    protected T taskManager;

    public abstract T createTaskManager();

    @BeforeEach
    void setUp() {
        taskManager = createTaskManager();
    }

    protected Task addTask() {
        return new Task("Test task name", Status.NEW, "Test task description",  LocalDateTime.now()
                , Duration.ofMinutes(30));
    }

    protected Epic addEpic() {
        return new Epic("Test epic name", "Test epic description",  LocalDateTime.now(),
                Duration.ofMinutes(30));
    }

    protected Subtask addSubtask(Epic epic) {
        return new Subtask("Test subtask name", "Test subtask description", epic.getId(), Status.NEW,
                LocalDateTime.now(), Duration.ofMinutes(30));
    }

    @Test
    public void shouldAddTask() {
        Task task = addTask();
        taskManager.add(task);
        assertEquals(task, taskManager.getTaskById(task.getId()));
        assertEquals(Status.NEW, taskManager.getTaskById(task.getId()).getStatus());
    }

    @Test
    public void shouldAddEpic() {
        Epic epic = addEpic();
        taskManager.add(epic);
        assertEquals(epic, taskManager.getEpicById(epic.getId()));
        assertEquals(Status.NEW, taskManager.getEpicById(epic.getId()).getStatus());
    }

    @Test
    public void shouldAddSubtask() {
        Epic epic = addEpic();
        taskManager.add(epic);
        Subtask subtask = addSubtask(epic);
        taskManager.add(subtask);
        assertEquals(subtask, taskManager.getSubtaskById(subtask.getId()));
        assertEquals(Status.NEW, taskManager.getSubtaskById(subtask.getId()).getStatus());
        assertEquals(epic.getId(), subtask.getEpicId());
    }

    @Test
    public void shouldUpdateTask() {
        Task task = addTask();
        taskManager.add(task);
        taskManager.getTaskById(task.getId()).setStatus(Status.DONE);
        taskManager.update(task);
        assertEquals(Status.DONE, taskManager.getTaskById(task.getId()).getStatus());
        assertEquals(Status.DONE, task.getStatus());
    }

    @Test
    public void shouldUpdateEpic() {
        Epic epic = addEpic();
        taskManager.add(epic);
        taskManager.getEpicById(epic.getId()).setStatus(Status.DONE);
        taskManager.update(epic);
        assertEquals(Status.DONE, taskManager.getEpicById(epic.getId()).getStatus());
        assertEquals(Status.DONE, epic.getStatus());
    }

    @Test
    public void ShouldUpdateSubtask() {
        Epic epic = addEpic();
        taskManager.add(epic);
        Subtask subtask = addSubtask(epic);
        taskManager.add(subtask);
        taskManager.getSubtaskById(subtask.getId()).setStatus(Status.DONE);
        taskManager.update(subtask);
        assertEquals(Status.DONE, taskManager.getSubtaskById(subtask.getId()).getStatus());
        assertEquals(Status.DONE, subtask.getStatus());
    }

    @Test
    public void shouldGetTaskById() {
        Task task = addTask();
        taskManager.add(task);
        Task savedTask = taskManager.getTaskById(task.getId());
        assertEquals(task, savedTask);
        assertNotNull(savedTask);
    }

    @Test
    public void shouldGetEpicById() {
        Epic epic = addEpic();
        taskManager.add(epic);
        Epic savedEpic = taskManager.getEpicById(epic.getId());
        assertEquals(epic, savedEpic);
        assertNotNull(savedEpic);
    }

    @Test
    public void shouldGetSubtaskById() {
        Epic epic = addEpic();
        taskManager.add(epic);
        Subtask subtask = addSubtask(epic);
        taskManager.add(subtask);
        Subtask savedSubtask = taskManager.getSubtaskById(subtask.getId());
        assertEquals(subtask, savedSubtask);
        assertNotNull(savedSubtask);
    }

    @Test
    public void shouldGetAllTasks() {
        Task task = addTask();
        Task task2 = new Task("Test task2 name",Status.NEW, "Test task2 description",
                LocalDateTime.now().plus(Duration.ofMinutes(30)),
                Duration.ofMinutes(30));
        taskManager.add(task);
        taskManager.add(task2);
        List<Task> tasks = taskManager.getAllTasks();
        assertNotNull(tasks);
        assertEquals(2, tasks.size());
    }

    @Test
    public void shouldGetAllEpics() {
        Epic epic = addEpic();
        Epic epic2 = new Epic("Test epic2 name", "Test epic2 description", LocalDateTime.now(),
                Duration.ofMinutes(30));
        taskManager.add(epic);
        taskManager.add(epic2);
        List<Epic> epics = taskManager.getAllEpics();
        assertNotNull(epics);
        assertEquals(2, epics.size());
    }

    @Test
    public void shouldGetAllSubtasks() {
        Epic epic = addEpic();
        taskManager.add(epic);
        Subtask subtask = addSubtask(epic);
        Subtask subtask2 = new Subtask("Test subtask2 name", "Test subtask2 description",epic.getId(), Status.NEW,
                LocalDateTime.now().plus(Duration.ofMinutes(30)), Duration.ofMinutes(30));
        taskManager.add(subtask);
        taskManager.add(subtask2);
        List<Subtask> subtasks = taskManager.getAllSubtasks();
        assertNotNull(subtasks);
        assertEquals(2, subtasks.size());
    }

    @Test
    public void shouldRemoveTaskById() {
        Task task = addTask();
        taskManager.add(task);
        taskManager.delete(task);
        assertNull(taskManager.getTaskById(task.getId()));
    }

    @Test
    public void shouldRemoveEpicById() {
        Epic epic = addEpic();
        taskManager.add(epic);
        taskManager.delete(epic);
        assertNull(taskManager.getEpicById(epic.getId()));
    }

    @Test
    public void shouldRemoveSubtaskById() {
        Epic epic = addEpic();
        taskManager.add(epic);
        Subtask subtask = addSubtask(epic);
        taskManager.add(subtask);
        taskManager.delete(subtask);
        assertNull(taskManager.getSubtaskById(subtask.getId()));
    }

    @Test
    public void shouldRemoveAllTasks() {
        Task task = addTask();
        Task task2 = new Task("Test task2 name",Status.NEW, "Test task2 description",
                LocalDateTime.now().plus(Duration.ofMinutes(30)),
                Duration.ofMinutes(30));
        taskManager.add(task);
        taskManager.add(task2);
        taskManager.deleteAllTasks();
        assertEquals(0, taskManager.getAllTasks().size());
    }

    @Test
    public void shouldRemoveAllEpics() {
        Epic epic = addEpic();
        Epic epic2 = new Epic("Test epic2 name", "Test epic2 description", LocalDateTime.now(),
                Duration.ofMinutes(30));
        Subtask subtask = addSubtask(epic);
        taskManager.add(subtask);
        taskManager.add(epic);
        taskManager.add(epic2);
        taskManager.deleteAllEpics();
        assertEquals(0, taskManager.getAllEpics().size());
        assertEquals(0, taskManager.getAllSubtasks().size());
    }

    @Test
    public void shouldRemoveAllSubtasks() {
        Epic epic = addEpic();
        taskManager.add(epic);
        Subtask subtask = addSubtask(epic);
        Subtask subtask2 = new Subtask("Test subtask2 name", "Test subtask2 description", epic.getId(), Status.NEW,
                LocalDateTime.now().plus(Duration.ofMinutes(30)), Duration.ofMinutes(30));
        taskManager.add(subtask);
        taskManager.add(subtask2);
        taskManager.deleteAllSubtasks();
        assertEquals(0, taskManager.getAllSubtasks().size());
    }

    @Test
    public void shouldGetSubtasksForEpic() {
        Epic epic = addEpic();
        taskManager.add(epic);
        Subtask subtask = addSubtask(epic);
        Subtask subtask2 = new Subtask("Test subtask2 name", "Test subtask2 description",epic.getId(), Status.NEW,
                LocalDateTime.now().plus(Duration.ofMinutes(30)), Duration.ofMinutes(30));
        taskManager.add(subtask);
        taskManager.add(subtask2);
        List<Subtask> subtasks = taskManager.getSubtasksByEpicId(epic.getId());
        assertNotNull(subtasks);
        assertEquals(2, subtasks.size());
    }

}
