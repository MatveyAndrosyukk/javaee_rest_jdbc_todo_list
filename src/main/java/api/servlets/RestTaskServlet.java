package api.servlets;

import api.model.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static api.util.TaskUtils.idIsInvalid;
import static api.util.TaskUtils.requestIsValid;

@Slf4j
@WebServlet(urlPatterns = "/tasks/*")
public class RestTaskServlet extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Пришел запрос {} на URI: {}", request.getMethod(), request.getRequestURI());

        request.setCharacterEncoding("UTF-8");

        final String pathInfo = request.getPathInfo();
        final String[] params = pathInfo.split("/");
        final String paramId = params[1];

        final Integer taskId = Integer.parseInt(paramId);
        final Task task = tasks.get(taskId);
        final String taskJson = new ObjectMapper().writeValueAsString(task);

        response.setContentType("application/json; charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.write(taskJson);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Пришел запрос {} на URI: {}", request.getMethod(), request.getRequestURI());

        request.setCharacterEncoding("UTF-8");

        if (!requestIsValid(request)){
            response.setStatus(404);
            return;
        }

        final String title = request.getParameter("title");
        final String description = request.getParameter("description");
        final int id = this.idCounter.getAndIncrement();

        final Task task = new Task(id, title, description);
        this.tasks.put(id, task);

        final String taskJson = new ObjectMapper().writeValueAsString(task);

        response.setContentType("application/json; charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.write(taskJson);

        response.setStatus(201);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        log.info("Пришел запрос {} на URI: {}", req.getMethod(), req.getRequestURI());

        if (!requestIsValid(req)){
            resp.setStatus(404);
            return;
        }

        final String pathInfo = req.getPathInfo();
        final String[] params = pathInfo.split("/");
        final String paramId = params[1];
        final String title = req.getParameter("title");
        final String description = req.getParameter("description");

        Task task = this.tasks.get(Integer.parseInt(paramId));
        task.setTitle(title);
        task.setDescription(description);

        this.tasks.put(Integer.parseInt(paramId), task);

        resp.setStatus(201);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        log.info("Пришел запрос {} на URI: {}", req.getMethod(), req.getRequestURI());

        req.setCharacterEncoding("UTF-8");

        final String pathInfo = req.getPathInfo();
        final String[] params = pathInfo.split("/");
        final String paramId = params[1];

        if (idIsInvalid(paramId, tasks)){
            resp.setStatus(404);
            return;
        }

        this.tasks.remove(Integer.parseInt(paramId));
        resp.setStatus(201);
    }
}
