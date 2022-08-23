<%--
  Created by IntelliJ IDEA.
  User: natallia.vasilyeva
  Date: 02/08/2022
  Time: 22:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<jsp:include page="/WEB-INF/header/header.jsp"/>
<html>
<head>
  <title>Error 403</title>
</head>
<body>
<button onclick="history.back()">Back to Previous Page</button>
<h1>Error 403</h1>
<h2><fmt:message key="error.403.no_access_rights_msg"/></h2>
</body>
</html>