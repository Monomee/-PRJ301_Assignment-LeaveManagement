<%-- 
    Document   : view
    Created on : Jun 22, 2025, 10:31:55 PM
    Author     : PC
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>My Leave Requests</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .message { color: green; }
    </style>
</head>
<body>
<h1>My Leave Requests</h1>
<c:if test="${not empty message}">
    <p class="message">${message}</p>
</c:if>
<c:choose>
    <c:when test="${empty leaveRequests}">
        <p>No leave requests found.</p>
    </c:when>
    <c:otherwise>
        <table>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>From Date</th>
                <th>To Date</th>
                <th>Reason</th>
                <th>Status</th>
                <th>Processed By</th>
                <th>Processed Reason</th>
            </tr>
            <c:forEach var="request" items="${leaveRequests}">
                <tr>
                    <td>${request.lid}</td>
                    <td>${request.title}</td>
                    <td>${request.fromDate}</td>
                    <td>${request.toDate}</td>
                    <td>${request.reason}</td>
                    <td>${request.status}</td>
                    <td>${request.processedBy != null ? request.processedBy.fullName : ''}</td>
                    <td>${request.processedReason}</td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>
<a href="${pageContext.request.contextPath}/leave/create">Create New Request</a> |
<a href="${pageContext.request.contextPath}/home">Back to Home</a>
</body>
</html>