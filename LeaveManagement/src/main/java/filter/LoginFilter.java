/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package filter;

import java.io.IOException;

import dal.RoleFeatureDBContext;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author PC
 */
@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {

    private RoleFeatureDBContext roleFeatureDB;

    @Override
    public void init(jakarta.servlet.FilterConfig filterConfig) throws ServletException {
        roleFeatureDB = new RoleFeatureDBContext();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        String path = req.getServletPath();

        // Allow access to login page without authentication
        if (path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/images/")
                || path.equals("/login") || path.equals("/home")) {
            chain.doFilter(request, response);
            return;
        }

        // Check if user is logged in
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Check if path does not exist 
        if (req.getServletContext().getResource(path) == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource Not Found");
            return;
        }

        // Check access permission
        User user = (User) session.getAttribute("user");
        if (!roleFeatureDB.hasAccess(user.getUid(), path)) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
