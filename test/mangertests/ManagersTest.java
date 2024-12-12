package mangertests;
import controllers.Managers;
import org.junit.jupiter.api.Test;
import controllers.TaskManager;
import controllers.HistoryManager;
import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {
    @Test
    void getDefault_returnsInitializedTaskManager() {
        TaskManager manager1 = Managers.getDefault();
        TaskManager manager2 = Managers.getDefault();
        assertNotNull(manager1);
        assertNotNull(manager2);
        assertNotEquals(manager1, manager2);
    }

    @Test
    void getDefaultHistory_returnsInitializedHistoryManager() {
        HistoryManager manager1 = Managers.getDefaultHistory();
        HistoryManager manager2 = Managers.getDefaultHistory();
        assertNotNull(manager1);
        assertNotNull(manager2);
        assertNotEquals(manager1, manager2);
    }
}