<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/view/navbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Contact Directory</title>
    <link rel="stylesheet" href="/css/contact.css">
</head>
<body>
    <main class="contact-main">
        <h1 class="contact-title">Company Contact Directory</h1>
        <section class="contact-list-section card">
            <table class="contact-table">
                <thead>
                    <tr>
                        <th>Full Name</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>Department</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${userList}">
                        <tr>
                            <td>${user.fullName}</td>
                            <td>${user.email}</td>
                            <td>${user.roleName}</td>
                            <td>${user.departmentName}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </section>
    </main>
    <script src="/js/contact.js"></script>
</body>
</html> 