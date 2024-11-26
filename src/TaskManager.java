import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private int nextId = 1;

    public void add(Task task) {
        task.setId(nextId++);
        tasks.put(task.getId(), task);
    }

    public void add(Epic epic) {
        epic.setId(nextId++);
        epics.put(epic.getId(), epic);
    }

    public void add(Subtask subtask) {
        subtask.setId(nextId++);
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            epic.addSubtask(subtask.getId());
        }
    }

    public void update(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    public void update(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
        }
    }

    public void update(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
        }
    }

    public void delete(Task task) {
        tasks.remove(task.getId());
    }

    public void delete(Epic epic) {
        epics.remove(epic.getId());
    }

    public void delete(Subtask subtask) {
        subtasks.remove(subtask.getId());
    }

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

    public void deleteAllTasks() {
        tasks.clear();
    }
    public void deleteAllSubtasks() {
        subtasks.clear();
    }
    public void deleteAllEpics() {
        epics.clear();
    }

    public Subtask getSubtaskById(int subtaskId) {
        return subtasks.get(subtaskId);
    }

    public Epic getEpicById(int epicId) {
        return epics.get(epicId);
    }
    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }
}
