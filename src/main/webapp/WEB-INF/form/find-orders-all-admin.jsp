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
<form id="find-orders-by-all-form" action="${pageContext.request.contextPath}/admin-orders" method="get">
    <div class="form-group">
        <fmt:message key="form.find_order_admin_all" var="orders"/>
        <fmt:message key="form.find_orders_button" var="Find"/>
        <div class="modal-footer">
            <button type="submit" class="btn btn-primary">${Find}</button>
        </div>
    </div>
</form>