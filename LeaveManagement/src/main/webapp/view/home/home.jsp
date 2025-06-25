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
        <h1>Welcome, ${sessionScope.user.fullName}</h1>
    <h2>Your Roles</h2>
    <table>
        <tr>
            <th>Role Name</th>
        </tr>
        <c:forEach var="role" items="${roles}">
            <tr>
                <td>${role.roleName}</td>
            </tr>
        </c:forEach>
    </table>
    <h2>Your Features</h2>
    <table>
        <tr>
            <th>Feature Name</th>
            <th>Entry Point</th>
        </tr>
        <c:forEach var="feature" items="${features}">
            <tr>
                <td>${feature.featureName}</td>
                <td><a href="${pageContext.request.contextPath}${feature.entryPoint}">Click here</a></td>            
            </tr>
        </c:forEach>
    </table>
    
    </body>
</html>
