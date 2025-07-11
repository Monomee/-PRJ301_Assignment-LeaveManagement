<%-- 
    Document   : home
    Created on : Jun 22, 2025, 11:54:07 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/view/navbar.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dashboard - HR Management</title>
        <link rel="stylesheet" href="/css/home.css">
    </head>
    <body>
        <main class="dashboard">
            <section class="welcome-section card">
                <h1>Welcome, ${sessionScope.user.fullName}!</h1>
                <p>Glad to see you at the Leave Management System. Here you can manage your leave requests, stay updated with company news, and connect with colleagues.</p>
            </section>
            <section class="quick-links">
                <div class="quick-link card">
                    <a href="/tools/leave">
                        <span class="icon">📝</span>
                        <span>Apply for Leave</span>
                    </a>
                </div>
                <div class="quick-link card">
                    <a href="/news">
                        <span class="icon">📰</span>
                        <span>Company News</span>
                    </a>
                </div>
                <div class="quick-link card">
                    <a href="/contact">
                        <span class="icon">📇</span>
                        <span>Contact Directory</span>
                    </a>
                </div>
                <div class="quick-link card">
                    <a href="/tools/leave">
                        <span class="icon">🛠️</span>
                        <span>Leave Tools</span>
                    </a>
                </div>
            </section>
            <section class="announcements card">
                <h2>Announcements</h2>
                <ul>
                    <li><strong>2025-06-23:</strong> Hệ thống nghỉ phép đã cập nhật giao diện mới!</li>
                    <li><strong>2025-06-20:</strong> Đừng quên kiểm tra lịch nghỉ lễ sắp tới.</li>
                    <li><strong>2025-06-15:</strong> Chào mừng các thành viên mới gia nhập công ty!</li>
                </ul>
            </section>
        </main>
        <script src="/js/home.js"></script>
    </body>
</html>
