<%--
  Created by IntelliJ IDEA.
  User: natallia.vasilyeva
  Date: 09/08/2022
  Time: 10:35
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/user/profile-user.js"></script>
    <title>User profile</title>
</head>
<body>
<jsp:include page="/WEB-INF/header/header.jsp"/>
<c:if test="${not empty param.incorrect}">
    <div class="alert alert-danger">
        <p>${param.incorrect}</p>
    </div>
</c:if>
<%@include file="/WEB-INF/fragment/error_message.jsp" %>
<%@include file="/WEB-INF/fragment/success_message.jsp" %>
<div class="container-xl px-4 mt-4">
    <div class="row">
        <div class="col-xl-12">
            <form id="profile-form" method="POST" action="${pageContext.request.contextPath}/user-profile">
                <div class="card mb-4">
                    <div class="card-header">User profile</div>
                    <div class="card-body">
                        <div class="form-group row" hidden>
                            <label for="profile-id-input" class="col-2 col-form-label">
                                <fmt:message key="form.user_id"/>
                            </label>
                            <div class="col-10">
                                <input type="text" class="form-control"
                                       id="profile-id-input" name="userId" value="${user.id}" readonly/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="profile-firstname-input" class="col-2 col-form-label">
                                <fmt:message key="form.first_name"/>
                            </label>
                            <div class="col-10">
                                <input type="text" class="form-control"
                                       id="profile-firstname-input" name="username" value="${user.name}"
                                       readonly/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="profile-surname-input" class="col-2 col-form-label">
                                <fmt:message key="form.last_name"/>
                            </label>
                            <div class="col-10">
                                <input type="text" class="form-control" id="profile-surname-input"
                                       name="surname"
                                       value="${user.surname}" readonly/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="profile-login-input" class="col-2 col-form-label">
                                <fmt:message key="form.login"/>
                            </label>
                            <div class="col-10">
                                <input type="text" class="form-control" id="profile-login-input" name="login"
                                       value="${user.login}" readonly/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="profile-email-input" class="col-2 col-form-label">
                                <fmt:message key="form.email"/>
                            </label>
                            <div class="col-10">
                                <input type="text" class="form-control" id="profile-email-input" name="email"
                                       value="${user.email}" readonly/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="profile-address-input" class="col-2 col-form-label">
                                <fmt:message key="form.address"/>
                            </label>
                            <div class="col-10">
                                <input type="text" class="form-control" id="profile-address-input"
                                       name="address"
                                       value="${user.address}" readonly/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="profile-phone-input" class="col-2 col-form-label">
                                <fmt:message key="form.phone"/>
                            </label>
                            <div class="col-10">
                                <input type="text" class="form-control" id="profile-phone-input" name="phone"
                                       value="${user.phone}" readonly/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="profile-birthday-input" class="col-2 col-form-label">
                                <fmt:message key="form.birthday"/>
                            </label>
                            <div class="col-10">
                                <input type="text" class="form-control" id="profile-birthday-input"
                                       name="birthday"
                                       value="${user.birthday}" readonly>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="profile-role-input" class="col-2 col-form-label">
                                <fmt:message key="form.role"/>
                            </label>
                            <div class="col-10">
                                <input type="text" class="form-control" id="profile-role-input" name="role"
                                       value="${user.role}"
                                       readonly/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="profile-driver-license-number-input" class="col-2 col-form-label">
                                <fmt:message key="form.driver_license"/>
                            </label>
                            <div class="col-10">
                                <input type="text" class="form-control"
                                       id="profile-driver-license-number-input" name="driverLicenseNumber"
                                       value="${user.driverLicenseDto.driverLicenseNumber}" readonly/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="profile-driver-license-issue-date-input" class="col-2 col-form-label">
                                <fmt:message key="form.driver_license_issue_date"/>
                            </label>
                            <div class="col-10">
                                <input type="text" class="form-control"
                                       id="profile-driver-license-issue-date-input"
                                       name="driverLicenseIssueDate"
                                       value="${user.driverLicenseDto.driverLicenseIssueDate}" readonly/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="profile-driver-license-expired-date-input" class="col-2 col-form-label">
                                <fmt:message key="form.driver_license_expired_date"/>
                            </label>
                            <div class="col-10">
                                <input type="text" class="form-control"
                                       id="profile-driver-license-expired-date-input"
                                       name="driverLicenseExpiredDate"
                                       value="${user.driverLicenseDto.driverLicenseExpiredDate}" readonly/>
                            </div>
                        </div>
                        <div class="form-group row" id="edit-profile-button-div">
                            <input type="button"
                                    class="form-control btn btn-light align-content-lg-center makeeditablebtn"
                                value = <fmt:message key="form.order.table.button.edit"/>
                            >

<%--                            </input>--%>
                        </div>
                        <div class="form-group row" id="edit-manage-buttons-div" style="display: none">
                            <label class="col-md-3 control-label"></label>
                            <div class="col-md-8">
                                <input type="submit" class="btn btn-primary" value="Save Changes">
                                <span></span>
                                <input type="reset" class="btn btn-default makeuneditablebtn" value="Cancel">
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</div>
<jsp:include page="/WEB-INF/footer/footer.jsp"/>
</body>
</html>