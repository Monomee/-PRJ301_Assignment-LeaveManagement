<%
    String fullname = (String) session.getAttribute("fullname");
    if (fullname == null) fullname = "User";
%>
<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<nav class="navbar">
    <div class="navbar-left">
        <a href="/home" class="navbar-logo">HR Management</a>
        <a href="/home" class="navbar-link">Home</a>
        <a href="/news" class="navbar-link">News</a>
        <a href="/contact" class="navbar-link">Contact</a>
        <a href="/tools/leave" class="navbar-link">Tools</a>
    </div>
    <div class="navbar-right">
        <span class="navbar-user"><i class="fa-solid fa-user"></i>${sessionScope.user.fullName}</span>
        <a href="/logout" class="navbar-logout">Logout</a>
    </div>
</nav>
<link rel="stylesheet" href="/css/navbar.css">
<script src="/js/navbar.js"></script> 