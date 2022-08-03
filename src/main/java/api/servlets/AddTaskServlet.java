package api.servlets;

import api.model.Task;
import api.util.TaskUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet("/add-task")
public class AddTaskServlet extends HttpServlet {
    private Map<Integer, Task> tasks;
    private AtomicInteger idCounter;

    @Override
    public void init() {
        final Object tasks = getServletContext().getAttribute("tasks");

        if (tasks instanceof ConcurrentHashMap){
            this.tasks = (ConcurrentHashMap<Integer, Task>) tasks;
        }else {
            throw new IllegalStateException("Your repo does not initialize!");
        }

        idCounter = (AtomicInteger) getServletContext().getAttribute("idCounter");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        if (TaskUtils.requestIsValid(request)){
            final String title = request.getParameter("title");
            final String description = request.getParameter("description");
            final int id = this.idCounter.getAndIncrement();

            final Task task = new Task(id, title, description);

            tasks.put(id, task);
        }
        response.sendRedirect(request.getContextPath() + "/");
    }
}
