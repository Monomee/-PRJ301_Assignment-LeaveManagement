<%-- 
    Document   : home
    Created on : Jun 22, 2025, 11:54:07 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    </head>
    <body>
        <nav class="navbar">
            <div class="navbar-left">
                <span class="logo">Leave Management</span>
            </div>
            <div class="navbar-right">
                <span class="welcome">Welcome, ${sessionScope.user.fullName}</span>
                <a class="logout-btn" href="/logout">Logout</a>
            </div>
        </nav>
        <main class="dashboard">
            <section class="card roles-card">
                <h2>Your Roles</h2>
                <ul class="roles-list">
                    <c:forEach var="role" items="${roles}">
                        <li>${role.roleName}</li>
                    </c:forEach>
                </ul>
            </section>
            <section class="card features-card">
                <h2>Your Features</h2>
                <ul class="features-list">
                    <c:forEach var="feature" items="${features}">
                        <c:if test="${feature.entryPoint != '/leave/update'}">
                            <li><a href="${pageContext.request.contextPath}${feature.entryPoint}">${feature.featureName}</a></li>
                        </c:if>
                    </c:forEach>
                </ul>
            </section>
        </main>
        <script src="${pageContext.request.contextPath}/js/home.js"></script>
    </body>
</html>
