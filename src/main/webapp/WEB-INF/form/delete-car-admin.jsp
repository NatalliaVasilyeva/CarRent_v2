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

<form id="delete-car-form" class="form-horizontal" method="POST"
      action="${pageContext.request.contextPath}/admin-delete-car">
    <div class="card text-white bg-dark mb-0">
        <div class="card-header">
            <h2 class="m-0"><fmt:message key="form.delete_car"/></h2>
        </div>
        <div class="card-body">
            <div class="form-group">
                <label class="col-form-label" for="delete-car-id-admin"><fmt:message key="form.car_id"/>: </label>
                <input type="text" name="carId" class="form-control" id="delete-car-id-admin" required>
            </div>
            <!-- /carId -->
        </div>
    </div>
</form>