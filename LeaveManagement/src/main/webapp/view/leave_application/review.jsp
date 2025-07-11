<%-- 
    Document   : review
    Created on : Jun 22, 2025, 10:31:45 PM
    Author     : PC
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/view/navbar.jsp" %>
<html>
    <head>
        <title>Review Leave Requests</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/feature.css">
    </head>
    <body>
        <div class="leave-table-container">
            <h1>Review Leave Requests</h1>
            <c:if test="${not empty message}">
                <p class="message">${message}</p>
            </c:if>
            <c:if test="${not empty error}">
                <p class="error">${error}</p>
            </c:if>
            <c:choose>
                <c:when test="${leaveRequests == null || leaveRequests.isEmpty()}">
                    <p>No leave requests to review.</p>
                </c:when>
                <c:otherwise>
                    <table class="leave-table">
                        <tr>
                            <th>ID</th>
                            <th>Title</th>
                            <th>Employee</th>
                            <th>From Date</th>
                            <th>To Date</th>
                            <th>Reason</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                        <c:forEach var="request" items="${leaveRequests}">
                            <tr>
                                <td><c:out value="${request.lid}" default="N/A"/></td>
                                <td><c:out value="${request.title}" default="(No title)"/></td>
                                <td><c:out value="${request.user.fullName}" default="(No name)"/></td>
                                <td><c:out value="${request.fromDate}" default="-" /></td>
                                <td><c:out value="${request.toDate}" default="-" /></td>
                                <td><c:out value="${request.reason}" default="-" /></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${request.status == 'approved'}">
                                            <span class="status-approved">Approved</span>
                                        </c:when>
                                        <c:when test="${request.status == 'rejected'}">
                                            <span class="status-rejected">Rejected</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="status-pending">Inprocess</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${request.status == 'approved'}">
                                            <span class="status-approved">Approved</span>
                                        </c:when>
                                        <c:when test="${request.status == 'rejected'}">
                                            <span class="status-rejected">Rejected</span>
                                        </c:when>
                                        <c:otherwise>
                                            <form method="post" action="${pageContext.request.contextPath}/leave/review" style="display:inline;">
                                                <input type="hidden" name="lid" value="${request.lid}"/>
                                                <div class="form-group">
                                                    <label>Reason:</label>
                                                    <textarea name="reason" ></textarea>
                                                </div>
                                                <input type="submit" name="action" value="approve" onclick="return confirm('Approve this request?')"/>
                                                <input type="submit" name="action" value="reject" onclick="return confirm('Reject this request?')"/>
                                            </form>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:otherwise>
            </c:choose>
            <a href="${pageContext.request.contextPath}/tools/leave">Back to Leave Management</a>
        </div>
        <script src="${pageContext.request.contextPath}/js/leave.js"></script>
        <script src="${pageContext.request.contextPath}/js/feature.js"></script>
    </body>
</html>
