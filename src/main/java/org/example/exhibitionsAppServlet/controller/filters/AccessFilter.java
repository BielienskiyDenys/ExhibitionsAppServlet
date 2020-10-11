package org.example.exhibitionsAppServlet.controller.filters;

import org.example.exhibitionsAppServlet.model.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        User user;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String path = request.getRequestURI();
        if (path.contains("login") || path.contains("registration")) {//TODO: rewrite add user roles
            if ((((HttpServletRequest) servletRequest).getSession().getAttribute("userRole") != null) &&
                    !((HttpServletRequest) servletRequest).getSession().getAttribute("userRole").equals("GUEST")) {
//                servletResponse.getWriter().append("AccessDenied");
                HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
                httpResponse.sendRedirect("redirect:/index.jsp");
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else if(path.contains("admin")){
            if ((((HttpServletRequest) servletRequest).getSession().getAttribute("userRole") != null) &&
                    ((HttpServletRequest) servletRequest).getSession().getAttribute("userRole").equals("ADMIN")) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
//                servletResponse.getWriter().append("AccessDenied");
                HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
                httpResponse.sendRedirect("redirect:/index.jsp");
            }
        }
        else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

}
