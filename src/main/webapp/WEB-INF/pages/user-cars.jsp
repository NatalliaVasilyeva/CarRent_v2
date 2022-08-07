<%--
  Created by IntelliJ IDEA.
  User: natallia.vasilyeva
  Date: 17/07/2022
  Time: 20:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<c:choose>--%>
<%--    <c:when test="${not empty sessionScope.locale}">--%>
<%--        <fmt:setLocale value="${sessionScope.locale}"/>--%>
<%--    </c:when>--%>
<%--    <c:otherwise>--%>
<%--        <fmt:setLocale value="en_US"/>--%>
<%--    </c:otherwise>--%>
<%--</c:choose>--%>
<fmt:setBundle basename="content"/>

<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/include/common.html"/>
    <jsp:include page="/WEB-INF/include/i18n.html"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title>Car list for user</title>
</head>
<body>
<jsp:include page="/WEB-INF/header/header.jsp"/>
<h2 align="center">Welcome ${sessionScope.user.firstName}!</h2>
<h3 align="center">There are all our available car!</h3>

<c:if test="${not empty incorrect}">
    <div class="alert alert-danger">
        <p>${incorrect}</p>
    </div>
</c:if>
<%@include file="/WEB-INF/fragment/error_message.jsp" %>
<%@include file="/WEB-INF/fragment/success_message.jsp" %>

<c:if test="${empty cars}">
    <h1 align="center">There are not available cars!</h1>
</c:if>
<c:if test="${not empty cars}">
    <table class="table table-dark">
        <thead>
        <tr>
            <th scope="col" hidden=true>id</th>
            <th scope="col">Brand</th>
            <th scope="col">Model</th>
            <th scope="col" hidden>Transmission</th>
            <th scope="col" hidden>Engine type</th>
            <th scope="col" hidden>Color</th>
            <th scope="col" hidden>Year of production</th>
            <th scope="col" hidden>Payment Per Day</th>
            <th scope="col">Image</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cars}" var="car">
            <tr>
                <td hidden=true>${car.id}</td>
                <td>${car.brand}</td>
                <td>${car.model}</td>
                <td hidden>${car.transmission}</td>
                <td hidden>${car.engineType}</td>
                <td hidden>${car.color}</td>
                <td hidden>${car.yearOfProduction}</td>
                <td hidden>${car.pricePerDay}</td>
                <td>
                    <img width="200" height="150" src="data:image/jpg;base64,${car.imageContent}" alt="NO IMAGE">
                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/car-user?id=${car.id}" method="GET">
                        <input type="text" name="id" value="${car.id}" hidden="true"><br/>
                        <input class="btn btn-warning" type="submit" value="See more information"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
<a href="${pageContext.request.contextPath}/user-cars">
    Back to main page
</a>
</body>
</html>