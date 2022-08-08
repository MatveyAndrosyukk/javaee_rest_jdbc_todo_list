package api.service.rest_handlers;

import api.dao.TaskDAO;
import api.dao.UserDAO;
import api.model.Task;
import api.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static api.service.rest_handlers.util.RestApiHandlerUtils.parseID;

@Getter
@Setter
@AllArgsConstructor
public class RestApiGetHandlerService implements RestApiHandler{
    private final ObjectMapper objectMapper = new ObjectMapper();
    private TaskDAO taskDAO;
    private UserDAO userDAO;

    @Override
    public Optional<String> handleRestRequest(String requestPath) throws SQLException, JsonProcessingException {
        if (requestPath.matches("^/tasks/\\d+$")){
            long id = parseID(requestPath);
            Task task = taskDAO.findById(id).orElseThrow(SQLException::new);
            return Optional.ofNullable(objectMapper.writeValueAsString(task));
        }else if (requestPath.matches("^/tasks/$")){
            final List<Task> tasks = taskDAO.findAll();
            return Optional.ofNullable(objectMapper.writeValueAsString(tasks));
        }else if (requestPath.matches("^/users/\\d+$")){
            long id = parseID(requestPath);
            User user = userDAO.findById(id).orElseThrow(SQLException::new);
            return Optional.ofNullable(objectMapper.writeValueAsString(user));
        }else if (requestPath.matches("^/users/$")){
            final List<User> users = userDAO.findAll();
            return Optional.ofNullable(objectMapper.writeValueAsString(users));
        }
        return Optional.empty();
    }

    @Override
    public long handleRestRequest(String requestPath, HttpServletRequest request) {
        throw new UnsupportedOperationException();
    }
}
