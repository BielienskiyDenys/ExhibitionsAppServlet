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
            <c:if test="${!sessionScope.get('userRole').equals('ADMIN') && !sessionScope.get('userRole').equals('USER')}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/login.jsp">Login</a>
                </li>
            </c:if>
            <c:if test="${!sessionScope.get('userRole').equals('ADMIN') && !sessionScope.get('userRole').equals('USER')}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/registration.jsp">Registration</a>
                </li>
            </c:if>
            <c:if test="${sessionScope.get('userRole').equals('ADMIN')}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin_user_management.jsp">User
                        Management</a>
                </li>
            </c:if>
            <c:if test="${sessionScope.get('userRole').equals('ADMIN')}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin_exhibition_management.jsp">Exhibition
                        Management</a>
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


<h2>My tickets</h2>

<div>
    <div>
        <h2>Tickets Search</h2>
    </div>
    <div>
        <form action="api/show_my_tickets">
            <select name="ticket_status">
                <option value="ACTIVE">Active</option>
                <option value="EXPIRED">Expired</option>
                <option value="WAITING_REFUND">Waiting for refund</option>
                <option value="REFUNDED">Refunded</option>
            </select>
            <button type="submit">Show</button>
        </form>
    </div>
    <c:if test="${show_my_tickets_message!=null}">
        <div>
            <h5>${show_my_tickets_message}</h5>
        </div>
    </c:if>
    <div>
        <c:forEach var="i" items="${ticketList}">
            <div>
                <b>${i.id}</b>
                <p>${i.exhibition}</p>
                <p>${i.user}</p>
                <p>${i.ticketStatus}</p>
            </div>
        </c:forEach>
    </div>
</div>
<div>
    <table>
        <tr>
            <th>Name</th>
            <th>id</th>
        </tr>
        <c:forEach var="i" items="${exhibitionList}">
            <tr>
                <td>${i.exName}</td>
                <td>${i.id}</td>
            </tr>
            <c:if test="${sessionScope.get('userRole').equals('ADMIN') || sessionScope.get('userRole').equals('USER')}">
                <tr>
                    <td>
                        <form action="api/buy_ticket" method="post">
                            <input type="hidden" name="exhibition_id" value="${i.id}"/>
                            <input type="hidden" name="user_id" value="${sessionScope.get('currentUser').getId()}"/>
                            <select name="quantity">
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                            </select>
                            <button type="submit">Buy ticket</button>
                        </form>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
        <c:if test="${buy_ticket_message!=null}">
            <tr>
                <td><h5>${buy_ticket_message}</h5></td>
            </tr>
        </c:if>
    </table>
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
