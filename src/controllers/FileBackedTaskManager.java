package controllers;

import model.Epic;
import model.Subtask;
import model.Task;
import model.Status;
import java.io.*;
import exceptions.ManagerSaveException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.TaskType;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
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

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    private void save() {
        List<String> lines = new ArrayList<>();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            writer.write("id, type, name, status, description, startTime, duration, epic" + "\n");
            for (Task task : getAllTasks()) {
                writer.write(toString(task) + "\n");
            }
            for (Epic epic : getAllEpics()) {
                writer.write(toString(epic) + "\n");
            }
            for (Subtask subtask : getAllSubtasks()) {
                writer.write(toString(subtask) + "\n");
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Произошла ошибка при сохранении данных в файл");
        }
    }

    private static String toString(Task task) {
        String[] toJoin = {
                Integer.toString(task.getId()),
                task.getType().toString(),
                task.getNameTask(),
                task.getStatus().toString(),
                task.getDescription(),
                task.getStartTime().toString(),
                task.getDuration().toString()
        };
        return String.join(",", toJoin);
    }

    private static Task fromString(String value) {
        String[] parts = value.split(",");
        try {
            int id = Integer.parseInt(parts[0]);
            TaskType type = TaskType.valueOf(parts[1]);
            String name = parts[2];
            Status status = Status.valueOf(parts[3]);
            String description = parts[4];
            LocalDateTime startTime = null;
            if (!parts[5].equals("null")) {
                try {
                    startTime = LocalDateTime.parse(parts[5]);
                } catch (java.time.format.DateTimeParseException e) {
                    throw new ManagerSaveException("Ошибка при парсинге даты");
                }
            }
            Duration duration = Duration.ofMinutes(Long.parseLong(parts[6]));

            if (type.equals(TaskType.TASK)) {
                return new Task(name, description, id, status, startTime, duration);

            } else if (type.equals(TaskType.EPIC)) {
                return new Epic(name, description, id, status, startTime, duration);

            } else {
                int epicId = Integer.parseInt(parts[7]);
                return new Subtask(name, description, epicId, status, startTime, duration);
            }
        } catch (Exception e) {
            throw new ManagerSaveException("Ошибка при парсинге строки");
        }
    }


    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);
        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            String line = reader.readLine();
            int maxId = 1;
            while (reader.ready()) {
                line = reader.readLine();
                Task task = fromString(line);
                if (task != null) {
                    taskManager.loadTask(task);
                    if (task.getId() > maxId) {
                        maxId = task.getId();
                    }
                }
            }
            taskManager.setNextId(maxId + 1);
        } catch (IOException e) {
            throw new ManagerSaveException("Произошла ошибка во время загрузки из файла");
        }
        return taskManager;
    }
}
