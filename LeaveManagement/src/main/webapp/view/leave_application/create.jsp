<%-- 
    Document   : create
    Created on : Jun 22, 2025, 10:30:40 PM
    Author     : PC
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/view/navbar.jsp" %>
<html>
<head>
    <title>Create Leave Request</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/feature.css">
</head>
<body>
<div class="leave-container">
    <h1>Create Leave Request</h1>
    <form method="post" action="${pageContext.request.contextPath}/leave/create">
        <div class="form-group">
            <label>Title:</label>
            <input type="text" name="title" required/>
        </div>
        <div class="form-group">
            <label>From Date:</label>
            <input type="date" name="fromDate" required/>
        </div>
        <div class="form-group">
            <label>To Date:</label>
            <input type="date" name="toDate" required/>
        </div>
        <div class="form-group">
            <label>Reason:</label>
            <textarea name="reason" required></textarea>
        </div>
        <div class="form-group">
            <input type="submit" value="Submit"/>
        </div>
        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>
    </form>
    <a href="${pageContext.request.contextPath}/tools/leave">Back to Leave Management</a>
</div>
<script src="${pageContext.request.contextPath}/js/leave.js"></script>
<script src="${pageContext.request.contextPath}/js/feature.js"></script>
</body>
</html>
