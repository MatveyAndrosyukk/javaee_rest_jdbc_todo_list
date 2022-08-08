package api.servlets.rest;

import api.service.rest_handlers.RestApiHandler;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Slf4j
@WebServlet(urlPatterns = "/rest/*")
public class RestTaskServlet extends HttpServlet {
    private static final String TASK_CREATED_SUCCESS_JSON = "{ \"task_id\" : \"%d\" }";
    private static final String USER_CREATED_SUCCESS_JSON = "{ \"user_id\" : \"%d\" }";
    private static final String TASK_DELETED_SUCCESS = "Задача с идентификационным номером %d успешно удалена !\n";
    private static final String TASK_UPDATE_SUCCESS = "Задача с идентификационным номером %d успешно обновлена !\n";
    private static final String TASK_NOT_FOUND = "Не найдено задачи с таким ID !\n";
    private static final String USER_NOT_FOUND = "Не найдено исполнителя с таким ID!\n";
    private RestApiHandler restApiGetHandlerService;
    private RestApiHandler restApiPostHandlerService;
    private RestApiHandler restApiPutHandlerService;
    private RestApiHandler restApiDeleteHandlerService;

    @Override
    public void init() {
        this.restApiGetHandlerService = (RestApiHandler) getServletContext().getAttribute("restApiGetHandlerService");
        this.restApiPostHandlerService = (RestApiHandler) getServletContext().getAttribute("restApiPostHandlerService");
        this.restApiDeleteHandlerService = (RestApiHandler) getServletContext().getAttribute("restApiDeleteHandlerService");
        this.restApiPutHandlerService = (RestApiHandler) getServletContext().getAttribute("restApiPutHandlerService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Пришел запрос {} на URI: {}", request.getMethod(), request.getRequestURI());
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/HTML; charset=UTF-8");
        final String pathInfo = request.getPathInfo();
        System.out.println(pathInfo);

        try {
            String resp = restApiGetHandlerService.handleRestRequest(pathInfo).orElseThrow(SQLException::new);
            response.setContentType("application/json; charset=UTF-8");
            response.setStatus(200);
            response.getWriter().write(resp);
        } catch (SQLException e) {
            e.printStackTrace();
            if (pathInfo.contains("tasks")) {
                response.getWriter().write(TASK_NOT_FOUND);
            }
            if (pathInfo.contains("users")) {
                response.getWriter().write(USER_NOT_FOUND);
            }
            response.setStatus(404);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Пришел запрос {} на URI: {}", req.getMethod(), req.getRequestURI());
        req.setCharacterEncoding("UTF-8");
        final String pathInfo = req.getPathInfo();

        try {
            long generatedID = restApiPostHandlerService.handleRestRequest(pathInfo, req);
            resp.setContentType("application/json; charset=UTF-8");
            if (pathInfo.contains("tasks")) {
                resp.setStatus(201);
                resp.getWriter().write(String.format(TASK_CREATED_SUCCESS_JSON, generatedID));

            }
            if (pathInfo.contains("users")) {
                resp.setStatus(201);
                resp.getWriter().write(String.format(USER_CREATED_SUCCESS_JSON, generatedID));

            }
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setContentType("application/json; charset=UTF-8");
            resp.getWriter().write(e.getMessage());
            resp.setStatus(404);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Пришел запрос {} на URI: {}", req.getMethod(), req.getRequestURI());
        req.setCharacterEncoding("UTF-8");
        final String pathInfo = req.getPathInfo();
        try {
            long generatedID = restApiPutHandlerService.handleRestRequest(pathInfo, req);

            resp.setContentType("application/json; charset=UTF-8");
            if (pathInfo.contains("tasks")) {
                resp.setStatus(201);
                resp.getWriter().write(String.format(TASK_UPDATE_SUCCESS, generatedID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setContentType("application/json; charset=UTF-8");
            resp.getWriter().write(e.getMessage());
            resp.setStatus(404);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Пришел запрос {} на URI: {}", req.getMethod(), req.getRequestURI());
        req.setCharacterEncoding("UTF-8");
        final String pathInfo = req.getPathInfo();

        try {
            long generatedId = restApiDeleteHandlerService.handleRestRequest(pathInfo, req);
            resp.setContentType("application/json; charset=UTF-8");

            if (pathInfo.contains("tasks")) {
                resp.setStatus(201);
                resp.getWriter().write(String.format(TASK_DELETED_SUCCESS, generatedId));

            }

        } catch (SQLException e) {
            e.printStackTrace();
            if (pathInfo.contains("tasks")) {
                resp.getWriter().write(TASK_NOT_FOUND);
            }

            resp.setStatus(404);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
