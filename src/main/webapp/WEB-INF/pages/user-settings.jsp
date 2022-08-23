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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/user/settings.js"></script>
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
<div class="container-xl px-4 mt-4 pb-5 mb-20">
    <div class="row">
        <div class="col-xl-12">
            <div class="container justify-content-center">

                <div class="row justify-content-center">
                    <h1><fmt:message key="header.user.settings"/></h1>
                </div>

                <button type="button" class="btn btn-primary btn-lg justify-content-center" data-toggle="modal"
                        data-target="#change-language">
                    <fmt:message key="user.settings.change-language-button"/>
                </button>

                <button type="button" class="btn btn-primary btn-lg justify-content-center" data-toggle="modal"
                        data-target="#change-password">
                    <fmt:message key="user.settings.change-password-button"/>
                </button>

                <c:if test="${sessionScope.role == 'CLIENT'}">
                    <form action="${pageContext.request.contextPath}/user-download" method="get">
                        <button type="submit" class="btn btn-primary btn-lg justify-content-center"><fmt:message
                                key="user.settings.download-report"/></button>
                    </form>
                </c:if>

                <c:if test="${sessionScope.role == 'ADMIN'}">
                    <form action="${pageContext.request.contextPath}/report" method="get">
                        <button type="submit" class="btn btn-primary btn-lg justify-content-center"><fmt:message
                                key="user.settings.download-report"/></button>
                    </form>
                </c:if>


                <div class="modal fade" id="change-language" tabindex="-1" role="dialog"
                     aria-labelledby="changeLanguage" aria-hidden="true">
                    <div class="modal-dialog modal-sm" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="change-language-modal-label"><fmt:message
                                        key="form.choose_language"/></h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                        aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body" id="attachment-change-language-body-content">
                                <form id="change-language-form" class="form-horizontal" method="GET"
                                      action="${pageContext.request.contextPath}/change-language">
                                    <div class="card text-white bg-secondary mb-0">
                                        <div class="card-header">
                                            <h2 class="m-0 text-center"><fmt:message
                                                    key="user.settings.change-language-button"/></h2>
                                        </div>
                                        <div class="card-body">
                                            <div class="input-group mb-3">
                                                <select class="language-select" id="inputLanguageSelect"
                                                        name="language">
                                                    <option language><fmt:message key="form.choose_language"/>...
                                                    </option>
                                                    <option value="en_US"><fmt:message key="label.lang.en"/></option>
                                                    <option value="ru_RU"><fmt:message key="label.lang.ru"/></option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-primary" form="change-language-form"
                                        value="Submit">
                                    <fmt:message key="user.settings.submit-button"/>
                                </button>
                                <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message
                                        key="user.settings.cancel-button"/></button>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="modal fade" id="change-password" tabindex="-1" role="dialog"
                     aria-labelledby="changeLanguage" aria-hidden="true">
                    <div class="modal-dialog modal-sm" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="change-password-modal-label"><fmt:message
                                        key="form.choose_language"/></h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                        aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body" id="attachment-change-password-body-content">
                                <form id="change-password-form" class="form-horizontal" method="POST"
                                      action="${pageContext.request.contextPath}/change-password">
                                    <div class="card text-white bg-secondary mb-0">
                                        <div class="card-header">
                                            <h2 class="m-0 text-center"><fmt:message
                                                    key="user.settings.change-password-button"/></h2>
                                        </div>
                                        <div class="card-body">
                                            <div class="form-group">
                                                <fmt:message key="user.settings.old-password" var="password"/>
                                                <label class="col-form-label"
                                                       for="settings-old-password-input">${password} </label>
                                                <input type="password" class="form-control" placeholder="${password}"
                                                       pattern="(?=^.{6,40}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$"
                                                       maxlength="40"
                                                       title=
                                                       <fmt:message key="form.password_pattern"/>
                                                               autocomplete="off"
                                                       id="settings-old-password-input" name="old_password" required/>
                                                <small class="text-danger"
                                                       id="settings-old-password-error-small"></small>
                                            </div>
                                            <div class="form-group">
                                                <fmt:message key="user.settings.new-password" var="password"/>
                                                <label class="col-form-label"
                                                       for="settings-new-password-input">${password} </label>
                                                <input type="password" class="form-control" placeholder="${password}"
                                                       pattern="(?=^.{6,40}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$"
                                                       maxlength="40"
                                                       title=
                                                       <fmt:message key="form.password_pattern"/>
                                                               autocomplete="off"
                                                       id="settings-new-password-input" name="new_password" required/>
                                                <small class="text-danger"
                                                       id="settings-new-password-error-small"></small>
                                            </div>
                                            <div class="form-group">
                                                <fmt:message key="form.repeat_password" var="confirm_password"/>
                                                <label class="col-form-label"
                                                       for="settings-confirm-password-input">${confirm_password} </label>
                                                <input type="password" class="form-control"
                                                       placeholder="${confirm_password}"
                                                       pattern="(?=^.{6,40}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$"
                                                       maxlength="40"
                                                       title=
                                                       <fmt:message key="form.password_pattern"/>
                                                               autocomplete="off"
                                                       id="settings-confirm-password-input" name="confirm_password"
                                                       required/>
                                                <small class="text-danger"
                                                       id="sign-up-confirm-password-error-small"></small>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-primary" form="change-password-form"
                                        value="Submit">
                                    <fmt:message key="user.settings.submit-button"/>
                                </button>
                                <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message
                                        key="user.settings.cancel-button"/></button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/footer/footer.jsp"/>
</body>
</html>