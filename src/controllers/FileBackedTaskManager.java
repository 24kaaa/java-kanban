package controllers;

import model.Epic;
import model.Subtask;
import model.Task;
import model.Status;
import java.io.*;


public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
        loadFromFile();
    }

    @Override
    public void add(Task task) {
        super.add(task);
        save();
    }

    @Override
    public void add(Epic epic) {
        super.add(epic);
        save();
    }

    @Override
    public void add(Subtask subtask) {
        super.add(subtask);
        save();
    }

    @Override
    public void update(Task task) {
        super.update(task);
        save();
    }

    @Override
    public void update(Epic epic) {
        super.update(epic);
        save();
    }

    @Override
    public void update(Subtask subtask) {
        super.update(subtask);
        save();
    }

    @Override
    public void delete(Task task) {
        super.delete(task);
        save();
    }

    @Override
    public void delete(Epic epic) {
        super.delete(epic);
        save();
    }

    @Override
    public void delete(Subtask subtask) {
        super.delete(subtask);
        save();
    }

    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            for (Task task : getAllTasks()) {
                writer.write(task.toString());
                writer.newLine();
            }

            for (Epic epic : getAllEpics()) {
                writer.write(epic.toString());
                writer.newLine();
            }

            for (Subtask subtask : getAllSubtasks()) {
                writer.write(subtask.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void loadFromFile() {
        if (!file.exists() || file.length() == 0) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    try {
                        switch (parts[0]) {
                            case "TASK":
                                if (parts.length == 5) {
                                    String name = parts[1];
                                    String description = parts[2];
                                    int id = Integer.parseInt(parts[3]);
                                    Status status = Status.valueOf(parts[4]);

                                    Task task = new Task(name, status, description);
                                    task.setId(id);
                                    add(task);
                                }
                                break;
                            case "EPIC":
                                if (parts.length >= 4) {
                                    Epic epic = new Epic(parts[2], parts[3]);
                                    epic.setId(Integer.parseInt(parts[1]));
                                    add(epic);
                                }
                                break;
                            case "SUBTASK":
                                if (parts.length == 6) {
                                    int subtaskId = Integer.parseInt(parts[1]);
                                    String subtaskName = parts[2];
                                    int epicId = Integer.parseInt(parts[5]);
                                    Status subtaskStatus = Status.valueOf(parts[4]);
                                    String subtaskDescription = parts[3];

                                    Subtask subtask = new Subtask(subtaskName, subtaskDescription, epicId, subtaskStatus);
                                    subtask.setId(subtaskId);
                                    add(subtask);
                                }
                                break;
                            default:
                                System.out.println("Неизвестный тип: " + parts[0]);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка преобразования числа: " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.err.println("Ошибка значения: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
