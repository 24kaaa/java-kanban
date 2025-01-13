package model;

import java.util.ArrayList;

public class Epic extends Task {
    public ArrayList<Integer> subtaskIds;

    public Epic(String nameTask, String description) {
        super(nameTask, Status.NEW, description);
        this.subtaskIds = new ArrayList<>();
    }
    public Epic(String name, String description, int id) {
        super(name, description, id, Status.NEW);
    }

    public void addSubtask(int subtaskId) {
        subtaskIds.add(subtaskId);
    }
}
