package model;

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
}
