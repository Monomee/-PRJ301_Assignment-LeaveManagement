/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.authentication;

import dal.UserDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.User;

/**
 *
 * @author PC
 */
@WebServlet("/login")
public class LoginController extends HttpServlet{
    private UserDBContext userDB;

    @Override
    public void init() throws ServletException {
        userDB = new UserDBContext();
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = userDB.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) { // Trong thực tế, dùng bcrypt để kiểm tra mật khẩu
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect("home");
        } else {
            req.setAttribute("error", "Invalid username or password");
            req.getRequestDispatcher("/view/authentication/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
    
}
