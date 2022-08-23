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
<form id="find-cars-by-brand-form" action="${pageContext.request.contextPath}/cars-by-brand-admin" method="get">
    <div class="form-group">
        <fmt:message key="form.find_car_brand_placeholder" var="brand"/>
        <fmt:message key="form.find_car_button" var="Find"/>
        <div class="form-group">
            <fmt:message key="form.find_car_brand_label" var="brand_label"/>
            <label for="find-cars-by-brand-form">${brand_label}</label>
            <input type="text" name="brand" class="form-control" placeholder="${brand}"
                   autocomplete="off" id="find-cars-by-brand-form"/>
        </div>
        <div class="modal-footer">
            <button type="submit" class="btn btn-primary">${Find}</button>
        </div>
    </div>
</form>