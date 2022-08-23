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

<form id="create-model-form" class="form-horizontal" method="POST"
      action="${pageContext.request.contextPath}/admin-create-model">
    <div class="card text-white bg-dark mb-0">
        <div class="card-header">
            <h2 class="m-0"><fmt:message
                    key="form.create_model"/></h2>
        </div>
        <div class="card-body">
            <!-- name -->
            <div class="form-group">
                <label class="col-form-label" for="create-model-name"> <fmt:message
                        key="form.model_name"/> </label>
                <input type="text" name="modelName" class="form-control" id="create-model-name" required>
            </div>
            <!-- /name -->
            <!-- brand -->
            <div class="form-group">
                <label class="col-form-label" for="create-model-brand"> <fmt:message
                        key="form.brand_name"/> </label>
                <input type="text" name="brandName" class="form-control" id="create-model-brand" required>
            </div>
            <!-- /brand -->
            <!-- transmission -->
            <div class="form-group">
                <label class="col-form-label" for="create-model-transmission"> <fmt:message
                        key="form.model_transmission"/> </label>
                <input type="text" name="transmission" class="form-control" id="create-model-transmission" required>
            </div>
            <!-- /transmission -->
            <!-- engine -->
            <div class="form-group">
                <label class="col-form-label" for="create-model-engine"> <fmt:message
                        key="form.model_engine"/> </label>
                <input type="text" name="engineType" class="form-control" id="create-model-engine" required>
            </div>
            <!-- /engine -->
            <!-- category -->
            <div class="form-group">
                <label class="col-form-label" for="create-model-category"> <fmt:message
                        key="form.category_name"/> </label>
                <input type="text" name="categoryName" class="form-control" id="create-model-category" required>
            </div>
            <!-- /category -->
        </div>
        <div class="modal-footer">
            <button type="submit" class="btn btn-primary"><fmt:message
                    key="form.button"/></button>
        </div>
    </div>
</form>