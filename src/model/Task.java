package model;

import java.util.Objects;


public class Task {
    private String nameTask;
    private Status status;
    private String description;
    private int id;

    public Task(String nameTask, Status status, String description) {
        this.nameTask = nameTask;
        this.status = status;
        this.description = description;
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
}
