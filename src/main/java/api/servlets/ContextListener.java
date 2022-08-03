package api.servlets;

import api.model.Task;
import api.service.AuthenticationService;
import api.service.AuthenticationServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static api.util.TaskUtils.generateTasks;

@WebListener
public class ContextListener implements ServletContextListener{
    private Map<Integer, Task> tasks;
    private AtomicInteger idCounter;
    private AuthenticationService authenticationService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext servletContext = sce.getServletContext();

        this.tasks = new ConcurrentHashMap<>();
        this.idCounter = new AtomicInteger(4);
        this.authenticationService = new AuthenticationServiceImpl();
        servletContext.setAttribute("tasks", tasks);
        servletContext.setAttribute("idCounter", idCounter);
        servletContext.setAttribute("authenticationService", authenticationService);

        List<Task> taskList = generateTasks();
        taskList.forEach(task -> this.tasks.put(task.getId(), task));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        this.tasks = null;
        this.idCounter = null;
        this.authenticationService = null;
    }
}
