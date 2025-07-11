<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/view/navbar.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tools</title>
        <link rel="stylesheet" href="/css/leave.css">
    </head>
    <body>
        <main class="leave-main">
            <section class="leave-card">
                <h2 class="leave-title">Your Roles</h2>
                <ul class="leave-list">
                    <c:forEach var="role" items="${roles}">
                        <li class="leave-list-item">${role.roleName}</li>
                    </c:forEach>
                </ul>
            </section>
            <section class="leave-card">
                <h2 class="leave-title">Your Features</h2>
                <ul class="leave-list">
                    <c:forEach var="feature" items="${features}">
                        <c:if test="${feature.entryPoint != '/leave/update'}">
                            <li class="leave-list-item"><a class="leave-btn" href="${pageContext.request.contextPath}${feature.entryPoint}">${feature.featureName}</a></li>
                        </c:if>
                    </c:forEach>
                </ul>
            </section>
        </main>

        <script src="${pageContext.request.contextPath}/js/leave.js"></script>
    </body>
</html>