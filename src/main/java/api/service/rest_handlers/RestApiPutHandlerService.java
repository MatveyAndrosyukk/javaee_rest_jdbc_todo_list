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

import static api.service.rest_handlers.util.RestApiHandlerUtils.*;

@Getter
@Setter
@AllArgsConstructor
public class RestApiPutHandlerService implements RestApiHandler{
    private static final String TASK_NOT_FOUND = "Не найдено задачи с таким ID !\n";
    private static final String USER_NOT_FOUND = "Не найдено исполнителя с таким ID!\n";
    private static final String TASK_EXECUTOR_NOT_FOUND = "Исполнитель с таким username не найден в БД !\n";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private TaskDAO taskDAO;
    private UserDAO userDAO;

    @Override
    public Optional<String> handleRestRequest(String requestPath) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long handleRestRequest(String requestPath, HttpServletRequest request) throws IOException, SQLException, IllegalAccessException, NoSuchFieldException {
        long generatedId = 0;
        long id = parseID(requestPath);
        String bodyParams = request.getReader().lines().collect(Collectors.joining());

        if (requestPath.matches("^/tasks/\\d+$")) {
            Optional<Task> optionalTaskById = taskDAO.findById(id);
            if (optionalTaskById.isEmpty()){
                throw new SQLException(TASK_NOT_FOUND);
            }

            Task taskFromDB = optionalTaskById.get();
            Task task = objectMapper.readValue(bodyParams, Task.class);
            fillNullFields(task, taskFromDB);

            String username = getField("\"username\": \"\\w+\"", bodyParams);
            if (username != null){
                Optional<User> optionalUserByUsername = userDAO.findByUsername(username);
                if (optionalUserByUsername.isEmpty()){
                    throw new SQLException(TASK_EXECUTOR_NOT_FOUND);
                }
            }

            generatedId = taskDAO.update(task);
        }

        return generatedId;
    }
}
