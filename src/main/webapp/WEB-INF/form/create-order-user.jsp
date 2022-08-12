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

<form id="create-order-form" class="form-horizontal" method="POST" action="${pageContext.request.contextPath}/user-create-order">
    <div class="card text-white bg-dark mb-0">
        <div class="card-header">
            <h2 class="m-0">Create order</h2>
        </div>
        <div class="card-body">
            <!-- id -->
            <div class="form-group" hidden>
                <label class="col-form-label" for="create-order-car-id">Passport </label>
                <input type="text" name="carId" class="form-control" id="create-order-car-id" required>
            </div>
            <!-- /id -->
            <!-- /passport -->
            <div class="form-group">
                <label class="col-form-label" for="create-order-passport">Passport </label>
                <input type="text" name="passport" class="form-control" id="create-order-passport" required>
            </div>
            <!-- /passport -->
            <!-- /insurance -->
            <div class="form-group">
                <label class="col-form-label" for="create-order-insurance">Insurance </label>
                <select name="isInsuranceNeeded" class="form-control" id="create-order-insurance" required autofocus>
                    <option value="true">true</option>
                    <option value="false">false</option>
                </select>
            </div>
            <!-- /insurance -->
            <!-- start date -->
            <div class="form-group">
                <label class="col-form-label" for="create-order-start-date">Start rent date </label>
                <input type="datetime-local" name="startRentalDate" class="form-control" id="create-order-start-date"
                       required>
            </div>
            <!-- /start date -->
            <!-- end date -->
            <div class="form-group">
                <label class="col-form-label" for="create-order-end-date">Start rent date </label>
                <input type="datetime-local" name="endRentalDate" class="form-control" id="create-order-end-date"
                       required>
            </div>
            <!-- /end date -->
        </div>
    </div>
</form>