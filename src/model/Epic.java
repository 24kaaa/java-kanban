package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    public ArrayList<Integer> subtaskIds;
    private LocalDateTime endtime;

    public Epic(String nameTask, String description, LocalDateTime startTime, Duration duration) {
        super(nameTask, Status.NEW, description, startTime, duration);
        this.subtaskIds = new ArrayList<>();
        this.endtime = super.getEndTime();
    }

    public Epic(String name, String description, int id,Status status, LocalDateTime startTime, Duration duration) {
        super(name, description, id, status, startTime, duration);
        this.subtaskIds = new ArrayList<>();
    }

    public void addSubtask(int subtaskId) {
        subtaskIds.add(subtaskId);
    }

    public void setEndtime(LocalDateTime endTime) {
        this.endtime = endTime;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endtime;
    }

    @Override
    public String toString() {
        return TaskType.EPIC + "," + getId() + "," + getNameTask() + "," + getDescription();
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }
}
