<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>ExhibitionsAppServlet</title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">ExhibitionsAppServlet</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/index.jsp">Home</a>
            </li>
            <c:if test="${!sessionScope.get('userRole').equals('ADMIN') && !sessionScope.get('userRole').equals('USER')}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/registration.jsp">Registration</a>
                </li>
            </c:if>

            <c:if test="${sessionScope.get('userRole').equals('ADMIN') || sessionScope.get('userRole').equals('USER')}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/api/logout">Logout</a>
                </li>
            </c:if>

        </ul>
    </div>
</nav>


<h2>Login Page</h2>


<form method="post" action="api/login">
    <input type="text" name="email"/><br>
    <input type="password" name = "pass"/><br>
    <input class="button" type="submit" value="Войти">
</form>

</body>
</html>
