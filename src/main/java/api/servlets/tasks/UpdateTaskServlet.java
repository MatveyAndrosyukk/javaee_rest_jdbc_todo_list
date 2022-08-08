package api.servlets.tasks;

import api.model.Task;
import api.model.User;
import api.service.TaskService;
import api.service.UserService;
import api.util.TaskUtils;
import lombok.SneakyThrows;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/update-task")
public class UpdateTaskServlet extends HttpServlet {
    private TaskService taskService;
    private UserService userService;

    @Override
    public void init() {
        this.taskService = (TaskService) getServletContext().getAttribute("taskService");
        this.userService = (UserService) getServletContext().getAttribute("userService");
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Task task = taskService.findById(Long.parseLong(request.getParameter("id"))).orElseThrow(SQLException::new);
        request.setAttribute("task", task);
        request.getRequestDispatcher("/WEB-INF/view/update-task-page.jsp").forward(request, response);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        request.setCharacterEncoding("UTF-8");

        Long id = Long.valueOf(request.getParameter("id"));
        String username = request.getParameter("username");

        Optional<Task> optionalTaskById = taskService.findById(id);
        Optional<User> optionalUserByUsername = userService.findByUsername(username);

        Task taskFromDB = optionalTaskById.orElseThrow(SQLException::new);

        if (username.isEmpty()){
            optionalUserByUsername = userService.findByUsername(taskFromDB.getExecutor().getUsername());
        }

        Task task = TaskUtils.enterNullFields(request, taskFromDB, optionalUserByUsername);

        if (task == null){
            request.setAttribute("task", taskService.findById(id).orElseThrow(SQLException::new));
            getServletContext().getRequestDispatcher("/WEB-INF/view/update-task-page.jsp").forward(request, response);
            return;
        }

        taskService.update(task);

        response.sendRedirect(request.getContextPath() + "/tasks-menu");
    }
}
