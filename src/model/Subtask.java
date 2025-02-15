package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String nameTask, String description, Status status, String epicDescription, int epicId,
                   LocalDateTime startTime, Duration duration) {
        super(nameTask, status, description, startTime, duration);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, int epicId, Status status, LocalDateTime startTime, Duration duration ) {
        super(name, status, description, startTime, duration);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public TaskType getType() {
        return TaskType.SUBTASK;
    }
}
