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
<form id="find-cars-by-years-form" action="${pageContext.request.contextPath}/cars-by-years" method="get">
    <div class="form-group">
        <fmt:message key="form.find_car_year_placeholder" var="year"/>
        <fmt:message key="form.find_car_button" var="Find"/>
        <div class="form-group">
            <fmt:message key="form.find_car_years_from_label" var="years_from_label"/>
            <label for="find-cars-by-years-from-form">${years_from_label}</label>
            <input type="text" name="yearFrom" class="form-control" placeholder="${year}"
                   autocomplete="off" id="find-cars-by-years-from-form"/>
        </div>
        <div class="form-group">
            <fmt:message key="form.find_car_years_to_label" var="years_to_label"/>
            <label for="find-cars-by-years-to-form">${years_to_label}</label>
            <input type="text" name="yearTo" class="form-control" placeholder="${year}" autocomplete="off"
                   id="find-cars-by-years-to-form"/>
        </div>
        <div class="modal-footer">
            <button type="submit" class="btn btn-primary">${Find}</button>
        </div>
    </div>
</form>