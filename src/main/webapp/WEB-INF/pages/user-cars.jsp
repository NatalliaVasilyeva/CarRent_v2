<%--
  Created by IntelliJ IDEA.
  User: natallia.vasilyeva
  Date: 17/07/2022
  Time: 20:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt"  prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title>Car list for user</title>
</head>
<body>
<h2>Welcome ${sessionScope.user.firstName}!</h2>
<table class="table table-dark">
    <thead>
    <tr>
        <th scope="col" hidden=true>id</th>
        <th scope="col">Brand</th>
        <th scope="col">Model</th>
        <th scope="col">Transmission</th>
        <th scope="col">Engine type</th>
        <th scope="col">Color</th>
        <th scope="col">Year of production</th>
        <th scope="col">Payment Per Day</th>
        <th scope="col">Image</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${cars}" var="car">
            <tr>
                <th scope="row" hidden=true>${car.id}</th>
                <td>${car.brand}</td>
                <td>${car.model}</td>
                <td>${car.transmission}</td>
                <td>${car.engineType}</td>
                <td>${car.color}</td>
                <td>${car.yearOfProduction}</td>
                <td>${car.pricePerDay}</td>
                <td>${car.pricePerDay}</td>
                <td>
                    <img width="150" height="100" src="${pageContext.request.contextPath}/img/${car.image}" alt="NO IMAGE">
                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/api/v1/user/cars/byId" method="GET">
                        <input type="text" name="id" value="${car.id}" hidden="true"><br/>
                        <input class="btn btn-warning" type="submit" value="See more information"/>
                    </form>
                </td>
            </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>