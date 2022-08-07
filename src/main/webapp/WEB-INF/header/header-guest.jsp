<%--
  Created by IntelliJ IDEA.
  User: natallia.vasilyeva
  Date: 25/07/2022
  Time: 00:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
    <c:when test="${not empty sessionScope.locale}"> <fmt:setLocale value="${sessionScope.locale}"/></c:when>
    <c:when test="${empty sessionScope.locale}"> <fmt:setLocale value="${sessionScope.locale = 'en_US'}"/></c:when>
</c:choose>

<fmt:setBundle basename="content"/>
<script src="${pageContext.request.contextPath}/js/header/header-guest.js"></script>
<script src="${pageContext.request.contextPath}/js/form/sing-up.js"></script>

<nav class="navbar navbar-light" style="background-color: #e3f2fd;">

    <div class="d-flex">
        <div class="p-2">
            <c:choose>
                <c:when test="${not empty user}">
                    <a class="nav-brand" href="/"></a>
                </c:when>
                <c:otherwise>
                    <fmt:message key="header.car_rent"/> <span class="fa fa-leaf" align="center"></span>
                </c:otherwise>
            </c:choose>

        </div>
    </div>

    <ul class="navbar-nav">
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
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


    <div class="d-flex flex-row">
        <div class="p-2">
            <a href="" data-toggle="modal" data-target="#sign-in-modal">
                <fmt:message key="form.login"/>
            </a>
        </div>
        <div class="p-2">
            <a href="" data-toggle="modal" data-target="#sign-up-modal">
                <fmt:message key="form.register"/>
            </a>
        </div>
    </div>
</nav>
<div class="modal fade" id="sign-in-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title">
                    <fmt:message key="form.login"/>
                </h1>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="/WEB-INF/form/sign-in.jsp"/>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="sign-up-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title">
                    <fmt:message key="form.register"/>
                </h1>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="/WEB-INF/form/sign-up.jsp"/>
            </div>
        </div>
    </div>
</div>