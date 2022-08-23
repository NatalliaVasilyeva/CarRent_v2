<%--
  Created by IntelliJ IDEA.
  User: natallia.vasilyeva
  Date: 25/07/2022
  Time: 15:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:choose>
    <c:when test="${not empty sessionScope.locale}"> <fmt:setLocale value="${sessionScope.locale}"/></c:when>
    <c:when test="${empty sessionScope.locale}"> <fmt:setLocale value="${sessionScope.locale = 'en_US'}"/></c:when>
</c:choose>

<fmt:setBundle basename="content"/>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#"><fmt:message
            key="form.car_rent"/></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent-3"
            aria-controls="navbarSupportedContent-3" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent-3">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/welcome"><fmt:message
                        key="header.main_name"/><span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link waves-effect waves-light my-2 my-sm-0 dropdown-toggle" href="#" type="button"
                   id="navbarDropdownMenuLink-2"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><fmt:message
                        key="form.find_orders_button"/>
                </a>
                <div class="dropdown-menu dropdown-menu-right dropdown-unique"
                     aria-labelledby="navbarDropdownMenuLink-2">
                    <a class="dropdown-item" href="" data-toggle="modal"
                       data-target="#find-orders-by-all-modal"><fmt:message
                            key="form.find_order_admin_all"/></a>
                    <a class="dropdown-item" href="" data-toggle="modal"
                       data-target="#find-orders-by-date-modal"><fmt:message
                            key="form.find_order_admin_date"/></a>
                    <a class="dropdown-item" href="" data-toggle="modal"
                       data-target="#find-orders-by-dates-modal"><fmt:message
                            key="form.find_order_admin_dates"/></a>
                    <a class="dropdown-item" href="" data-toggle="modal"
                       data-target="#find-orders-by-car-modal"><fmt:message
                            key="form.find_order_admin_car"/></a>
                    <a class="dropdown-item" href="" data-toggle="modal"
                       data-target="#find-orders-by-status-modal"><fmt:message
                            key="form.find_order_admin_status"/></a>
                    <a class="dropdown-item" href="" data-toggle="modal"
                       data-target="#find-orders-by-with-accidents-modal"><fmt:message
                            key="form.find_order_admin_accidents"/></a>
                </div>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link waves-effect waves-light my-2 my-sm-0 dropdown-toggle" href="#" type="button"
                   id="navbarDropdownMenuLink-3"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><fmt:message
                        key="form.find_car_button"/>
                </a>
                <div class="dropdown-menu dropdown-menu-right dropdown-unique"
                     aria-labelledby="navbarDropdownMenuLink-3">
                    <a class="dropdown-item" href="" data-toggle="modal" data-target="#find-by-brand-modal"><fmt:message
                            key="form.find_car_brand"/></a>
                    <a class="dropdown-item" href="" data-toggle="modal" data-target="#find-by-year-modal"><fmt:message
                            key="form.find_car_year"/></a>
                    <a class="dropdown-item" href="" data-toggle="modal" data-target="#find-by-years-modal"><fmt:message
                            key="form.find_car_years"/></a>
                    <a class="dropdown-item" href="" data-toggle="modal"
                       data-target="#find-by-category-modal"><fmt:message
                            key="form.find_car_category"/></a>
                    <a class="dropdown-item" href="" data-toggle="modal"
                       data-target="#find-by-accidents-modal"><fmt:message
                            key="form.find_car_admin_accidents"/></a>
                    <a class="dropdown-item" href="" data-toggle="modal"
                       data-target="#find-by-repair-modal"><fmt:message
                            key="form.find_car_admin_repair"/></a>
                    <a class="dropdown-item" href="" data-toggle="modal"
                       data-target="#find-by-number-modal"><fmt:message
                            key="form.find_car_admin_number"/></a>
                </div>
            </li>
        </ul>
        <ul class="navbar-nav ml-auto nav-flex-icons">
            <li class="nav-item dropdown">
                <a class="nav-link waves-effect waves-light dropdown-toggle" href="#" type="button"
                   id="dropdownMenuButton"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <span class="flag-icon flag-icon-us"></span> <fmt:message key="label.lang.label"/>
                </a>
                <div class="dropdown-menu dropdown-menu-right dropdown-unique"
                     aria-labelledby="dropdownMenuButton">
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/change-language?language=en_US"><span
                            class="flag-icon flag-icon-us"></span>
                        <fmt:message key="label.lang.en"/></a>
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/change-language?language=ru_RU"><span
                            class="flag-icon flag-icon-ru"></span>
                        <fmt:message key="label.lang.ru"/></a>
                </div>
            </li>

            <li class="nav-item dropdown">
                <a class="nav-link waves-effect waves-light my-2 my-sm-0 dropdown-toggle" href="#" type="button"
                   id="navbarDropdownMenuLink-5"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><fmt:message
                        key="form.create_admin_button"/>
                </a>
                <div class="dropdown-menu dropdown-menu-right dropdown-unique"
                     aria-labelledby="navbarDropdownMenuLink-5">
                    <a class="dropdown-item" href="" data-toggle="modal" data-target="#create-brand-modal"><fmt:message
                            key="form.create_brand"/></a>
                    <a class="dropdown-item" href="" data-toggle="modal" data-target="#create-model-modal"><fmt:message
                            key="form.create_model"/></a>
                    <a class="dropdown-item" href="" data-toggle="modal" data-target="#create-price-modal"><fmt:message
                            key="form.create_price"/></a>
                    <a class="dropdown-item" href="" data-toggle="modal"
                       data-target="#create-category-modal"><fmt:message
                            key="form.create_category"/></a>
                    <a class="dropdown-item" href="" data-toggle="modal" data-target="#create-car-modal"><fmt:message
                            key="form.create_car"/></a>
                    <a class="dropdown-item" href="" data-toggle="modal"
                       data-target="#create-accident-modal"><fmt:message
                            key="form.create_accident"/></a>
                </div>
            </li>

            <li class="nav-item">
                <a class="nav-link waves-effect waves-light" href="${pageContext.request.contextPath}/report">
                <fmt:message key="user.settings.download-admin-report"/></a>
            </li>

            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false"><i class="fa fa-user"></i>
                </a>
                <div class="dropdown-menu dropdown-menu-right dropdown-unique" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/user-profile"><fmt:message
                            key="header.user.profile"/></a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/user-settings"><fmt:message
                            key="header.user.settings"/></a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/logout"
                       class="nav-link d-flex align-items-center"><fmt:message key="form.logout"/></a>
                </div>
            </li>
        </ul>
    </div>
