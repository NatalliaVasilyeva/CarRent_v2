<%--
  Created by IntelliJ IDEA.
  User: natallia.vasilyeva
  Date: 25/07/2022
  Time: 15:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<script src="../../js/header/header-admin.js"></script>
<nav class="navbar navbar-expand-lg bg-light">
    <a class="nav-brand" href="/"><fmt:message key="header.car_rent"/> <span class="fa fa-leaf"></span></a>
    <ul class="navbar-nav mr-auto">
        <li class="nav-item">
            <a class="nav-link" href="javascript:goToBusinessProfile()">
                <fmt:message key="header.admin.business-profile"/>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="javascript:goToOrders()">
                <fmt:message key="header.admin.request.orders"/>
            </a>
        </li>
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown" aria-haspopup="true"
               aria-expanded="false">
                <fmt:message key="header.language"/>
            </a>
            <div class="dropdown-menu">
                <ul>
                    <li><a href="${pageContext.request.contextPath}/change-language?language=en_US"><fmt:message
                            key="label.lang.en"/></a></li>
                    <li><a href="${pageContext.request.contextPath}/change-language?language=ru_RU"><fmt:message
                            key="label.lang.ru"/></a></li>
                </ul>
            </div>
        </li>

    </ul>
    <ul class="navbar-nav">
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" aria-haspopup="true"
               aria-expanded="false">
                <fmt:message key="header.admin.manage"/>
            </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                <a class="dropdown-item" href="javascript:goToManageOrders()">
                    <fmt:message key="header.admin.manage.orders"/>
                </a>
                <a class="dropdown-item" href="javascript:goToManageUsers()">
                    <fmt:message key="header.admin.manage.users"/>
                </a>
<%--                <a class="dropdown-item" href="javascript:goToManageUsers()">--%>
<%--                    <fmt:message key="header.admin.manage."/>--%>
<%--                </a>--%>
            </div>
        </li>
    </ul>
    <ul class="navbar-nav">
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" aria-haspopup="true"
               aria-expanded="false">
                <c:out value="${sessionScope.username}"/>
            </a>
            <div class="dropdown-menu">
                <a class="dropdown-item" href="javascript:goToProfile()">
                    <fmt:message key="header.admin.profile"/>
                </a>
                <a class="dropdown-item" href="javascript:goToSettings()">
                    <fmt:message key="header.admin.settings"/>
                </a>
                <a class="dropdown-item" href="javascript:signOut()">
                    <fmt:message key="header.admin.sign-out"/>
                </a>
            </div>
        </li>
    </ul>
</nav>
<form id="welcome-form" action="welcome" method="get"></form>