<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>ExhibitionsAppServlet</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
          crossorigin="anonymous">
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
            <c:if test="${sessionScope.get('userRole').equals('ADMIN')}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin_exhibition_management.jsp">Exhibition Management</a>
                </li>
            </c:if>
            <c:if test="${sessionScope.get('userRole').equals('ADMIN')}">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/api/logout">Logout</a>
            </li>
            </c:if>
            <c:if test="${sessionScope.get('userRole').equals('ADMIN')}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/my_tickets.jsp">My tickets</a>
                </li>
            </c:if>

        </ul>
    </div>
</nav>


<h2>Admin page for managing users</h2>

<div>
    <div>
        <h2>Exhibition Search</h2>
    </div>
    <div>
        <button>Find All</button>
    </div>
    <div>
        <div>
            Search by:
        </div>
        <div>
            <form>
                <select>
                    <option>Name</option>
                    <option>Theme</option>
                </select>
                <input type="text"/>
                <input type="submit"/>
            </form>
        </div>
        <div>
            <div>Date:</div>
            <form>
                <input type="date"/>
                <input type="date"/>
                <input type="submit"/>
            </form>
        </div>
        <div>
            <div>Status:</div>
            <form>
                <select>
                    <option>Active</option>
                    <option>Canceled</option>
                    <option>Ended</option>
                </select>
                <input type="submit"/>
            </form>
        </div>
    </div>
</div>


<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
        integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
        integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
        crossorigin="anonymous"></script>
</body>
</html>
