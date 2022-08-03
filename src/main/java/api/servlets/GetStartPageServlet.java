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

@WebServlet("/tasks-menu")
public class GetStartPageServlet extends HttpServlet {
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
        request.setAttribute("tasks", tasks.values());
        getServletContext().getRequestDispatcher("/WEB-INF/view/start-page.jsp").forward(request, response);
    }
}
