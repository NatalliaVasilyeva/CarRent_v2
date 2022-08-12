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
<div class="container-xl px-4 mt-4">
    <div class="row">
        <div class="col-xl-12">
            <div class="container">

                <div class="row">
                    <h1 align="center"><fmt:message key="header.user.settings"/></h1>
                </div>

                <div class="row top-buffer" id="change-language-div">
                    <button type="button" class="btn btn-light btn-block" onclick="changeLanguage()">
                        <fmt:message key="user.settings.change-language-button"/>
                    </button>
                </div>


                <div class="row top-buffer" id="save-new-language-div" style="display: none">
                    <label for="locale-select"><fmt:message key="header.language"/></label>
                    <div class="col-6">
                        <select class="form-control" onchange="changeLocale(this)" id="locale-select">
                            <option value="en_US">English</option>
                            <option value="ru_RU">Русский</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <div class="col-12">
                            <button type="button" class="form-control btn btn-light" onclick="cancelChangeLanguage()">
                                <fmt:message key="form.cancel_button"/>
                            </button>
                        </div>
                    </div>
                </div>


                <div class="row top-buffer" id="change-password-div">
                    <button type="button" class="btn btn-light btn-block" onclick="changePassword()">
                        <fmt:message key="user.settings.change-password-button"/>
                    </button>
                </div>
                <div class="row top-buffer" id="save-new-password-div" style="display: none">
                    <form method="POST" action="${pageContext.request.contextPath}/change-password">
                        <div class="form-group row">
                            <label for="settings-old-password-input" class="col-2">
                                <fmt:message key="user.settings.old-password"/>
                            </label>
                            <div class="col-8">
                                <input type="password" class="form-control"
                                       pattern="(?=^.{6,40}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$"
                                       maxlength="40"
                                       title="Password(6-40 symbols) should contain UpperCase, LowerCase, Number/SpecialChar"
                                       autocomplete="off"
                                       id="settings-old-password-input" name="old_password" required/>
                            </div>
                            <div class="col-2" id="old-password-error-div">
                                <small class="text-danger" id="settings-old-password-error-small"></small>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="settings-new-password-input" class="col-2">
                                <fmt:message key="user.settings.new-password"/>
                            </label>
                            <div class="col-8">
                                <input type="password" class="form-control"
                                       pattern="(?=^.{6,40}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$"
                                       maxlength="40"
                                       title="Password(6-40 symbols) should contain UpperCase, LowerCase, Number/SpecialChar"
                                       autocomplete="off"
                                       id="settings-new-password-input" name="new_password" required/>
                            </div>
                            <div class="col-2" id="new-password-error-div">
                                <small class="text-danger" id="settings-new-password-error-small"></small>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="settings-confirm-new-password-input" class="col-2">
                                <fmt:message key="user.settings.confirm-new-password"/>
                            </label>
                            <div class="col-8">
                                <input type="password" class="form-control"
                                       pattern="(?=^.{6,40}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$"
                                       maxlength="40"
                                       title="Password(6-40 symbols) should contain UpperCase, LowerCase, Number/SpecialChar"
                                       autocomplete="off"
                                       id="settings-confirm-new-password-input" name="confirm_new_password" required/>
                            </div>
                            <div class="col-2" id="confirm-new-password-error-div">
                                <small class="text-danger" id="settings-confirm-new-password-error-small"></small>
                            </div>
                        </div>
                        <div class="form-group row" id="settings-buttons-div">
                            <label class="col-md-3 control-label"></label>
                            <div class="col-md-8">
                                <input type="submit" class="btn btn-primary" value="Save Changes">
                                <span></span>
                                <input type="reset" class="btn btn-default" value="Cancel">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/footer/footer.jsp"/>
</body>
</html>