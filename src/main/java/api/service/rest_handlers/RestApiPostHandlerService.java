package api.service.rest_handlers;

import api.dao.TaskDAO;
import api.dao.UserDAO;
import api.model.Task;
import api.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Collectors;

import static api.service.rest_handlers.util.RestApiHandlerUtils.getField;

@Getter
@Setter
@AllArgsConstructor
public class RestApiPostHandlerService implements RestApiHandler {
    private static final String TASK_EXECUTOR_NOT_FOUND = "Исполнитель с таким username не найден в БД !\n";
    private static final String TASK_EXECUTOR_NOT_FILLED = "Вы не указали исполнителя задачи !\n";
    private static final String USER_USERNAME_ERROR = "Исполнитель с таким username уже есть в базе !\n";
    private static final String USER_EMAIL_ERROR = "Исполнитель с таким email уже есть в базе !\n";
    private static final String FIELDS_NOT_FILLED_ERROR = "Заполните все поля правильно !\n";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private TaskDAO taskDAO;
    private UserDAO userDAO;

    @Override
    public Optional<String> handleRestRequest(String requestPath) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long handleRestRequest(String requestPath, HttpServletRequest request) throws IOException, SQLException {
        long generatedId = 0;
        String bodyParams = request.getReader().lines().collect(Collectors.joining());

        if (requestPath.matches("^/tasks/$")) {
            String title = getField("\"title\": \"\\w+\"", bodyParams);
            String description = getField("\"description\": \".[^\"]+\"", bodyParams);
            String deadline_date = getField("\"deadline_date\": \"\\d{4}-\\d{2}-\\d{2}\"", bodyParams);
            String username = getField("\"username\": \"\\w+\"", bodyParams);
            if (title == null || description == null || deadline_date == null) {
                throw new SQLException(FIELDS_NOT_FILLED_ERROR);
            }

            Optional<User> optionalUser = userDAO.findByUsername(username);
            if (optionalUser.isEmpty()) {
                throw new SQLException(TASK_EXECUTOR_NOT_FOUND);
            }

            Task task = objectMapper.readValue(bodyParams, Task.class);
            generatedId = taskDAO.save(task);

        } else if (requestPath.matches("^/users/$")) {
            String username = getField("\"username\": \"\\w+\"", bodyParams);
            String name = getField("\"name\": \"\\w+\"", bodyParams);
            String surname = getField("\"surname\": \"\\w+\"", bodyParams);
            String email = getField("\"email\": \"\\S+\"", bodyParams);
            if (username == null || name == null || surname == null
                    || email == null) {
                throw new SQLException(FIELDS_NOT_FILLED_ERROR);
            }

            Optional<User> optionalUserByUsername = userDAO.findByUsername(username);
            Optional<User> optionalUserByEmail = userDAO.findByEmail(email);
            if (optionalUserByUsername.isPresent()) {
                throw new SQLException(USER_USERNAME_ERROR);
            }
            if (optionalUserByEmail.isPresent()) {
                throw new SQLException(USER_EMAIL_ERROR);
            }

            User user = objectMapper.readValue(bodyParams, User.class);
            generatedId = userDAO.save(user);
        }

        return generatedId;
    }
}