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
      action="${pageContext.request.contextPath}/admin-create-brand">
    <div class="card text-white bg-dark mb-0">
        <div class="card-header">
            <h2 class="m-0"><fmt:message
                    key="form.create_brand"/></h2>
        </div>
        <div class="card-body">
            <!-- name -->
            <div class="form-group">
                <label class="col-form-label" for="create-brand-name"> <fmt:message
                        key="form.brand_name"/> </label>
                <input type="text" name="brandName" class="form-control" id="create-brand-name" required>
            </div>
            <!-- /name -->
        </div>
        <div class="modal-footer">
            <button type="submit" class="btn btn-primary"><fmt:message
                    key="form.button"/></button>
        </div>
    </div>
</form>