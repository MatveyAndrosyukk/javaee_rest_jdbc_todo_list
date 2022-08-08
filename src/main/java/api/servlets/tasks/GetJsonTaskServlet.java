package api.servlets.tasks;

import api.model.Task;
import api.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/get-task")
public class GetJsonTaskServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private TaskService taskService;

    @Override
    public void init() {
        this.taskService = (TaskService) getServletContext().getAttribute("taskService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.valueOf(request.getParameter("id"));

        try {
            Task task = taskService.findById(id).orElseThrow(SQLException::new);
            String jsonTask = objectMapper.writeValueAsString(task);
            System.out.println(task);
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write(jsonTask);

        } catch (SQLException e) {
            e.printStackTrace();
            response.setContentType("text/HTML; charset=UTF-8");
            response.getWriter().write("Не найдено задачи с таким ID");
        }
    }
}
