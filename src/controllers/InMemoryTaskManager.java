package controllers;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import model.TaskType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import exceptions.ManagerValidateException;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private final Set<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));
    private int nextId = 1;

    @Override
    public void add(Task task) {
        task.setId(nextId++);
        if (task.getStartTime() != null && isValidate(task)) {
            throw new ManagerValidateException("Задача № " + task.getId() + " пересекается с другими задачами");
        }
        tasks.put(task.getId(), task);
        addPrioritizedTask(task);
    }

    @Override
    public void add(Epic epic) {
        epic.setId(nextId++);
        epics.put(epic.getId(), epic);
    }

    @Override
    public void add(Subtask subtask) {
        subtask.setId(nextId++);
        if (subtask.getStartTime() != null && isValidate(subtask)) {
            throw new ManagerValidateException("Подзадача № " + subtask.getId() + " пересекается с другими задачами");
        }
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            epic.addSubtask(subtask.getId());
        }
        addPrioritizedTask(subtask);
    }

    @Override
    public void update(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
        prioritizedTasks.removeIf(t -> t.getId() == task.getId());
        if (task.getStartTime() != null && isValidate(task)) {
            throw new ManagerValidateException("Задача № " + task.getId() + " пересекается с другими задачами");
        }
        addPrioritizedTask(task);
    }

    @Override
    public void update(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
        }
    }

    @Override
    public void update(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                updateEpicStatus(epic);
            }
        }
        prioritizedTasks.removeIf(t -> t.getId() == (subtask.getId()));
        if (subtask.getStartTime() != null && isValidate(subtask)) {
            throw new ManagerValidateException("Подзадача № " + subtask.getId() + " пересекается с другими задачами");
        }
        addPrioritizedTask(subtask);
    }

    @Override
    public void delete(Task task) {
        tasks.remove(task.getId());
        historyManager.remove(task.getId());
        prioritizedTasks.removeIf(t -> t.getId() == task.getId());
    }

    @Override
    public void delete(Epic epic) {
        ArrayList<Integer> subtaskIds = epic.subtaskIds;
        for (int subtaskId : subtaskIds) {
            subtasks.remove(subtaskId);
            historyManager.remove(subtaskId);
        }
        epics.remove(epic.getId());
        historyManager.remove(epic.getId());
    }

    @Override
    public void delete(Subtask subtask) {
        subtasks.remove(subtask.getId());
        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        if (epic != null) {
            epic.subtaskIds.remove(Integer.valueOf(subtask.getId()));
            updateEpicStatus(epic);
        }
        historyManager.remove(subtask.getId());
        prioritizedTasks.removeIf(task -> task.getId() == (subtask.getId()));
    }

    @Override
    public void updateEpicStatus(Epic epic) {
        if (epic.subtaskIds.isEmpty()) {
            epic.setStatus(Status.NEW);
        } else {
            boolean allDone = true;
            boolean allNew = true;

            for (int subtaskId : epic.subtaskIds) {
                Subtask subtask = subtasks.get(subtaskId);
                if (subtask != null) {
                    if (subtask.getStatus() != Status.DONE) {
                        allDone = false;
                    }
                    if (subtask.getStatus() != Status.NEW) {
                        allNew = false;
                    }
                }
            }

            if (allDone) {
                epic.setStatus(Status.DONE);
            } else if (allNew) {
                epic.setStatus(Status.NEW);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
        }
    }

    @Override
    public ArrayList<Subtask> getSubtasksByEpicId(int epicId) {
        ArrayList<Subtask> epicSubtasks = new ArrayList<>();
        Epic epic = epics.get(epicId);
        if (epic != null) {
            for (int subtaskId : epic.subtaskIds) {
                Subtask subtask = subtasks.get(subtaskId);
                if (subtask != null) {
                    epicSubtasks.add(subtask);
                }
            }
        }
        return epicSubtasks;
    }

    @Override
    public void deleteAllTasks() {
        for (int taskId : tasks.keySet()) {
            historyManager.remove(taskId);
            prioritizedTasks.removeIf(task -> task.getId() == taskId);
        }
        tasks.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.subtaskIds.clear();
            epic.setStatus(Status.NEW);
        }
        for (int subtaskId : subtasks.keySet()) {
            historyManager.remove(subtaskId);
            prioritizedTasks.removeIf(task -> task.getId() == subtaskId);
        }
        subtasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        for (int epicId : epics.keySet()) {
            historyManager.remove(epicId);
        }
        for (int subtaskId : subtasks.keySet()) {
            historyManager.remove(subtaskId);
        }
        subtasks.clear();
        epics.clear();
    }

    @Override
    public Subtask getSubtaskById(int subtaskId) {
        Subtask subtask = subtasks.get(subtaskId);
        if (subtask != null) {
            historyManager.addTask(subtask);
        }
        return subtasks.get(subtaskId);
    }

    @Override
    public Epic getEpicById(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            historyManager.addTask(epic);
        }
        return epics.get(epicId);
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.addTask(task);
        }
        return tasks.get(id);
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void updateTimeEpic(Epic epic) {
        List<Subtask> subtasks = getSubtasksByEpicId(epic.getId());
        if (subtasks.isEmpty()) {
            epic.setDuration(Duration.ZERO);
            return;
        }
        LocalDateTime startTime = subtasks.getFirst().getStartTime();
        LocalDateTime endTime = subtasks.getFirst().getEndTime();
        Duration totalDuration = Duration.ZERO;
        for (Subtask subtask : subtasks) {
            if (subtask.getStartTime().isBefore(startTime)) {
                startTime = subtask.getStartTime();
            }
            if (subtask.getEndTime().isAfter(endTime)) {
                endTime = subtask.getEndTime();
            }
            totalDuration = totalDuration.plus(subtask.getDuration());
        }
        epic.setStartTime(startTime);
        epic.setEndtime(endTime);
        epic.setDuration(totalDuration);
    }

    protected void loadTask(Task task) {
        if (task.getType().equals(TaskType.TASK)) {
            tasks.put(task.getId(), task);
        } else if (task.getType().equals(TaskType.EPIC)) {
            Epic epic = (Epic) task;
            epics.put(epic.getId(), epic);
        } else if (task.getType().equals(TaskType.SUBTASK)) {
            Subtask subtask = (Subtask) task;
            subtasks.put(subtask.getId(), subtask);
            Epic epic = epics.get(subtask.getEpicId());
            epic.addSubtask(subtask.getId());
        }
    }

    public List<Task> getPrioritizedTasks() {
        return prioritizedTasks.stream().toList();
    }

    private boolean isValidate(Task task) {
        return prioritizedTasks.stream()
                .anyMatch(t -> t.getStartTime().isBefore(task.getEndTime())
                        && task.getStartTime().isBefore(t.getEndTime()));
    }

    public void addPrioritizedTask(Task task) {
        if (task.getStartTime() == null) {
            return;
        }
        if (isValidate(task)) {
            throw new ManagerValidateException("Задача № " + task.getId() + " пересекается с другими задачами");
        }
        prioritizedTasks.add(task);
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }
}
