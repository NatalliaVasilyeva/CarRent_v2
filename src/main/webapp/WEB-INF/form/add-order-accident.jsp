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

<form id="add-accident-form" class="form-horizontal" method="POST" action="${pageContext.request.contextPath}/admin-add-accident">
    <div class="card text-white bg-dark mb-0">
        <div class="card-header">
            <h2 class="m-0"><fmt:message key="form.create_accident"/></h2>
        </div>
        <div class="card-body">
            <!-- id -->
            <div class="form-group">
                <label class="col-form-label" for="add-accident-order-id"><fmt:message key="form.order.table.header.number"/> </label>
                <input type="text" name="orderId" class="form-control" id="add-accident-order-id" required>
            </div>
            <!-- /id -->
            <!-- date -->
            <div class="form-group">
                <label class="col-form-label" for="add-accident-date"><fmt:message key="form.create_accident_date"/> </label>
                <input type="datetime-local" name="accidentDate" class="form-control" id="add-accident-date" required>
            </div>
            <!-- /date -->
            <!-- description -->
            <div class="form-group">
                <label class="col-form-label" for="add-accident-description"><fmt:message key="form.create_accident_description"/> </label>
                <input type="text" name="description" class="form-control" id="add-accident-description" required>
            </div>
            <!-- /description -->
            <!-- damage -->
            <div class="form-group">
                <label class="col-form-label" for="add-accident-damage"><fmt:message key="form.create_accident_damage"/> </label>
                <input type="number" name="damage" class="form-control" id="add-accident-damage">
            </div>
            <!-- /damage -->
            <!-- startRental -->
            <div class="form-group">
                <label class="col-form-label" for="add-accident-order-start-id"><fmt:message key="form.order.table.header.startDate"/> </label>
                <input type="datetime-local" name="startRentalDate" class="form-control" id="add-accident-order-start-id" required>
            </div>
            <!-- /startRental -->
            <!-- endRental -->
            <div class="form-group">
                <label class="col-form-label" for="add-accident-order-end-id"><fmt:message key="form.order.table.header.endDate"/> </label>
                <input type="datetime-local" name="endRentalDate" class="form-control" id="add-accident-order-end-id" required>
            </div>
            <!-- /endRental -->
        </div>
    </div>
</form>