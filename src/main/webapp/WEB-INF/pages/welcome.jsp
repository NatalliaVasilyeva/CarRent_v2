<%--
  Created by IntelliJ IDEA.
  User: natallia.vasilyeva
  Date: 25/07/2022
  Time: 00:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
    <title><fmt:message key="guest.welcome.title"/></title>
    <jsp:include page="/WEB-INF/include/common.html"/>
    <jsp:include page="/WEB-INF/include/i18n.html"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/car/cars-info.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/order/user-order.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/header/header-user.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/header/header.jsp"/>
<div class="container">
    <div align="center">
        <h1 id="h1-welcome">
            <fmt:message key="guest.welcome.h-message"/>
        </h1>
    </div>
    <c:if test="${not empty incorrect}">
        <div class="alert alert-danger">
            <p>${incorrect}</p>
        </div>
    </c:if>
    <%@include file="/WEB-INF/fragment/error_message.jsp" %>
    <%@include file="/WEB-INF/fragment/success_message.jsp" %>
    <c:if test="${not empty cars}">
        <div class="row">
            <c:forEach items="${cars}" var="car">
                <div class="col-sm-6 col-md-4">
                    <div class="col mb-4">
                        <div class="card h-100">
                            <img width="250" height="250" class="card-img-top"
                                 src="data:image/jpg;base64,${car.imageContent}" alt="Card image cap">
                            <div class="card-body">
                                <p hidden class="carId">${car.id}</p>
                                <p hidden class="brand">${car.brand}</p>
                                <p hidden class="model">${car.model}</p>
                                <p hidden class="transmission">${car.transmission}</p>
                                <p hidden class="engineType">${car.engineType}</p>
                                <p hidden class="color">${car.color}</p>
                                <p hidden class="yearOfProduction">${car.yearOfProduction}</p>
                                <h5 class="card-title">${car.brand} ${car.model} </h5>
                                <p class="card-text pricePerDay">${car.pricePerDay}$</p>
                            </div>
                            <div class="card-footer">
                                <c:if test="${not empty sessionScope.role}">
<%--                                    <form action="${pageContext.request.contextPath}/car-user?id=${car.id}"--%>
<%--                                          method="GET">--%>
<%--                                        <input type="text" name="id" value="${car.id}" hidden="true"><br/>--%>
<%--                                        <input class="btn btn-warning" type="submit" value="See more information"/>--%>
<%--                                    </form>--%>
                                    <td>
                                        <button class="btn btn-dark btn-xs seecarbtn"
                                                data-toggle="modal"
                                                data-target="#see-car-modal">
                                            <i class="fa fa-eye" aria-hidden="true"></i>
                                        </button>
                                    </td>
                                </c:if>
                                <c:if test="${empty sessionScope.role}">
                                    <small class="text-muted">You don't have permission to see more info. Please,
                                        sign-in or
                                        sign-up</small>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
</div>

<!-- Attachment Modal -->
<div class="modal fade" id="see-car-modal" tabindex="-1" role="dialog" aria-labelledby="see-car-modal-label"
     aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="see-car-modal-label">Edit Data</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" id="attachment-body-content">
                <jsp:include page="/WEB-INF/form/see-car-user.jsp"/>
            </div>
            <div class="modal-footer">
                <button class="btn btn-dark btn-xs showorderform"
                        data-toggle="modal"
                        data-target="#create-order-modal">
                    <i class="fa fa-pencil" aria-hidden="true"></i>
                    Make order
                </button>
<%--                <button type="button" class="btn btn-primary showorderform" form="see-car-form" value="Button">Make order</button>--%>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<!-- /Attachment Modal -->

<!-- Attachment Modal -->
<div class="modal fade" id="create-order-modal" tabindex="-1" role="dialog" aria-labelledby="see-car-modal-label"
     aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="create-order-modal-label">Edit Data</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" id="attachment-create-order-body-content">
                <jsp:include page="/WEB-INF/form/create-order-user.jsp"/>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-primary" form="create-order-form" value="Submit">Create order</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<!-- /Attachment Modal -->

<jsp:include page="/WEB-INF/footer/footer.jsp"/>
</body>
</html>