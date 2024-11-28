package model;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String nameTask, String description, Status status, String epicDescription, int epicId) {
        super(nameTask, status, description);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}
