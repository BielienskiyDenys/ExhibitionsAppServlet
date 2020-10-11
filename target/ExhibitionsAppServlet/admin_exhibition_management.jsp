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
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin_user_management.jsp">Login</a>
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


<h2>Admin page for managing exhibitions</h2>

<div>
    <div>
        <h2>Create Exhibition</h2>
    </div>
    <div>
        <form action="api/create_new_exhibition_admin" method="post">
            <input type="text" name="ex_name" required="required" placeholder="Name"/>
            <input type="date" name="start_date" required="required"/>
            <input type="date" name="end_date" required="required"/>
            <input type="time" name="open_time" required="required"/>
            <input type="time" name="close_time" required="required"/>
            <input type="text" name="description" required="required" placeholder="Description"/>
            <input type="text" name="price" required="required" placeholder="Ticket price"/>
            <input type="text" name="themes" required="required" placeholder="Themes"/>
            <select name="hall_name">
                <option value="SHEVCHENKO">SHEVCHENKO</option>
                <option value="FRANKO">FRANKO</option>
                <option value="KOSTENKO">KOSTENKO</option>
            </select>
            <button type="submit">Create new exhibition</button>
        </form>
    </div>
    <div>
        <c:if test="${creating_exhibition_message!=null}">
            <h5>${creating_exhibition_message}</h5>
        </c:if>
    </div>

</div>

<div>
    <div>
        <h2>Add hall to Exhibition</h2>
    </div>
    <div>
        <form action="api/add_hall_to_exhibition_admin" method="post">
            <input type="text" name="exhibition_id" required="required" placeholder="Exhibition ID"/>
            <select name="hall_name">
                <option value="SHEVCHENKO">SHEVCHENKO</option>
                <option value="FRANKO">FRANKO</option>
                <option value="KOSTENKO">KOSTENKO</option>
            </select>
            <button type="submit">Add hall</button>
        </form>
    </div>
    <c:if test="${add_hall_to_exhibition_message!=null}">
        <div>
            <h5>${add_hall_to_exhibition_message}</h5>
        </div>
    </c:if>
</div>
<div>
    <div>
        <h2>Update exhibition status</h2>
    </div>
</div>
<div>
    <form action="api/manual_expired_update_admin" method="post">
        <button type="submit">Update</button>
    </form>
</div>

<c:if test="${exhibition_management_message!=null}">
    <div>
        <h5>${exhibition_management_message}</h5>
    </div>
</c:if>
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
