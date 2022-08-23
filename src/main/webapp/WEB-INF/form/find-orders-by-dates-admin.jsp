<%--
  Created by IntelliJ IDEA.
  User: natallia.vasilyeva
  Date: 25/07/2022
  Time: 15:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
    <c:when test="${not empty sessionScope.locale}">
        <fmt:setLocale value="${sessionScope.locale}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en_US"/>
    </c:otherwise>
</c:choose>
<fmt:setBundle basename="content"/>
<form id="find-orders-by-dates-form" action="${pageContext.request.contextPath}/admin-orders-by-dates" method="get">
    <div class="form-group">
        <fmt:message key="form.find_order_admin_dates" var="date_from"/>
        <fmt:message key="form.find" var="Find"/>
        <div class="form-group">
            <fmt:message key="form.find_order_dates_from_label" var="year_label"/>
            <label for="find-orders-by-years-from-form">${year_label}</label>
            <input type="date" name="dateFrom" class="form-control" placeholder="${date_from}"
                   autocomplete="off" id="find-orders-by-years-from-form"/>
        </div>
        <div class="form-group">
            <fmt:message key="form.find_order_dates_to_label" var="date_to"/>
            <label for="find-orders-by-years-to-form">${year_label}</label>
            <input type="date" name="dateTo" class="form-control" placeholder="${date_to}"
                   autocomplete="off" id="find-orders-by-years-to-form"/>
        </div>
        <div class="modal-footer">
            <button type="submit" class="btn btn-primary">${Find}</button>
        </div>
    </div>
</form>