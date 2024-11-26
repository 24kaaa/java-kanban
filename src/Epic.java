import java.util.ArrayList;

public class Epic extends Task {
    public ArrayList<Integer> subtaskIds;

    public Epic(String nameTask, String description) {
        super(nameTask, Status.NEW, description);
        this.subtaskIds = new ArrayList<>();
    }

    public void addSubtask(int subtaskId) {
        subtaskIds.add(subtaskId);
    }
}
