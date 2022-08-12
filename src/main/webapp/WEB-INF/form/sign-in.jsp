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
<%--<c:choose>--%>
<%--    <c:when test="${not empty sessionScope.locale}">--%>
<%--        <fmt:setLocale value="${sessionScope.locale}"/>--%>
<%--    </c:when>--%>
<%--    <c:otherwise>--%>
<%--        <fmt:setLocale value="en_US"/>--%>
<%--    </c:otherwise>--%>
<%--</c:choose>--%>
<fmt:setBundle basename="content"/>
<form id="sign-in-form" action="${pageContext.request.contextPath}/sign-in" method="post">
    <div class="form-group">
        <fmt:message key="form.login" var="login"/>
        <fmt:message key="form.username_pattern" var="username_pattern"/>
        <input type="text" name="login" class="form-control" placeholder="${login}" pattern="^[A-Za-z0-9._-]{4,45}$"
               maxlength="40" title="${username_pattern}"
               autocomplete="off" id="sign-in-login-input" required/>
        <small class="text-danger" id="sign-in-login-error-small"></small>
    </div>
    <div class="form-group">
        <fmt:message key="form.password" var="password"/>
        <fmt:message key="form.password_pattern" var="password_pattern"/>
        <input type="password" name="password" class="form-control" placeholder="${password}"
               id="sign-in-password-input" pattern="(?=^.{6,40}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$"
               maxlength="40" title="${password_pattern}"
               autocomplete="off" required/>
        <small class="text-danger" id="sign-in-password-error-small"></small>
    </div>
    <input type="submit" class="submit bg-info text-light" value="${login}" name="login" required/>
</form>