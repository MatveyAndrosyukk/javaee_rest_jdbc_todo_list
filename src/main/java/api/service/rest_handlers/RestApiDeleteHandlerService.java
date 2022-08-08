package api.service.rest_handlers;

import api.dao.TaskDAO;
import com.mysql.cj.jdbc.exceptions.OperationNotSupportedException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Optional;

import static api.service.rest_handlers.util.RestApiHandlerUtils.parseID;

@Getter
@Setter
@AllArgsConstructor
public class RestApiDeleteHandlerService implements RestApiHandler{
    private TaskDAO taskDAO;
    @Override
    public Optional<String> handleRestRequest(String requestPath) throws SQLException {
        throw new OperationNotSupportedException();
    }

    @Override
    public long handleRestRequest(String requestPath, HttpServletRequest request) throws SQLException {
        long generatedId = 0;
        if (requestPath.matches("^/tasks/\\d+$")){
            long id = parseID(requestPath);
            generatedId = taskDAO.deleteById(id);
        }
        return generatedId;
    }
}
