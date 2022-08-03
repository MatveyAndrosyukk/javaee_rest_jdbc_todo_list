package api.servlets;

import api.model.Task;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static api.util.TaskUtils.idIsInvalid;

@WebServlet("/get-task")
public class JsonTaskServlet extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        final String id = request.getParameter("id");

        if (idIsInvalid(id, tasks)){
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        final Task task = tasks.get(Integer.parseInt(id));
        final String jsonTask = new ObjectMapper().writeValueAsString(task);

        response.setContentType("application/json; charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.write(jsonTask);
    }
}
