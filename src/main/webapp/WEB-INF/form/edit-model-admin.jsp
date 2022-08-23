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

<form id="edit-model-form" class="form-horizontal" method="POST"
      action="${pageContext.request.contextPath}/admin-edit-model">
    <div class="card text-white bg-dark mb-0">
        <div class="card-header">
            <h2 class="m-0"><fmt:message
                    key="form.edit_model"/></h2>
        </div>
        <div class="card-body">
            <!-- id -->
            <div class="form-group" hidden>
                <label class="col-form-label" for="edit-model-id"> <fmt:message
                        key="form.model_id"/> </label>
                <input type="text" name="modelId" class="form-control" id="edit-model-id" required>
            </div>
            <!-- /id -->
            <!-- name -->
            <div class="form-group">
                <label class="col-form-label" for="edit-model-name"> <fmt:message
                        key="form.model_name"/> </label>
                <input type="text" name="modelName" class="form-control" id="edit-model-name" required>
            </div>
            <!-- /name -->
            <!-- brand -->
            <div class="form-group">
                <label class="col-form-label" for="edit-model-brand"> <fmt:message
                        key="form.brand_name"/> </label>
                <input type="text" name="brandName" class="form-control" id="edit-model-brand" required readonly>
            </div>
            <!-- /brand -->
            <!-- transmission -->
            <div class="form-group">
                <label class="col-form-label" for="edit-model-transmission"> <fmt:message
                        key="form.model_transmission"/> </label>
                <input type="text" name="transmission" class="form-control" id="edit-model-transmission" required>
            </div>
            <!-- /transmission -->
            <!-- engine -->
            <div class="form-group">
                <label class="col-form-label" for="edit-model-engine"> <fmt:message
                        key="form.model_engine"/> </label>
                <input type="text" name="engineType" class="form-control" id="edit-model-engine" required>
            </div>
            <!-- /engine -->
            <!-- category -->
            <div class="form-group">
                <label class="col-form-label" for="edit-model-category"> <fmt:message
                        key="form.category_name"/> </label>
                <input type="text" name="categoryName" class="form-control" id="edit-model-category" required>
            </div>
            <!-- /category -->
        </div>
    </div>
</form>