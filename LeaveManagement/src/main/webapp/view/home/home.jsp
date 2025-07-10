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
    </head>
    <body>
        <div style="display: flex; justify-content: space-between">
            <h1>Welcome, ${sessionScope.user.fullName}</h1>
            <a href="/logout">Logout</a>
        </div>      
        <h2>Your Roles</h2>
        <table>
            <c:forEach var="role" items="${roles}">
                <tr>
                    <td>${role.roleName}</td>
                </tr>
            </c:forEach>
        </table>
        <h2>Your Features</h2>
        <table>
            <c:forEach var="feature" items="${features}">
                <tr>
                    <c:if test="${feature.entryPoint != '/leave/update'}">
                        <td><a href="${pageContext.request.contextPath}${feature.entryPoint}">${feature.featureName}</a></td>
                        </c:if>
                </tr>
            </c:forEach>
        </table>

    </body>
</html>
