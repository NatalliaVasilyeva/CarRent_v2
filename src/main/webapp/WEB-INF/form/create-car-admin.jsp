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

<form id="create-car-form" class="form-horizontal" method="POST" enctype="multipart/form-data"
      action="${pageContext.request.contextPath}/admin-create-car">
    <div class="card text-white bg-dark mb-0">
        <div class="card-header">
            <h2 class="m-0"><fmt:message
                    key="form.create_car"/></h2>
        </div>
        <div class="card-body">
            <!-- image -->
            <label for="create-car-image"> <fmt:message key="form.add_image"/>:
                <input type="file" name="image" id="create-car-image">
            </label>
            <!-- image -->
            <!-- brand -->
            <div class="form-group">
                <label class="col-form-label" for="create-car-brand-admin"><fmt:message key="form.brand_name"/>: </label>
                <input type="text" name="brandName" class="form-control" id="create-car-brand-admin" required>
            </div>
            <!-- /brand -->
            <!-- model -->
            <div class="form-group">
                <label class="col-form-label" for="create-car-model-admin"><fmt:message key="form.model_name"/>: </label>
                <input type="text" name="modelName" class="form-control" id="create-car-model-admin" required>
            </div>
            <!-- /model -->
            <!-- transmission -->
            <div class="form-group">
                <label class="col-form-label" for="create-car-transmission-admin"><fmt:message key="form.model_transmission"/>: </label>
                <input type="text" name="transmission" class="form-control" id="create-car-transmission-admin" required>
            </div>
            <!-- /transmission -->
            <!-- engine -->
            <div class="form-group">
                <label class="col-form-label" for="create-car-engine-admin"><fmt:message key="form.model_engine"/>: </label>
                <input type="text" name="engineType" class="form-control" id="create-car-engine-admin" required>
            </div>
            <!-- /engine -->
            <!-- color -->
            <div class="form-group">
                <label class="col-form-label" for="create-car-color-admin"><fmt:message key="form.color"/>: </label>
                <input type="text" name="color" class="form-control" id="create-car-color-admin" required>
            </div>
            <!-- /color -->
            <!-- yearOfProduction -->
            <div class="form-group">
                <label class="col-form-label" for="create-car-year-admin"><fmt:message key="form.car_year"/>: </label>
                <input type="text" name="year" class="form-control" id="create-car-year-admin" required>
            </div>
            <!-- /yearOfProduction -->
            <!-- carNumber -->
            <div class="form-group">
                <label class="col-form-label" for="create-car-number-admin"><fmt:message key="form.car_number"/>: </label>
                <input type="text" name="carNumber" class="form-control" id="create-car-number-admin" required>
            </div>
            <!-- /carNumber -->
            <!-- vin -->
            <div class="form-group">
                <label class="col-form-label" for="create-car-vin-admin"><fmt:message key="form.car_vin"/>: </label>
                <input type="text" name="vin" class="form-control" id="create-car-vin-admin" required>
            </div>
            <!-- /vin -->
            <!-- repaired -->
            <div class="form-group">
                <label class="col-form-label" for="create-car-repaired-admin"><fmt:message
                        key="form.car_repaired"/>: </label>
                <select name="isRepaired" class="form-control" id="create-car-repaired-admin" required autofocus>
                    <option value="true">true</option>
                    <option value="false">false</option>
                </select>
            </div>
            <!-- /repaired -->
            <!-- category -->
            <div class="form-group">
                <label class="col-form-label" for="create-car-category-admin"><fmt:message key="form.category_name"/>: </label>
                <input type="text" name="categoryName" class="form-control" id="create-car-category-admin" required>
            </div>
            <!-- /category -->
        </div>
        <div class="modal-footer">
            <button type="submit" class="btn btn-primary"><fmt:message
                    key="form.button"/></button>
        </div>
    </div>
</form>