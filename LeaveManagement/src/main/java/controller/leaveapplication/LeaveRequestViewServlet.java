/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.leaveapplication;

import dal.LeaveRequestDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import model.LeaveRequest;
import model.User;

/**
 *
 * @author PC
 */
@WebServlet(name = "LeaveRequestView", urlPatterns = "/leave/view")
public class LeaveRequestViewServlet extends HttpServlet{
    private LeaveRequestDBContext leaveRequestDB;

    @Override
    public void init() throws ServletException {
        leaveRequestDB = new LeaveRequestDBContext();
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        List<LeaveRequest> leaveRequests = leaveRequestDB.findByUserId(user.getUid());
        req.setAttribute("leaveRequests", leaveRequests);

        // Get and clear success message
        String message = (String) session.getAttribute("message");
        if (message != null) {
            req.setAttribute("message", message);
            session.removeAttribute("message");
        }

        req.getRequestDispatcher("/view/leave_application/view.jsp").forward(req, resp);
    }
    
}
