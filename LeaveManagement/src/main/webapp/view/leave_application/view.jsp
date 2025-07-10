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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/leave.css">
</head>
<body>
<div class="leave-table-container">
    <h1>My Leave Requests</h1>
    <c:if test="${not empty message}">
        <p class="message">${message}</p>
    </c:if>
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>
    <c:choose>
        <c:when test="${empty leaveRequests}">
            <p>No leave requests found.</p>
        </c:when>
        <c:otherwise>
            <table class="leave-table">
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>From Date</th>
                    <th>To Date</th>
                    <th>Reason</th>
                    <th>Status</th>
                    <th>Processed By</th>
                    <th>Processed Reason</th>
                    <th>Actions</th>
                </tr>
                <c:forEach var="request" items="${leaveRequests}">
                    <tr>
                        <td>${request.lid}</td>
                        <td>${request.title}</td>
                        <td>${request.fromDate}</td>
                        <td>${request.toDate}</td>
                        <td>${request.reason}</td>
                        <td class="status-${request.status}">${request.status}</td>
                        <td>${request.processedBy != null ? request.processedBy.fullName : ''}</td>
                        <td>${request.processedReason}</td>
                        <td>
                            <c:if test="${request.status == 'inprogress'}">
                                <a href="${pageContext.request.contextPath}/leave/update?lid=${request.lid}">Edit</a> |
                                <a href="${pageContext.request.contextPath}/leave/update?lid=${request.lid}&action=delete" onclick="return confirm('Are you sure you want to delete this request?')">Delete</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
    <a href="${pageContext.request.contextPath}/leave/create">Create New Request</a> |
    <a href="${pageContext.request.contextPath}/home">Back to Home</a>
</div>
<script src="${pageContext.request.contextPath}/js/leave.js"></script>
</body>
</html>