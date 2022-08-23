<%--
  Created by IntelliJ IDEA.
  User: natallia.vasilyeva
  Date: 17/07/2022
  Time: 20:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="https://dmdev.com/functions" prefix="f" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>

<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/include/common.html"/>
    <jsp:include page="/WEB-INF/include/i18n.html"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/objects/admin-objects.js"></script>
    <title>User orders</title>
</head>
<body>
<jsp:include page="/WEB-INF/header/header.jsp"/>

<c:if test="${not empty param.incorrect}">
    <div class="alert alert-danger">
        <p>${param.incorrect}</p>
    </div>
</c:if>
<c:if test="${not empty incorrect}">
    <div class="alert alert-danger">
        <p>${incorrect}</p>
    </div>
</c:if>
<%@include file="/WEB-INF/fragment/error_message.jsp" %>
<%@include file="/WEB-INF/fragment/success_message.jsp" %>

<jsp:include page="/WEB-INF/if/brands-exists.jsp"/>
<jsp:include page="/WEB-INF/if/categories-exists.jsp"/>
<jsp:include page="/WEB-INF/if/models-exists.jsp"/>

</body>
</html>