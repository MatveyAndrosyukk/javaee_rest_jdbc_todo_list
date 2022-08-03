package api.servlets;

import api.service.AuthenticationService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private AuthenticationService authenticationService;

    @Override
    public void init() throws ServletException {
        this.authenticationService = (AuthenticationService) getServletContext().getAttribute("authenticationService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/view/login-page.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String username = request.getParameter("username");
        final String password = request.getParameter("password");

        if (authenticationService.isAuthenticated(username, password)){
            request.getSession();
            response.sendRedirect("/tasks-menu");
        }else {
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("Неверный логин или пароль");
        }

    }
}
