<%-- 
    Document   : update
    Created on : Jun 22, 2025, 10:31:26 PM
    Author     : PC
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Update Leave Request</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .error { color: red; }
        .form-group { margin-bottom: 15px; }
        label { display: inline-block; width: 150px; }
        input, textarea { width: 300px; }
    </style>
</head>
<body>
<h1>Update Leave Request #${leaveRequest.lid}</h1>
<form method="post" action="${pageContext.request.contextPath}/leave/update">
    <input type="hidden" name="lid" value="${leaveRequest.lid}"/>
    <div class="form-group">
        <label>Title:</label>
        <input type="text" name="title" value="${leaveRequest.title}" required/>
    </div>
    <div class="form-group">
        <label>From Date:</label>
        <input type="date" name="fromDate" value="${leaveRequest.fromDate}" required/>
    </div>
    <div class="form-group">
        <label>To Date:</label>
        <input type="date" name="toDate" value="${leaveRequest.toDate}" required/>
    </div>
    <div class="form-group">
        <label>Reason:</label>
        <textarea name="reason" required>${leaveRequest.reason}</textarea>
    </div>
    <div class="form-group">
        <input type="submit" value="Update"/>
    </div>
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>
</form>
<a href="${pageContext.request.contextPath}/leave/view">Back to My Leave Requests</a>
</body>
</html>
