package managertests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import controllers.Managers;
import model.Status;
import controllers.InMemoryHistoryManager;
import controllers.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private TaskManager taskManager;
    private InMemoryHistoryManager imhm;

    @BeforeEach
    public void setUp() {
        taskManager = Managers.getDefault();
        imhm = Managers.getDefaultHistory();
    }

    @Test
    public void shouldAddAndGetHistory() {
        Task task1 = new Task("Задача 1", Status.NEW, "Описание задачи 1");
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        Subtask subtask1 = new Subtask("Подзадача 1",
                "Описание подзадачи 1", Status.NEW, "Описание", epic1.getId());
        imhm.addTask(task1);
        imhm.addTask(epic1);
        imhm.addTask(subtask1);
        assertEquals(3, imhm.getHistory().size());
        assertTrue(imhm.getHistory().contains(task1));
        assertTrue(imhm.getHistory().contains(epic1));
        assertTrue(imhm.getHistory().contains(subtask1));

    }

    @Test
    void shouldRemoveFirstTaskWhenAddNewTaskAndListAreFull() {
        Task task11 = new Task("Задача 11", Status.NEW, "Описание задачи 11");
        for (int i = 0; i < 10; i++) {
            imhm.addTask(new Task("Задача " + i, Status.NEW, "Описание"));
        }
        imhm.addTask(task11);

        assertEquals(10, imhm.getHistory().size());
        assertEquals(task11, imhm.getHistory().get(9));

    }
}