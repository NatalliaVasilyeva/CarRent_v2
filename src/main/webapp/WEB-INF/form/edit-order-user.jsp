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

<fmt:setBundle basename="content"/>

<form id="edit-form" class="form-horizontal" method="POST"
      action="${pageContext.request.contextPath}/user-update-order">
    <div class="card text-white bg-dark mb-0">
        <div class="card-header">
            <h2 class="m-0"><fmt:message
                    key="modal.edit.order.title"/></h2>
        </div>
        <div class="card-body">
            <!-- id -->
            <div class="form-group">
                <label class="col-form-label" for="edit-order-id">Id </label>
                <input type="text" name="orderId" class="form-control" id="edit-order-id" required readonly>
            </div>
            <!-- /id -->
            <!-- date -->
            <div class="form-group">
                <label class="col-form-label" for="edit-date"><fmt:message
                        key="form.order.table.header.date"/> </label>
                <input type="date" name="date" class="form-control" id="edit-date" required readonly>
            </div>
            <!-- /date -->
            <!-- status -->
            <div class="form-group">
                <label class="col-form-label" for="edit-status"><fmt:message
                        key="form.order.table.header.status"/> </label>
                <input type="text" name="status" class="form-control" id="edit-status" required readonly>
            </div>
            <!-- /status -->
            <!-- description -->
            <div class="form-group">
                <label class="col-form-label" for="edit-description"><fmt:message
                        key="form.order.table.header.description"/> </label>
                <input type="text" name="description" class="form-control" id="edit-description" required readonly>
            </div>
            <!-- /description -->
            <!-- start date -->
            <div class="form-group">
                <label class="col-form-label" for="edit-start-date"><fmt:message
                        key="form.order.table.header.startDate"/> </label>
                <input type="datetime-local" name="start-date" class="form-control" id="edit-start-date" required>
            </div>
            <!-- /start date -->
            <!-- end date -->
            <div class="form-group">
                <label class="col-form-label" for="edit-end-date"><fmt:message
                        key="form.order.table.header.endDate"/> </label>
                <input type="datetime-local" name="end-date" class="form-control" id="edit-end-date" required>
            </div>
            <!-- /end date -->
            <!-- ensurance -->
            <div class="form-group">
                <label class="col-form-label" for="edit-ensurance"><fmt:message
                        key="form.insurance"/></label>
                <select name="ensurance" class="form-control" id="edit-ensurance" required autofocus>
                    <option value="true">true</option>
                    <option value="false">false</option>
                </select>
            </div>
            <!-- /ensurance -->
            <!-- accidents -->
            <div class="form-group">
                <label class="col-form-label" for="edit-accident"><fmt:message
                        key="form.order.table.header.accidence"/> </label>
                <input type="text" name="accident" class="form-control" id="edit-accident" required readonly>
            </div>
            <!-- /accidents -->
            <!-- sum -->
            <div class="form-group">
                <label class="col-form-label" for="edit-sum"><fmt:message
                        key="form.order.table.header.sum"/> </label>
                <input type="text" name="sum" class="form-control" id="edit-sum" required readonly>
            </div>
            <!-- /sum -->
        </div>
    </div>
</form>