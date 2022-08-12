<%--
  Created by IntelliJ IDEA.
  User: natallia.vasilyeva
  Date: 24/07/2022
  Time: 20:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>404 error</title>
</head>
<body>
<button onclick="history.back()">Back to Previous Page</button>
Page not found!!!
<br/>
<p><b>Error code:</b> ${pageContext.errorData.statusCode}</p>
<p><b>Request URI:</b> ${pageContext.request.scheme}://${header.host}${pageContext.errorData.requestURI}</p>
<c:if test="${not empty requestScope['jakarta.servlet.error']}">
    <p><b>Error code:</b> ${requestScope['jakarta.servlet.error.sc']}</p>
    <p><b>Error message:</b> ${requestScope['jakarta.servlet.error.message']}</p>
</c:if>
<br/>
</body>
</html>