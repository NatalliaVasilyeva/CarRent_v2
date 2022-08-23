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

<form id="edit-category-form" class="form-horizontal" method="POST"
      action="${pageContext.request.contextPath}/admin-edit-category">
    <div class="card text-white bg-dark mb-0">
        <div class="card-header">
            <h2 class="m-0"><fmt:message
                    key="form.edit_category"/></h2>
        </div>
        <div class="card-body">
            <!-- id -->
            <div class="form-group" hidden>
                <label class="col-form-label" for="edit-category-id"> <fmt:message
                        key="form.category_id"/> </label>
                <input type="text" name="categoryId" class="form-control" id="edit-category-id" required>
            </div>
            <!-- /id -->
            <!-- name -->
            <div class="form-group">
                <label class="col-form-label" for="edit-category-name"> <fmt:message
                        key="form.category_name"/> </label>
                <input type="text" name="categoryName" class="form-control" id="edit-category-name" required>
            </div>
            <!-- /name -->
            <!-- price -->
            <div class="form-group">
                <label class="col-form-label" for="edit-category-price"> <fmt:message
                        key="form.category_price"/> </label>
                <input type="text" name="price" class="form-control" id="edit-category-price" required>
            </div>
            <!-- /price -->
        </div>
    </div>
</form>