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
import java.io.PrintWriter;
import java.util.ArrayList;
import model.LeaveRequest;
import model.User;
import com.google.gson.Gson;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author PC
 */
@WebServlet(name = "LeaveRequestAgenda", urlPatterns = "/leave/agenda")
public class LeaveRequestAgendaServlet extends HttpServlet {

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
        String action = req.getParameter("action");
        String view = req.getParameter("view");

        if ("events".equals(action)) {
            LocalDate startDate = req.getParameter("start") != null ? LocalDate.parse(req.getParameter("start")) : LocalDate.now().minusMonths(1);
            LocalDate endDate = req.getParameter("end") != null ? LocalDate.parse(req.getParameter("end")) : LocalDate.now().plusMonths(1);
            String employeeFilter = req.getParameter("employeeFilter");
            List<LeaveRequest> leaveRequests = leaveRequestDB.findForAgendaWithEmployeeFilter(user.getDepartment().getDid(), startDate, endDate, employeeFilter);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            Gson gson = new Gson();
            String jsonEvents = gson.toJson(leaveRequests.stream().map(lr -> new Event(
                    lr.getLid(),
                    lr.getTitle(),
                    lr.getUser().getFullName(),
                    lr.getFromDate().toString(),
                    lr.getToDate().plusDays(1).toString(),
                    lr.getReason(),
                    lr.getProcessedBy() != null ? lr.getProcessedBy().getFullName() : "",
                    lr.getStatus()
            )).toList());
            out.print(jsonEvents);
            out.flush();
        } else if ("staff".equals(action)) {
            LocalDate startDate = req.getParameter("start") != null ? LocalDate.parse(req.getParameter("start")) : LocalDate.now().with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
            LocalDate endDate = startDate.plusDays(6);
            List<User> users = leaveRequestDB.findUsersByDepartment(user.getDepartment().getDid());
            List<LeaveRequest> leaveRequests = leaveRequestDB.findForAgenda(user.getDepartment().getDid(), startDate, endDate);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            Gson gson = new Gson();
            String jsonStaff = gson.toJson(users.stream().map(u -> new StaffStatus(
                    u.getUid(),
                    u.getFullName(),
                    leaveRequests.stream()
                            .filter(lr -> lr.getUser().getUid() == u.getUid() && lr.getStatus().equals("approved"))
                            .flatMap(lr -> {
                                List<LocalDate> dates = lr.getFromDate().datesUntil(lr.getToDate().plusDays(1)).collect(Collectors.toList());
                                return dates.stream().map(d -> new StaffDay(d, "absent"));
                            })
                            .collect(Collectors.toList()),
                    startDate,
                    endDate
            )).toList());
            out.print(jsonStaff);
            out.flush();
        } else {
            req.setAttribute("view", view != null ? view : "calendar");
            req.getRequestDispatcher("/view/leave_application/agenda.jsp").forward(req, resp);
        }
    }

    // Inner class to map LeaveRequest to FullCalendar event format
    private static class Event {

        public int id;
        public String title;
        public String employee;
        public String start;
        public String end;
        public String reason;
        public String processedBy;
        public String status;

        public Event(int id, String title, String employee, String start, String end, String reason, String processedBy, String status) {
            this.id = id;
            this.title = title;
            this.employee = employee;
            this.start = start;
            this.end = end;
            this.reason = reason;
            this.processedBy = processedBy;
            this.status = status;
        }
    }

    // Inner class for staff view
    private static class StaffStatus {

        public int uid;
        public String fullName;
        public List<StaffDay> days;
        public String startDate;
        public String endDate;

        public StaffStatus(int uid, String fullName, List<StaffDay> days, LocalDate startDate, LocalDate endDate) {
            this.uid = uid;
            this.fullName = fullName;
            this.days = days;
            this.startDate = startDate.toString();
            this.endDate = endDate.toString();
        }
    }

    // Inner class for a single day status
    private static class StaffDay {

        public String date;
        public String status;

        public StaffDay(LocalDate date, String status) {
            this.date = date.toString();
            this.status = status;
        }
    }
}
