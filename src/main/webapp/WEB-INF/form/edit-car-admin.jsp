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

<form id="edit-car-form" class="form-horizontal" method="POST" enctype="multipart/form-data"
      action="${pageContext.request.contextPath}/admin-update-car">
    <div class="card text-white bg-dark mb-0">
        <div class="card-header">
            <h2 class="m-0"><fmt:message key="form.edit_car"/></h2>
        </div>
        <div class="card-body">
            <img width="250" height="250" class="card-img-top" scr=""
                 id="edit-car-img" alt="Card image cap">
            <br>
            <br>
            <!-- new image -->
            <label for="edit-car-new-image"> <fmt:message key="form.add_image"/>:
                <input type="file" name="image" id="edit-car-new-image">
            </label>
            <!-- /new image -->
            <!-- carId -->
            <div class="form-group" hidden>
                <label class="col-form-label" for="edit-car-id-admin"><fmt:message key="form.car_id"/>: </label>
                <input type="text" name="carId" class="form-control" id="edit-car-id-admin" required>
            </div>
            <!-- /carId -->
            <!-- brand -->
            <div class="form-group">
                <label class="col-form-label" for="edit-car-brand-admin"><fmt:message key="form.brand_name"/>: </label>
                <input type="text" name="brandName" class="form-control" id="edit-car-brand-admin" required readonly>
            </div>
            <!-- /brand -->
            <!-- model -->
            <div class="form-group">
                <label class="col-form-label" for="edit-car-model-admin"><fmt:message key="form.model_name"/>: </label>
                <input type="text" name="modelName" class="form-control" id="edit-car-model-admin" required readonly>
            </div>
            <!-- /model -->
            <!-- color -->
            <div class="form-group">
                <label class="col-form-label" for="edit-car-color-admin"><fmt:message key="form.color"/>: </label>
                <input type="text" name="color" class="form-control" id="edit-car-color-admin" required>
            </div>
            <!-- /color -->
            <!-- yearOfProduction -->
            <div class="form-group">
                <label class="col-form-label" for="edit-car-year-admin"><fmt:message key="form.car_year"/>: </label>
                <input type="text" name="year" class="form-control" id="edit-car-year-admin" required>
            </div>
            <!-- /yearOfProduction -->
            <!-- carNumber -->
            <div class="form-group">
                <label class="col-form-label" for="edit-car-number-admin"><fmt:message key="form.car_number"/>: </label>
                <input type="text" name="carNumber" class="form-control" id="edit-car-number-admin" required>
            </div>
            <!-- /carNumber -->
            <!-- vin -->
            <div class="form-group">
                <label class="col-form-label" for="edit-car-vin-admin"><fmt:message key="form.car_vin"/>: </label>
                <input type="text" name="vin" class="form-control" id="edit-car-vin-admin" required readonly>
            </div>
            <!-- /vin -->
            <!-- repaired -->
            <div class="form-group">
                <label class="col-form-label" for="edit-car-repaired-admin"><fmt:message
                        key="form.car_repaired"/>: </label>
                <select name="isRepaired" class="form-control" id="edit-car-repaired-admin" required autofocus>
                    <option value="true">true</option>
                    <option value="false">false</option>
                </select>
            </div>
            <!-- /repaired -->
            <!-- vin -->
            <div class="form-group">
                <label class="col-form-label" for="edit-car-category-admin"><fmt:message key="form.car_category"/>: </label>
                <input type="text" name="categoryName" class="form-control" id="edit-car-category-admin" required>
            </div>
            <!-- /vin -->
        </div>
    </div>
</form>