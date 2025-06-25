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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import model.LeaveRequest;
import model.User;

/**
 *
 * @author PC
 */
@WebServlet(name = "LeaveRequestUpdate", urlPatterns = "/leave/update")
public class LeaveRequestUpdateServlet extends HttpServlet{
    private LeaveRequestDBContext leaveRequestDB;

    @Override
    public void init() throws ServletException {
        leaveRequestDB = new LeaveRequestDBContext();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String action = req.getParameter("action");
        int lid = Integer.parseInt(req.getParameter("lid"));
        LeaveRequest request = leaveRequestDB.findById(lid);

        if (request == null || !request.getStatus().equals("inprogress")) {
            req.getSession().setAttribute("error", "Cannot edit/delete this request.");
            resp.sendRedirect(req.getContextPath() + "/leave/view");
            return;
        }

        if ("delete".equals(action)) {
            leaveRequestDB.delete(lid);
            req.getSession().setAttribute("message", "Leave request deleted successfully!");
            resp.sendRedirect(req.getContextPath() + "/leave/view");
        } else {
            req.setAttribute("leaveRequest", request);
            req.getRequestDispatcher("/view/leave_application/update.jsp").forward(req, resp);
        }
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
        String title = req.getParameter("title");
        String fromDateStr = req.getParameter("fromDate");
        String toDateStr = req.getParameter("toDate");
        String reason = req.getParameter("reason");

        try {
            LeaveRequest request = leaveRequestDB.findById(lid);
            if (request == null || !request.getStatus().equals("inprogress") || request.getUser().getUid() != user.getUid()) {
                req.getSession().setAttribute("error", "Cannot edit this request.");
                resp.sendRedirect(req.getContextPath() + "/leave/view");
                return;
            }

            LocalDate fromDate = LocalDate.parse(fromDateStr);
            LocalDate toDate = LocalDate.parse(toDateStr);

            // Validation
            if (fromDate.isBefore(LocalDate.now())) {
                req.setAttribute("error", "From date cannot be in the past");
                req.setAttribute("leaveRequest", request);
                req.getRequestDispatcher("/view/leave_application/update.jsp").forward(req, resp);
                return;
            }
            if (toDate.isBefore(fromDate)) {
                req.setAttribute("error", "To date must be after from date");
                req.setAttribute("leaveRequest", request);
                req.getRequestDispatcher("/view/leave_application/update.jsp").forward(req, resp);
                return;
            }

            request.setTitle(title);
            request.setFromDate(fromDate);
            request.setToDate(toDate);
            request.setReason(reason);
            request.setCreatedAt(LocalDateTime.now());

            leaveRequestDB.update(request);
            req.getSession().setAttribute("message", "Leave request updated successfully!");
            resp.sendRedirect(req.getContextPath() + "/leave/view");
        } catch (DateTimeParseException e) {
            req.setAttribute("error", "Invalid date format. Use YYYY-MM-DD.");
            req.setAttribute("leaveRequest", leaveRequestDB.findById(lid));
            req.getRequestDispatcher("/view/leave_application/update.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", "Failed to update leave request: " + e.getMessage());
            req.setAttribute("leaveRequest", leaveRequestDB.findById(lid));
            req.getRequestDispatcher("/view/leave_application/update.jsp").forward(req, resp);
        }
    }
    
}
