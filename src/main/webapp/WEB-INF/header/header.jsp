<%--
  Created by IntelliJ IDEA.
  User: natallia.vasilyeva
  Date: 25/07/2022
  Time: 00:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${sessionScope.role == 'ADMIN'}">
        <jsp:include page="/WEB-INF/header/header-admin.jsp"/>
    </c:when>
    <c:when test="${sessionScope.role == 'CLIENT'}">
        <jsp:include page="/WEB-INF/header/header-user.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:include page="/WEB-INF/header/header-guest.jsp"/>
    </c:otherwise>
</c:choose>