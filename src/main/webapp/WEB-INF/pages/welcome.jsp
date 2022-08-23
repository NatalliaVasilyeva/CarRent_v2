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
    <c:if test="${not empty incorrect}">
        <div class="alert alert-danger">
            <p>${incorrect}</p>
        </div>
    </c:if>
    <%@include file="/WEB-INF/fragment/error_message.jsp" %>
    <%@include file="/WEB-INF/fragment/success_message.jsp" %>
    <c:if test="${not empty userCars}">
        <div class="row">
            <c:forEach items="${userCars}" var="car">
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
                                    <td>
                                        <button class="btn btn-dark btn-xs seecarbtn"
                                                data-toggle="modal"
                                                data-target="#see-car-modal">
                                            <i class="fa fa-eye" aria-hidden="true"></i>
                                        </button>
                                    </td>
                                </c:if>
                                <c:if test="${empty sessionScope.role}">
                                    <small class="text-muted"><fmt:message
                                            key="form.permissions"/></small>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>

    <c:if test="${not empty adminCars}">
        <div class="row">
            <c:forEach items="${adminCars}" var="car">
                <div class="col-sm-6 col-md-4">
                    <div class="col mb-4">
                        <div class="card h-100">
                            <img width="250" height="250" class="card-img-top"
                                 src="data:image/jpg;base64,${car.imageContent}" alt="Card image cap">
                            <div class="card-body">
                                <p hidden class="carId">${car.id}</p>
                                <p hidden class="brand">${car.brand}</p>
                                <p hidden class="model">${car.model}</p>
                                <p hidden class="color">${car.color}</p>
                                <p hidden class="card-text yearOfProduction">${car.yearOfProduction}</p>
                                <p hidden class="card-text carNumber">${car.number}</p>
                                <p hidden class="card-text vin">${car.vin}</p>
                                <p hidden class="card-text isRepaired">${car.repaired}</p>
                                <p hidden class="card-text category">${car.category}</p>


                                <h5 class="card-title text-center"><fmt:message
                                        key="form.order.table.header.description"/>: ${car.brand} ${car.model} </h5>
                                <hr>
                                <p class="card-text color"><fmt:message key="form.color"/>: ${car.color}</p>
                                <hr>
                                <p class="card-text transmission"><fmt:message
                                        key="form.model_transmission"/>: ${car.transmission}</p>
                                <hr>
                                <p class="card-text engineType"><fmt:message
                                        key="form.model_engine"/>: ${car.engineType}</p>
                                <hr>
                                <p class="card-text yearOfProduction"><fmt:message
                                        key="form.car_year"/>: ${car.yearOfProduction}</p>
                                <hr>
                                <p class="card-text carNumber"><fmt:message key="form.car_number"/>: ${car.number}</p>
                                <hr>
                                <p class="card-text vin"><fmt:message key="form.car_vin"/>: ${car.vin}</p>
                                <hr>
                                <p class="card-text isRepaired"><fmt:message
                                        key="form.car_repaired"/>: ${car.repaired}</p>
                                <hr>
                                <p class="card-text pricePerDay"><fmt:message
                                        key="form.category_price"/>: ${car.pricePerDay}$</p>
                            </div>
                            <div class="card-footer">
                                <td>
                                    <button class="btn btn-dark btn-xs editcarbtn"
                                            data-toggle="modal"
                                            data-target="#edit-car-modal">
                                        <i class="fa fa-pencil" aria-hidden="true"></i>
                                    </button>
                                </td>
                                <td>
                                    <button class="btn btn-dark btn-xs deletebtn"
                                            data-toggle="modal"
                                            data-target="#delete-car-modal">
                                        <i class="fa fa-trash" aria-hidden="true"></i>
                                    </button>
                                </td>
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
                    <fmt:message
                            key="form.submit_button"/>
                </button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message
                        key="form.close_button"/></button>
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
                <button type="submit" class="btn btn-primary" form="create-order-form" value="Submit"><fmt:message
                        key="header.user.create-order"/>
                </button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message
                        key="form.close_button"/></button>
            </div>
        </div>
    </div>
</div>
<!-- /Attachment Modal -->

<!-- Attachment Modal -->
<div class="modal fade" id="edit-car-modal" tabindex="-1" role="dialog" aria-labelledby="edit-car-modal-label"
     aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="edit-car-modal-label">Edit car</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" id="attachment-edit-car-body-content">
                <jsp:include page="/WEB-INF/form/edit-car-admin.jsp"/>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-primary" form="edit-car-form" value="Submit"><fmt:message
                        key="form.edit_car"/>
                </button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message
                        key="form.close_button"/></button>
            </div>
        </div>
    </div>
</div>
<!-- /Attachment Modal -->

<!-- Attachment Modal -->
<div class="modal fade" id="delete-car-modal" tabindex="-1" role="dialog" aria-labelledby="delete-car-modal-label"
     aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="delete-car-modal-label">Delete car</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" id="attachment-delete-car-body-content">
                <jsp:include page="/WEB-INF/form/delete-car-admin.jsp"/>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-primary" form="delete-car-form" value="Submit"><fmt:message
                        key="form.delete_car"/>
                </button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message
                        key="form.close_button"/></button>
            </div>
        </div>
    </div>
</div>
<!-- /Attachment Modal -->


<jsp:include page="/WEB-INF/footer/footer.jsp"/>
</body>
</html>