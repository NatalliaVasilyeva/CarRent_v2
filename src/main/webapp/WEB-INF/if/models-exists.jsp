<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>

<c:if test="${not empty models}">
    <div class="container-fluid">
        <div class="col-lg-auto">
            <div class="panel panel-default">
                <div class="panel-heading text-center">
                    <fmt:message key="form.models_label"/>
                </div>
                <div class="panel-body table-responsive">
                    <table class="table table-condensed table-striped text-center">
                        <thead class="thead-dark">
                        <tr>
                            <th><fmt:message key="form.model_id"/></th>
                            <th><fmt:message key="form.model_name"/></th>
                            <th><fmt:message key="form.brand_name"/></th>
                            <th><fmt:message key="form.model_transmission"/></th>
                            <th><fmt:message key="form.model_engine"/></th>
                            <th><fmt:message key="form.model_category"/></th>
                            <th><fmt:message key="form.model_price"/></th>
                            <th colspan="1"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${models}" var="model" varStatus="loop">
                            <tr data-toggle="collapse" class="accordion-toggle">
                                <td>${model.id}</td>
                                <td>${model.name}</td>
                                <td>${model.brandName}</td>
                                <td>${model.transmission}</td>
                                <td>${model.engineType}</td>
                                <td>${model.category}</td>
                                <td>${model.price}$</td>
                                <td>
                                    <button type="button" class="btn btn-dark btn-xs editmodelbtn"
                                            data-toggle="modal"
                                            data-target="#edit-model-modal">
                                        <i class="fa fa-check-square-o" aria-hidden="true"></i>
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <!-- Attachment Modal -->
    <div class="modal fade" id="edit-model-modal" tabindex="-1" role="dialog" aria-labelledby="edit-modal-label"
         aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="edit-modal-label"><fmt:message key="form.edit_model"/></h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body" id="attachment-body-content">
                    <jsp:include page="/WEB-INF/form/edit-model-admin.jsp"/>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary" form="edit-model-form" value="Submit"><fmt:message
                            key="form.submit_button"/>
                    </button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message
                            key="form.close_button"/></button>
                </div>
            </div>
        </div>
    </div>
    <!-- /Attachment Modal -->
</c:if>