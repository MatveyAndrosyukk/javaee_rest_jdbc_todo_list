package api.servlets;

import api.model.Task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static api.util.TaskUtils.requestIsValid;

@WebServlet("/update-task")
public class UpdateTaskServlet extends HttpServlet {
    private Map<Integer, Task> tasks;

    @Override
    public void init() {
        final Object tasks = getServletContext().getAttribute("tasks");

        if (tasks instanceof ConcurrentHashMap){
            this.tasks = (ConcurrentHashMap<Integer, Task>) tasks;
        }else {
            throw new IllegalStateException("Your repo does not initialize!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("task", tasks.get(Integer.parseInt(request.getParameter("id"))));
        request.getRequestDispatcher("/WEB-INF/view/update-task-page.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String title = request.getParameter("title");
        final String description = request.getParameter("description");
        final String id = request.getParameter("id");

        if (!requestIsValid(request)){
            response.sendRedirect(request.getContextPath() + "/update-task");
            return;
        }
        final Task task = new Task(Integer.parseInt(id), title, description);

        tasks.put(Integer.parseInt(id), task);

        response.sendRedirect(request.getContextPath() + "/");
    }
}
