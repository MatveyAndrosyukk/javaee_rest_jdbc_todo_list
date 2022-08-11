package api.servlets.tasks;

import api.service.TaskService;
import lombok.SneakyThrows;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/tasks-menu")
public class GetAllTasksPageServlet extends HttpServlet {
    private TaskService taskService;

    @Override
    public void init() {
        this.taskService = (TaskService) getServletContext().getAttribute("taskService");
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("tasks", taskService.findAll());
        getServletContext().getRequestDispatcher("/WEB-INF/view/all-tasks-page.jsp").forward(request, response);
    }
}