</nav>

<!-- find orders -->

<div class="modal fade" id="find-orders-by-all-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title">
                    <fmt:message key="form.find_order_admin_all"/>
                </h1>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="/WEB-INF/form/find-orders-all-admin.jsp"/>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="find-orders-by-date-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title">
                    <fmt:message key="form.find_order_admin_date"/>
                </h1>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="/WEB-INF/form/find-orders-by-date-admin.jsp"/>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="find-orders-by-dates-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title">
                    <fmt:message key="form.find_order_admin_dates"/>
                </h1>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="/WEB-INF/form/find-orders-by-dates-admin.jsp"/>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="find-orders-by-car-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title">
                    <fmt:message key="form.find_order_admin_car"/>
                </h1>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="/WEB-INF/form/find-orders-by-car-number-admin.jsp"/>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="find-orders-by-status-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title">
                    <fmt:message key="form.find_order_admin_status"/>
                </h1>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="/WEB-INF/form/find-orders-by-status-admin.jsp"/>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="find-orders-by-with-accidents-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title">
                    <fmt:message key="form.find_order_admin_accidents"/>
                </h1>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="/WEB-INF/form/find-orders-with-accidents-admin.jsp"/>
            </div>
        </div>
    </div>
</div>

<!--/ find orders -->

<!-- create -->
<div class="modal fade" id="create-brand-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title">
                    <fmt:message key="form.create_brand"/>
                </h1>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="/WEB-INF/form/create-brand-admin.jsp"/>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="create-accident-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title">
                    <fmt:message key="form.create_accident"/>
                </h1>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="/WEB-INF/form/add-order-accident.jsp"/>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-primary" form="add-accident-form" value="Submit">Submit
                </button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="create-price-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title">
                    <fmt:message key="form.create_price"/>
                </h1>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="/WEB-INF/form/create-price-admin.jsp"/>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="create-category-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title">
                    <fmt:message key="form.create_category"/>
                </h1>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="/WEB-INF/form/create-category-admin.jsp"/>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="create-model-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title">
                    <fmt:message key="form.create_model"/>
                </h1>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="/WEB-INF/form/create-model-admin.jsp"/>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="create-car-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title">
                    <fmt:message key="form.create_car"/>
                </h1>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="/WEB-INF/form/create-car-admin.jsp"/>
            </div>
        </div>
    </div>
</div>

<!-- /create -->

<!-- find car -->
<div class="modal fade" id="find-by-brand-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title">
                    <fmt:message key="form.find_car_brand"/>
                </h1>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="/WEB-INF/form/find-cars-by-brand-admin.jsp"/>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="find-by-year-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title">
                    <fmt:message key="form.find_car_year"/>
                </h1>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="/WEB-INF/form/find-cars-by-year-admin.jsp"/>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="find-by-years-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title">
                    <fmt:message key="form.find_car_years"/>
                </h1>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="/WEB-INF/form/find-cars-by-years-admin.jsp"/>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="find-by-category-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title">
                    <fmt:message key="form.find_car_category"/>
                </h1>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="/WEB-INF/form/find-cars-by-category-admin.jsp"/>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="find-by-accidents-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title">
                    <fmt:message key="form.find_car_admin_accidents"/>
                </h1>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="/WEB-INF/form/find-cars-with-accidents-admin.jsp"/>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="find-by-repair-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title">
                    <fmt:message key="form.find_car_admin_repair"/>
                </h1>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="/WEB-INF/form/find-cars-under-repair-admin.jsp"/>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="find-by-number-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title">
                    <fmt:message key="form.find_car_admin_number"/>
                </h1>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="/WEB-INF/form/find-cars-by-number-admin.jsp"/>
            </div>
        </div>
    </div>
</div>