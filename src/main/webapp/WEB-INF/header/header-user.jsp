<%--
  Created by IntelliJ IDEA.
  User: natallia.vasilyeva
  Date: 25/07/2022
  Time: 15:28
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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/header/header-user.js"></script>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">Car rent</a>
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
            <li class="nav-item">
                <a class="nav-link waves-effect waves-light" href="${pageContext.request.contextPath}/user-orders"><fmt:message
                        key="header.user.orders"/></a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link waves-effect waves-light my-2 my-sm-0 dropdown-toggle" href="#" type="button" id="navbarDropdownMenuLink-3"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Find cars
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
                       data-target="#find-by-category-modal"><fmt:message key="form.find_car_category"/></a>
                </div>
            </li>
        </ul>
        <ul class="navbar-nav ml-auto nav-flex-icons">
            <li class="nav-item dropdown">
                <a class="nav-link waves-effect waves-light dropdown-toggle" href="#" type="button" id="dropdownMenuButton"
                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <span class="flag-icon flag-icon-us"></span> <fmt:message key="label.lang.en"/>
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
                <butaton class="nav-link waves-effect waves-light dropdown-toggle" href="#" type="button" id="dropdownMenuButton1"
                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <span class="flag-icon flag-icon-us"></span> <fmt:message key="label.about"/>
                </butaton>
                <div class="dropdown-menu dropdown-menu-right dropdown-unique"
                     aria-labelledby="dropdownMenuButton1">
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/show-payment"><span
                            class="flag-icon flag-icon-us"></span>
                        <fmt:message key="label.about.payment"/></a>
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/show-roads"><span
                            class="flag-icon flag-icon-ru"></span>
                        <fmt:message key="label.about.road"/></a>
                </div>
            </li>

            <li class="nav-item">
                <a class="nav-link waves-effect waves-light href="${pageContext.request.contextPath}/contacts">
                <fmt:message key="label.about.contact"/></a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false"><i class="fa fa-user"></i>
                </a>
                <div class="dropdown-menu dropdown-menu-right dropdown-unique" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/user-profile"><fmt:message
                            key="header.user.profile"/></a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/user-settings"><fmt:message key="header.user.settings"/></a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/logout" class="nav-link d-flex align-items-center"><fmt:message key="form.logout"/></a>
                </div>
            </li>
        </ul>
    </div>
</nav>

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
                <jsp:include page="/WEB-INF/form/find-cars-by-brand.jsp"/>
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
                <jsp:include page="/WEB-INF/form/find-cars-by-category.jsp"/>
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
                <jsp:include page="/WEB-INF/form/find-cars-by-year.jsp"/>
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
                <jsp:include page="/WEB-INF/form/find-cars-by-years.jsp"/>
            </div>
        </div>
    </div>
</div>