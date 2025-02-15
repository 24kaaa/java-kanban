package model;

import java.util.Objects;
import java.time.Duration;
import java.time.LocalDateTime;


public class Task {
    private String nameTask;
    private Status status;
    private String description;
    private int id;
    private Duration duration;
    private LocalDateTime startTime;

    public Task(String nameTask, String description, int id, Status status, LocalDateTime startTime, Duration duration) {
        this.nameTask = nameTask;
        this.startTime = startTime;
        this.duration = duration;
        this.status = status;
        this.id = id;
        this.description = description;
    }

    public Task(String nameTask, Status status, String description, LocalDateTime startTime, Duration duration) {
        this.nameTask = nameTask;
        this.status = status;
        this.description = description;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Task(String nameTask, String description, int id, Status status) {
        this.nameTask = nameTask;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    public String getNameTask() {
        return nameTask;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public TaskType getType() {
        return TaskType.TASK;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "nameTask='" + nameTask + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", duration=" + duration +
                ", startTime=" + startTime +
                '}';
    }
}
