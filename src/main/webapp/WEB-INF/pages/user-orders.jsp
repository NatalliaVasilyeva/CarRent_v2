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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/order/user-order.js"></script>
    <title>User orders</title>
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

<c:if test="${empty orders}">
    <h1 align="center"><fmt:message key="form.not_orders"/></h1>
</c:if>
<c:if test="${not empty orders}">
    <div class="container-fluid">
        <div class="col-lg-auto">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Orders
                </div>
                <div class="panel-body table-responsive">
                    <table class="table table-condensed table-striped text-center">
                        <thead class="thead-dark">
                        <tr>
                            <th></th>
                            <th><fmt:message key="form.order.table.header.number"/></th>
                            <th><fmt:message key="form.order.table.header.date"/></th>
                            <th><fmt:message key="form.order.table.header.status"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${orders}" var="order" varStatus="loop">
                            <tr data-toggle="collapse" data-target="#orders_${loop.index}" class="accordion-toggle">
                                <td>
                                    <button class="btn btn-dark btn-xs">
                                        <i class="fa fa-eye" aria-hidden="true"></i>
                                    </button>
                                </td>
                                <td>${order.id}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${sessionScope.locale eq 'en_US'}">
                                            ${f:formatLocalDateTime(order.date, "yyyy-MM-dd")}
                                        </c:when>
                                        <c:when test="${sessionScope.locale eq 'ru_RU'}">
                                            ${f:formatLocalDateTime(order.date, "dd-MM-yyyy")}
                                        </c:when>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${order.orderStatus == 'CONFIRMATION_WAIT'}">
                                            <fmt:message key="form.order.status.confirm"/>
                                        </c:when>
                                        <c:when test="${order.orderStatus== 'DECLINED'}">
                                            <fmt:message key="form.order.status.declined"/>
                                        </c:when>
                                        <c:when test="${order.orderStatus== 'PAYED'}">
                                            <fmt:message key="form.order.status.payed"/>
                                        </c:when>
                                        <c:when test="${order.orderStatus== 'NOT_PAYED'}">
                                            <fmt:message key="form.order.status.not_payed"/>
                                        </c:when>
                                        <c:when test="${order.orderStatus== 'CANCELLED'}">
                                            <fmt:message key="form.order.status.cancelled"/>
                                        </c:when>
                                    </c:choose>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="12" class="hiddenRow">
                                    <div class="accordian-body collapse col-lg-auto table-responsive"
                                         id="orders_${loop.index}">
                                        <table class="table table-striped text-center" id="innerTable">
                                            <thead class="thead-dark">
                                            <tr class="info">
                                                <th><fmt:message key="form.order.table.header.number"/></th>
                                                <th><fmt:message key="form.order.table.header.date"/></th>
                                                <th><fmt:message key="form.order.table.header.status"/></th>
                                                <th><fmt:message key="form.order.table.header.description"/></th>
                                                <th><fmt:message key="form.order.table.header.startDate"/></th>
                                                <th><fmt:message key="form.order.table.header.endDate"/></th>
                                                <th><fmt:message key="form.order.table.header.ensurance"/></th>
                                                <th><fmt:message key="form.order.table.header.accidence"/></th>
                                                <th><fmt:message key="form.order.table.header.sum"/></th>
                                                <th colspan="2"></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr class="info">
                                                <td>${order.id}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${sessionScope.locale eq 'en_US'}">
                                                            ${f:formatLocalDateTime(order.date, "yyyy-MM-dd")}
                                                        </c:when>
                                                        <c:when test="${sessionScope.locale eq 'ru_RU'}">
                                                            ${f:formatLocalDateTime(order.date, "dd-MM-yyyy")}
                                                        </c:when>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${order.orderStatus == 'CONFIRMATION_WAIT'}">
                                                            <fmt:message key="form.order.status.confirm"/>
                                                        </c:when>
                                                        <c:when test="${order.orderStatus == 'DECLINED'}">
                                                            <fmt:message key="form.order.status.declined"/>
                                                        </c:when>
                                                        <c:when test="${order.orderStatus == 'PAYED'}">
                                                            <fmt:message key="form.order.status.payed"/>
                                                        </c:when>
                                                        <c:when test="${order.orderStatus == 'NOT_PAYED'}">
                                                            <fmt:message key="form.order.status.not_payed"/>
                                                        </c:when>
                                                        <c:when test="${order.orderStatus == 'CANCELLED'}">
                                                            <fmt:message key="form.order.status.cancelled"/>
                                                        </c:when>
                                                    </c:choose>
                                                </td>
                                                <td>${order.carDescription}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${sessionScope.locale eq 'en_US'}">
                                                            ${f:formatLocalDateTime(order.startRentalDate, "yyyy-MM-dd HH:mm")}
                                                        </c:when>
                                                        <c:when test="${sessionScope.locale eq 'ru_RU'}">
                                                            ${f:formatLocalDateTime(order.startRentalDate, "dd-MM-yyyy HH:mm")}
                                                        </c:when>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${sessionScope.locale eq 'en_US'}">
                                                            ${f:formatLocalDateTime(order.endRentalDate, "yyyy-MM-dd HH:mm")}
                                                        </c:when>
                                                        <c:when test="${sessionScope.locale eq 'ru_RU'}">
                                                            ${f:formatLocalDateTime(order.endRentalDate, "dd-MM-yyyy HH:mm")}
                                                        </c:when>
                                                    </c:choose>
                                                </td>
                                                <td>${order.insuranceNeeded}</td>
                                                <td>${order.accidentExist}</td>
                                                <td>${order.sum}</td>
                                                <c:choose>
                                                    <c:when test="${order.orderStatus eq 'CONFIRMATION_WAIT'}">
                                                        <td>
                                                            <button class="btn btn-dark btn-xs editbtn"
                                                                    data-toggle="modal"
                                                                    data-target="#edit-modal">
                                                                <i class="fa fa-pencil" aria-hidden="true"></i>
                                                            </button>
                                                        </td>
                                                        <td>
                                                            <button class="btn btn-dark btn-xs cancelledbtn">
                                                                <i class="fa fa-trash" aria-hidden="true"></i>
                                                            </button>
                                                        </td>
                                                    </c:when>
                                                </c:choose>

                                                <c:choose>
                                                    <c:when test="${order.orderStatus eq 'NOT_PAYED'}">
                                                        <td>
                                                            <button class="btn btn-dark btn-xs editbtn"
                                                                    data-toggle="modal"
                                                                    data-target="#edit-modal">
                                                                <i class="fa fa-pencil" aria-hidden="true"></i>
                                                            </button>
                                                        </td>
                                                        <td>
                                                            <button type="submit"
                                                                    class="btn btn-dark btn-xs cancelledbtn">
                                                                <i class="fa fa-trash" aria-hidden="true"></i>
                                                            </button>
                                                        </td>
                                                    </c:when>
                                                </c:choose>
                                            </tr>
                                            </tbody>
                                        </table>

                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <!-- Attachment Modal -->
    <div class="modal fade" id="edit-modal" tabindex="-1" role="dialog" aria-labelledby="edit-modal-label"
         aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="edit-modal-label">Edit Data</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body" id="attachment-body-content">
                    <jsp:include page="/WEB-INF/form/edit-order-user.jsp"/>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary" form="edit-form" value="Submit"><fmt:message key="form.submit_button"/></button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message key="form.close_button"/></button>
                </div>
            </div>
        </div>
    </div>
    <!-- /Attachment Modal -->

</c:if>
</body>
</html>