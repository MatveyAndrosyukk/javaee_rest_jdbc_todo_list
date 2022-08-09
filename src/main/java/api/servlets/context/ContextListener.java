package api.servlets.context;

import api.configuration.datasource.DataSourceMySQL;
import api.configuration.sessionmanager.SessionManager;
import api.configuration.sessionmanager.SessionManagerJDBC;
import api.dao.TaskDAO;
import api.dao.TaskDAOImpl;
import api.dao.UserDAO;
import api.dao.UserDAOImpl;
import api.service.*;
import api.service.rest_handlers.*;
import com.googlecode.flyway.core.Flyway;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class ContextListener implements ServletContextListener{
    private AuthenticationService authenticationService;
    private TaskService taskService;
    private UserService userService;
    private RestApiHandler restApiGetHandlerService;
    private RestApiHandler restApiPostHandlerService;
    private RestApiHandler restApiDeleteHandlerService;
    private RestApiHandler restApiPutHandlerService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext servletContext = sce.getServletContext();

        DataSource dataSource = DataSourceMySQL.getDataSource();
        SessionManager sessionManager = new SessionManagerJDBC(dataSource);
        UserDAO userDAO = new UserDAOImpl(sessionManager);
        TaskDAO taskDAO = new TaskDAOImpl(sessionManager);
        authenticationService = new AuthenticationServiceImpl(userDAO);
        taskService = new TaskServiceImpl(taskDAO);
        userService = new UserServiceImpl(userDAO);
        restApiGetHandlerService = new RestApiGetHandlerService(taskDAO, userDAO);
        restApiPostHandlerService = new RestApiPostHandlerService(taskDAO, userDAO);
        restApiPutHandlerService = new RestApiPutHandlerService(taskDAO, userDAO);
        restApiDeleteHandlerService = new RestApiDeleteHandlerService(taskDAO);
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.migrate();

        servletContext.setAttribute("authenticationService", authenticationService);
        servletContext.setAttribute("taskService", taskService);
        servletContext.setAttribute("userService", userService);
        servletContext.setAttribute("restApiGetHandlerService", restApiGetHandlerService);
        servletContext.setAttribute("restApiPostHandlerService", restApiPostHandlerService);
        servletContext.setAttribute("restApiPutHandlerService", restApiPutHandlerService);
        servletContext.setAttribute("restApiDeleteHandlerService", restApiDeleteHandlerService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        this.authenticationService = null;
        this.taskService = null;
        this.userService = null;
        this.restApiGetHandlerService=null;
        this.restApiPostHandlerService=null;
        this.restApiPutHandlerService=null;
        this.restApiDeleteHandlerService=null;
    }
}
