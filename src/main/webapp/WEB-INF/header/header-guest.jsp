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

<%--<nav class="navbar navbar-light" style="background-color: #e3f2fd;">--%>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand waves-effect waves-light" class="fa fa-leaf" href="#">Car rent</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent-4"
            aria-controls="navbarSupportedContent-4" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent-4">
        <%--    <div class="d-flex">--%>
        <%--        <div class="p-2">--%>
        <%--            <c:choose>--%>
        <%--                <c:when test="${not empty user}">--%>
        <%--                    <a class="nav-brand" href="/"></a>--%>
        <%--                </c:when>--%>
        <%--                <c:otherwise>--%>
        <%--                    <fmt:message key="header.car_rent"/> <span class="fa fa-leaf" align="center"></span>--%>
        <%--                </c:otherwise>--%>
        <%--            </c:choose>--%>

        <%--        </div>--%>
        <%--    </div>--%>
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link waves-effect waves-light" href="${pageContext.request.contextPath}/welcome"><fmt:message
                            key="header.main_name"/><span class="sr-only">(current)</span></a>
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
                <li class="nav-item">
                    <a class="nav-link waves-effect waves-light" href="" data-toggle="modal" data-target="#sign-in-modal">
                        <fmt:message key="form.login"/>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link waves-effect waves-light" href="" data-toggle="modal" data-target="#sign-up-modal">
                        <fmt:message key="form.register"/>
                    </a>
                </li>
            </ul>







<%--        <ul class="navbar-nav">--%>
<%--            <li class="nav-item dropdown">--%>
<%--                <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">--%>
<%--                    <fmt:message key="header.language"/>--%>
<%--                </a>--%>
<%--                <div class="dropdown-menu">--%>
<%--                    <ul>--%>
<%--                        <li><a href="${pageContext.request.contextPath}/change-language?language=en_US"><fmt:message--%>
<%--                                key="label.lang.en"/></a></li>--%>
<%--                        <li><a href="${pageContext.request.contextPath}/change-language?language=ru_RU"><fmt:message--%>
<%--                                key="label.lang.ru"/></a></li>--%>
<%--                    </ul>--%>
<%--                </div>--%>
<%--            </li>--%>
<%--        </ul>--%>


<%--        <div class="d-flex flex-row">--%>
            <%--            <div class="p-2">--%>
            <%--                <a href="" data-toggle="modal" data-target="#sign-in-modal">--%>
            <%--                    <fmt:message key="form.login"/>--%>
            <%--                </a>--%>
            <%--            </div>--%>
            <%--            <div class="p-2">--%>
            <%--                <a href="" data-toggle="modal" data-target="#sign-up-modal">--%>
            <%--                    <fmt:message key="form.register"/>--%>
            <%--                </a>--%>
            <%--            </div>--%>
            <%--        </div>--%>
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