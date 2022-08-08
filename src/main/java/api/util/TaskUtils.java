package api.util;

import api.model.Task;
import api.model.User;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Slf4j
public class TaskUtils {
    private static final String USER_ERROR_MESSAGE = "Исполнитель с таким username не существует";
    private static final String INVALID_REQUEST_MESSAGE = "Заполните поля правильно";
    private static final String INVALID_DATE_MESSAGE = "Заполните срок выполнения";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static boolean requestIsValid(HttpServletRequest request, Optional<User> optional) {
        final String title = request.getParameter("title");
        final String description = request.getParameter("description");
        final String username = request.getParameter("username");

        final User user = new User();
        final Task task = new Task();
        user.setUsername(username);
        task.setTitle(title);
        task.setDescription(description);
        task.setExecutor(user);

        try {
            task.setDeadline_date(LocalDate.parse(request.getParameter("deadline_date"), formatter));
        }catch (DateTimeParseException exception){
            log.error(exception.getMessage(), exception);
            request.setAttribute("invalidDateMessage",INVALID_DATE_MESSAGE);
            request.setAttribute("enteredFields",task);
            return false;
        }

        if (title.isEmpty() || description.isEmpty() || username.isEmpty()){

            request.setAttribute("invalidRequestMessage",INVALID_REQUEST_MESSAGE);
            request.setAttribute("enteredFields",task);
            return false;
        }
        if (optional.isEmpty()){
            request.setAttribute("userErrorMessage",USER_ERROR_MESSAGE);
            request.setAttribute("enteredFields",task);
            return false;
        }
        return true;
    }

    public static Task enterNullFields(HttpServletRequest request, Task taskFromDB, Optional<User> optional){
        final String title = request.getParameter("title");
        final String description = request.getParameter("description");
        final boolean done = request.getParameter("done") != null;
        final String username = request.getParameter("username");

        final User user = new User();
        final Task task = new Task();
        user.setUsername(username);
        task.setId(taskFromDB.getId());
        task.setTitle(title);
        task.setDescription(description);
        task.setDone(done);
        task.setExecutor(user);

        try {
            final LocalDate deadline_date = LocalDate.parse(request.getParameter("deadline_date"), formatter);
            task.setDeadline_date(deadline_date);
        }catch (DateTimeParseException exception){
            task.setDeadline_date(taskFromDB.getDeadline_date());
        }
        if (optional.isEmpty()){
            request.setAttribute("userErrorMessage",USER_ERROR_MESSAGE);
            request.setAttribute("enteredFields",task);
            return null;
        }
        if (title.isEmpty()) task.setTitle(taskFromDB.getTitle());
        if (description.isEmpty()) task.setDescription(taskFromDB.getDescription());
        if (title.isEmpty()) task.setTitle(taskFromDB.getTitle());
        if (username.isEmpty()) task.setExecutor(taskFromDB.getExecutor());

        return task;
    }
}
