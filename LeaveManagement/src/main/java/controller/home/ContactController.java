package controller.home;

import java.io.IOException;
import java.util.List;

import dal.UserDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserDTO;

@WebServlet(name = "Contact", urlPatterns = "/contact")
public class ContactController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        UserDBContext userDB = new UserDBContext();
        List<UserDTO> users = userDB.getAllUsersWithRoleAndDepartment();
        req.setAttribute("userList", users);
        req.getRequestDispatcher("/view/contact.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
} 