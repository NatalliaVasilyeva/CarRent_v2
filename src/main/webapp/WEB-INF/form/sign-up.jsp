<%--
  Created by IntelliJ IDEA.
  User: natallia.vasilyeva
  Date: 25/07/2022
  Time: 15:42
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
<%--<script src="${pageContext.request.contextPath}/js/form/sing-up.js"></script>--%>
<form id="sign-up-form">
    <div class="form-group">
        <fmt:message key="form.login" var="login"/>
        <label class="col-form-label" for="sign-up-login-input">${login} </label>
        <input type="text" class="form-control" placeholder="${login}" id="sign-up-login-input" required/>
        <small class="text-danger" id="sign-up-login-error-small"></small>
    </div>
    <div class="form-group">
        <fmt:message key="form.first_name" var="name"/>
        <label class="col-form-label" for="sign-up-first-name-input">${name} </label>
        <input type="text" class="form-control" placeholder="${name}" id="sign-up-first-name-input" required/>
        <small class="text-danger" id="sign-up-first-name-error-small"></small>
    </div>
    <div class="form-group">
        <fmt:message key="form.last_name" var="surname"/>
        <label class="col-form-label" for="sign-up-surname-input">${surname} </label>
        <input type="text" class="form-control" placeholder="${surname}" id="sign-up-surname-input" required/>
        <small class="text-danger" id="sign-up-surname-error-small"></small>
    </div>
    <div class="form-group">
        <fmt:message key="form.email" var="email"/>
        <label class="col-form-label" for="sign-up-email-input">${email} </label>
        <input type="text" class="form-control" placeholder="${email}" id="sign-up-email-input" required/>
        <small class="text-danger" id="sign-up-email-error-small"></small>
    </div>
    <div class="form-group">
        <fmt:message key="form.phone" var="phone"/>
        <label class="col-form-label" for="sign-up-phone-input">${phone} </label>
        <input type="text" class="form-control" placeholder="${phone}" id="sign-up-phone-input" required/>
        <small class="text-danger" id="sign-up-phone-error-small"></small>
    </div>
    <div class="form-group">
        <fmt:message key="form.address" var="address"/>
        <label class="col-form-label" for="sign-up-address-input">${address} </label>
        <input type="text" class="form-control" placeholder="${address}" id="sign-up-address-input" required/>
        <small class="text-danger" id="sign-up-address-error-small"></small>
    </div>
    <div class="form-group">
        <fmt:message key="form.birthday" var="birthday"/>
        <label class="col-form-label" for="sign-up-birthday-input">${birthday} </label>
        <input type="date" class="form-control" placeholder="${birthday}" id="sign-up-birthday-input"
               required/>
        <small class="text-danger" id="sign-up-birthday-error-small"></small>
    </div>
    <div class="form-group">
        <fmt:message key="form.driver_license" var="driver_license"/>
        <label class="col-form-label" for="sign-up-driver-license-input">${driver_license} </label>
        <input type="text" class="form-control" placeholder="${driver_license}" id="sign-up-driver-license-input"/>
        <small class="text-danger" id="sign-up-driver-license-error-small"></small>
    </div>
    <div class="form-group">
        <fmt:message key="form.driver_license_issue_date" var="driver_license_issue_date"/>
        <label class="col-form-label" for="sign-up-issue-date-input">${driver_license_issue_date} </label>
        <input type="date" class="form-control" placeholder="${driver_license_issue_date}"
               id="sign-up-issue-date-input"/>
        <small class="text-danger" id="sign-up-issue-date-error-small"></small>
    </div>
    <div class="form-group">
        <fmt:message key="form.driver_license_expired_date" var="driver_license_expired_date"/>
        <label class="col-form-label" for="sign-up-expired-date-input">${driver_license_expired_date}</label>
        <input type="date" class="form-control" placeholder="${driver_license_expired_date}"
               id="sign-up-expired-date-input"/>
        <small class="text-danger" id="sign-up-expired-date-error-small"></small>
    </div>
    <div class="form-group">
        <fmt:message key="form.password" var="password"/>
        <label class="col-form-label" for="sign-up-password-input">${password} </label>
        <input type="password" class="form-control" placeholder="${password}" id="sign-up-password-input" required/>
        <small class="text-danger" id="sign-up-password-error-small"></small>
    </div>
    <div class="form-group">
        <fmt:message key="form.repeat_password" var="confirm_password"/>
        <label class="col-form-label" for="sign-up-confirm-password-input">${confirm_password} </label>
        <input type="password" class="form-control" placeholder="${confirm_password}" id="sign-up-confirm-password-input" required/>
        <small class="text-danger" id="sign-up-confirm-password-error-small"></small>
    </div>
    <div class="form-group">
        <button type="submit" class="form-control btn btn-light signupbtn" value="Submit">
            <fmt:message key="form.sing_up_button"/>
        </button>
    </div>
</form>