package api.servlets.authentication;

import api.service.AuthenticationService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private static final String BAD_CREDENTIALS = "Неверный логин или пароль!";
    private AuthenticationService authenticationService;

    @Override
    public void init() {
        this.authenticationService = (AuthenticationService) getServletContext().getAttribute("authenticationService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/view/login-page.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        final String email = request.getParameter("email");
        final String password = request.getParameter("password");

        if (authenticationService.isAuthenticated(email, password)){
            final HttpSession session = request.getSession();
            session.setAttribute("email", email);
            response.sendRedirect(request.getContextPath() + "/tasks-menu");
        }else {
            request.setAttribute("credentialsError", BAD_CREDENTIALS);
            getServletContext().getRequestDispatcher("/WEB-INF/view/login-page.jsp").forward(request, response);
        }

    }
}
