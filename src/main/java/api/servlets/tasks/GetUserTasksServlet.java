package api.servlets.tasks;

import api.model.User;
import api.service.TaskService;
import api.service.UserService;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/my-tasks")
public class GetUserTasksServlet extends HttpServlet {
    private TaskService taskService;
    private UserService userService;
    @Override
    public void init() throws ServletException {
        this.taskService = (TaskService) getServletContext().getAttribute("taskService");
        this.userService = (UserService) getServletContext().getAttribute("userService");
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        String email = (String) request.getSession().getAttribute("email");

        User user = userService.findByEmail(email).orElseThrow(SQLException::new);

        request.setAttribute("tasks", taskService.findByExecutor(user));
        getServletContext().getRequestDispatcher("/WEB-INF/view/my-tasks.jsp").forward(request, response);
    }
}
