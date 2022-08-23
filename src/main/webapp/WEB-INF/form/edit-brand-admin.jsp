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

<form id="edit-brand-form" class="form-horizontal" method="POST"
      action="${pageContext.request.contextPath}/admin-edit-brand">
    <div class="card text-white bg-dark mb-0">
        <div class="card-header">
            <h2 class="m-0"><fmt:message
                    key="form.edit_brand"/></h2>
        </div>
        <div class="card-body">
            <!-- id -->
            <div class="form-group" hidden>
                <label class="col-form-label" for="edit-brand-id"> <fmt:message
                        key="form.brand_id"/> </label>
                <input type="text" name="brandId" class="form-control" id="edit-brand-id" required>
            </div>
            <!-- /id -->
            <!-- name -->
            <div class="form-group">
                <label class="col-form-label" for="edit-brand-name"> <fmt:message
                        key="form.brand_name"/> </label>
                <input type="text" name="brandName" class="form-control" id="edit-brand-name" required>
            </div>
            <!-- /name -->
        </div>
    </div>
</form>