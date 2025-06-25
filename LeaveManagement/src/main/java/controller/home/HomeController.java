/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.home;

import dal.RoleFeatureDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import model.Feature;
import model.Role;
import model.User;

/**
 *
 * @author PC
 */
@WebServlet(name = "Home", urlPatterns = "/home")
public class HomeController extends HttpServlet{

    private RoleFeatureDBContext roleFeatureDB;

    @Override
    public void init() throws ServletException {
        roleFeatureDB = new RoleFeatureDBContext();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        List<Role> roles = roleFeatureDB.getRolesByUserId(user.getUid());
        List<Feature> features = roleFeatureDB.getFeaturesByUserId(user.getUid());

        req.setAttribute("roles", roles);
        req.setAttribute("features", features);
        req.getRequestDispatcher("/view/home/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
