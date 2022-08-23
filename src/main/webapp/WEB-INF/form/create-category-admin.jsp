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

<form id="create-brand-form" class="form-horizontal" method="POST"
      action="${pageContext.request.contextPath}/admin-create-category">
    <div class="card text-white bg-dark mb-0">
        <div class="card-header">
            <h2 class="m-0"><fmt:message
                    key="form.create_category"/></h2>
        </div>
        <div class="card-body">
            <!-- name -->
            <div class="form-group">
                <label class="col-form-label" for="create-category-name"> <fmt:message
                        key="form.category_name"/> </label>
                <input type="text" name="categoryName" class="form-control" id="create-category-name" required>
            </div>
            <!-- /name -->
            <!-- name -->
            <div class="form-group">
                <label class="col-form-label" for="create-category-price"> <fmt:message
                        key="form.category_price"/> </label>
                <input type="number" name="price" class="form-control" id="create-category-price" required>
            </div>
            <!-- /name -->
        </div>
        <div class="modal-footer">
            <button type="submit" class="btn btn-primary"><fmt:message
                    key="form.button"/></button>
        </div>
    </div>
</form>