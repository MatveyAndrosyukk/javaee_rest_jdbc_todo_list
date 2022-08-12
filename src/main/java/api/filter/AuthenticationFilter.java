package api.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getRequestURI().substring(request.getContextPath().length());
        if(path.startsWith("/login") || path.startsWith("/images") ||
                path.startsWith("/registration") || path.startsWith("/rest")){
            System.out.println(request.getSession(false));
            System.out.println(path + " из if выражения");
            filterChain.doFilter(request, response);
            return;
        }


        String email = (String) request.getSession().getAttribute("email");
        if (email == null){
            request.getRequestDispatcher("/WEB-INF/view/login-page.jsp").forward(request, response);
        }else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void destroy() {

    }
}
