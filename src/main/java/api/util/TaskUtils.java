package api.util;

import api.model.Task;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TaskUtils {

    public static List<Task> generateTasks() {
        final Task task1 = new Task(1,
                "Написать Hello, World",
                "Создать простое приложение на Java");
        final Task task2 = new Task(2,
                "Написать простое приложение на JavaEE",
                "Написать и выложить на GitHub");
        final Task task3 = new Task(3,
                "Написать простое приложение на Spring Boot",
                "Написать и выложить на GitHub");

        return Arrays.asList(task1, task2, task3);
    }

    public static boolean requestIsValid(HttpServletRequest request) {
        final String title = request.getParameter("title");
        final String description = request.getParameter("description");

        return !title.isEmpty() && !description.isEmpty();
    }

    public static boolean idIsInvalid(String id, Map<Integer, Task> tasks) {
        final Task task = tasks.get(Integer.parseInt(id));
        return task == null;
    }
}
