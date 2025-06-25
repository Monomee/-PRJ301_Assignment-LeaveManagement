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
@WebServlet(name = "LeaveRequestCreate", urlPatterns = "/leave/create")
public class LeaveRequestCreateServlet extends HttpServlet{
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
        req.getRequestDispatcher("/view/leave_application/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        String title = req.getParameter("title");
        String fromDateStr = req.getParameter("fromDate");
        String toDateStr = req.getParameter("toDate");
        String reason = req.getParameter("reason");

        try {
            LocalDate fromDate = LocalDate.parse(fromDateStr);
            LocalDate toDate = LocalDate.parse(toDateStr);

            // Validation
            if (fromDate.isBefore(LocalDate.now())) {
                req.setAttribute("error", "From date cannot be in the past");
                req.getRequestDispatcher("/view/leave_application/create.jsp").forward(req, resp);
                return;
            }
            if (toDate.isBefore(fromDate)) {
                req.setAttribute("error", "To date must be after from date");
                req.getRequestDispatcher("/view/leave_application/create.jsp").forward(req, resp);
                return;
            }

            LeaveRequest request = new LeaveRequest();
            request.setUser(user);
            request.setTitle(title);
            request.setFromDate(fromDate);
            request.setToDate(toDate);
            request.setReason(reason);
            request.setStatus("inprogress");
            request.setCreatedAt(LocalDateTime.now());

            leaveRequestDB.create(request);
            req.getSession().setAttribute("message", "Leave request created successfully!");
            resp.sendRedirect(req.getContextPath() + "/leave/view");
        } catch (DateTimeParseException e) {
            req.setAttribute("error", "Invalid date format. Use YYYY-MM-DD.");
            req.getRequestDispatcher("/view/leave_application/create.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", "Failed to create leave request: " + e.getMessage());
            req.getRequestDispatcher("/view/leave_application/create.jsp").forward(req, resp);
        }
    }
    
}
