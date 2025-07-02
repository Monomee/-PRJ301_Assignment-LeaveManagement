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
import java.util.ArrayList;
import model.LeaveRequest;
import model.User;

/**
 *
 * @author PC
 */
@WebServlet(name = "LeaveRequestReview", urlPatterns = "/leave/review")
public class LeaveRequestReviewServlet extends HttpServlet {

    private LeaveRequestDBContext leaveRequestDB;

    @Override
    public void init() throws ServletException {
        leaveRequestDB = new LeaveRequestDBContext();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        int lid = Integer.parseInt(req.getParameter("lid"));
        String action = req.getParameter("action");
        String reason = req.getParameter("reason");

        try {
            LeaveRequest request = leaveRequestDB.findById(lid);
            User manager = request.getUser().getManager();
            if (manager == null || request == null
                    || !request.getStatus().equals("inprogress")
                    || manager.getUid() == user.getUid()) {
                session.setAttribute("error", "Cannot process this request.");
                resp.sendRedirect(req.getContextPath() + "/leave/review");
                return;
            }

            String status = "approve".equals(action) ? "approved" : "rejected";
            leaveRequestDB.updateStatus(lid, status, user, reason);
            session.setAttribute("message", "Leave request " + status + " successfully!");
            resp.sendRedirect(req.getContextPath() + "/leave/review");
        } catch (Exception e) {
            session.setAttribute("error", "Failed to process request: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/leave/review");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");
        ArrayList<LeaveRequest> leaveRequests = (ArrayList<LeaveRequest>) leaveRequestDB.getLeaveRequestsBySubordinates(user.getUid());
        req.setAttribute("leaveRequests", leaveRequests);
        req.getRequestDispatcher("/view/leave_application/review.jsp").forward(req, resp);
    }

}
