package controllers;

import model.Epic;
import model.Subtask;
import model.Task;
import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
     void add(Task task);

     void add(Epic epic);

     void add(Subtask subtask);

     void update(Task task);

     void update(Epic epic);

     void update(Subtask subtask);

     void delete(Task task);

     void delete(Epic epic);

     void delete(Subtask subtask);

     void updateEpicStatus(Epic epic);

     ArrayList<Subtask> getSubtasksByEpicId(int epicId);

     void deleteAllTasks();

     void deleteAllSubtasks();

     void deleteAllEpics();

     Subtask getSubtaskById(int subtaskId);

     Epic getEpicById(int epicId);

     Task getTaskById(int id);

     ArrayList<Subtask> getAllSubtasks();

     ArrayList<Epic> getAllEpics();

     ArrayList<Task> getAllTasks();

     List<Task> getHistory();
}
