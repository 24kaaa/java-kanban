import controllers.TaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import controllers.Managers;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        Task task1 = new Task("Задача 1", Status.NEW, "Описание задачи 1");
        Task task2 = new Task("Задача 2", Status.NEW, "Описание задачи 2");

        taskManager.add(task1);
        taskManager.add(task2);

        Subtask subtask1 = new Subtask("Подзадача 1",
                "Описание подзадачи 1", Status.NEW, "Описание", 0);
        Subtask subtask2 = new Subtask("Подзадача 2",
                "Описание подзадачи 2", Status.NEW, "Описание", 0);

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        taskManager.add(epic1);
        subtask1.setEpicId(epic1.getId());
        subtask2.setEpicId(epic1.getId());
        taskManager.add(subtask1);
        taskManager.add(subtask2);

        taskManager.updateEpicStatus(epic1);

        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3",
                Status.NEW, "Описание", 0);
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        taskManager.add(epic2);
        subtask3.setEpicId(epic2.getId());
        taskManager.add(subtask3);


        taskManager.updateEpicStatus(epic2);

        System.out.println("Список задач:");
        for (Task task : taskManager.getAllTasks()) {
            System.out.println("ID: " + task.getId() + ", Название: " + task.getNameTask()
                    + ", Статус: " + task.getStatus());
        }

        System.out.println("\nСписок подзадач:");
        for (Subtask subtask : taskManager.getAllSubtasks()) {
            System.out.println("ID: " + subtask.getId() + ", Название: " + subtask.getNameTask()
                    + ", Статус: " + subtask.getStatus() + ", Эпик ID: " + subtask.getEpicId());
        }

        System.out.println("\nСписок эпиков:");
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println("ID: " + epic.getId() + ", Название: " + epic.getNameTask()
                    + ", Статус: " + epic.getStatus());
        }


        task1.setStatus(Status.DONE);
        subtask1.setStatus(Status.DONE);
        taskManager.update(task1);
        taskManager.update(subtask1);
        taskManager.updateEpicStatus(epic1);

        System.out.println("\nСтатусы после изменений:");
        System.out.println("Задача 1: " + task1.getStatus());
        System.out.println("Подзадача 1: " + subtask1.getStatus());
        System.out.println("Эпик 1: " + epic1.getStatus());

        taskManager.delete(task2);
        taskManager.delete(epic2);


        System.out.println("\nСписки после удаления:");
        System.out.println("Задачи:");
        for (Task task : taskManager.getAllTasks()) {
            System.out.println("ID: " + task.getId() + ", Название: " + task.getNameTask() + ", Статус: " + task.getStatus());
        }

        System.out.println("\nПодзадачи:");
        for (Subtask subtask : taskManager.getAllSubtasks()) {
            System.out.println("ID: " + subtask.getId() + ", Название: " + subtask.getNameTask() + ", Статус: " + subtask.getStatus() + ", Эпик ID: " + subtask.getEpicId());
        }

        System.out.println("\nЭпики:");
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println("ID: " + epic.getId() + ", Название: " + epic.getNameTask() + ", Статус: " + epic.getStatus());
        }
    }
}
