<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>

<c:if test="${not empty brands}">
    <div class="container-fluid">
        <div class="col-lg-auto">
            <div class="panel panel-default">
                <div class="panel-heading text-center">
                    <fmt:message key="form.brands_label"/>
                </div>
                <div class="panel-body table-responsive">
                    <table class="table table-condensed table-striped text-center">
                        <thead class="thead-dark">
                        <tr>
                            <th></th>
                            <th><fmt:message key="form.brand_id"/></th>
                            <th><fmt:message key="form.brand_name"/></th>
                            <th colspan="1"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${brands}" var="brand" varStatus="loop">
                            <tr data-toggle="collapse" data-target="#models_${loop.index}" class="accordion-toggle">
                                <td>
                                    <button class="btn btn-dark btn-xs">
                                        <i class="fa fa-eye" aria-hidden="true"></i>
                                    </button>
                                </td>
                                <td>${brand.id}</td>
                                <td>${brand.name}</td>
                                <td>
                                    <button type="button" class="btn btn-dark btn-xs editbrandbtn" data-toggle="modal"
                                            data-target="#edit-brand-modal">
                                        <i class="fa fa-check-square-o" aria-hidden="true"></i>
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="12" class="hiddenRow">
                                    <div class="accordian-body collapse col-lg-auto table-responsive"
                                         id="models_${loop.index}">
                                        <table class="table table-striped text-center" id="innerTable">
                                            <thead class="thead-dark">
                                            <tr class="info">
                                                <th><fmt:message key="form.brand_name"/></th>
                                                <th><fmt:message key="form.model"/></th>
                                                <th><fmt:message key="form.model_transmission"/></th>
                                                <th><fmt:message key="form.model_engine"/></th>
                                                <th><fmt:message key="form.model_category"/></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${brand.models}" var="model" varStatus="loop">
                                                <tr class="info">
                                                    <td>${brand.name}</td>
                                                    <td>${model.name}</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${model.transmission == 'MANUAL'}">
                                                                <fmt:message key="form.model_transmission_manual"/>
                                                            </c:when>
                                                            <c:when test="${model.transmission == 'AUTOMATIC'}">
                                                                <fmt:message key="form.model_transmission_automatic"/>
                                                            </c:when>
                                                            <c:when test="${model.transmission == 'ROBOT'}">
                                                                <fmt:message key="form.model_transmission_robot"/>
                                                            </c:when>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${model.engineType == 'FUEL'}">
                                                                <fmt:message key="form.model_engine_fuel"/>
                                                            </c:when>
                                                            <c:when test="${model.engineType == 'ELECTRIC'}">
                                                                <fmt:message key="form.model_engine_electric"/>
                                                            </c:when>
                                                            <c:when test="${model.engineType == 'GAS'}">
                                                                <fmt:message key="form.model_engine_gas"/>
                                                            </c:when>
                                                            <c:when test="${model.engineType == 'DIESEL'}">
                                                                <fmt:message key="form.model_engine_diesel"/>
                                                            </c:when>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${model.category == 'ECONOMY'}">
                                                                <fmt:message key="form.model_category_economy"/>
                                                            </c:when>
                                                            <c:when test="${model.category == 'SPORT'}">
                                                                <fmt:message key="form.model_category_sport"/>
                                                            </c:when>
                                                            <c:when test="${model.category == 'LUXURY'}">
                                                                <fmt:message key="form.model_category_luxury"/>
                                                            </c:when>
                                                            <c:when test="${model.category == 'BUSINESS'}">
                                                                <fmt:message key="form.model_category_business"/>
                                                            </c:when>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
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
    <div class="modal fade" id="edit-brand-modal" tabindex="-1" role="dialog" aria-labelledby="edit-modal-label"
         aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="edit-modal-label"><fmt:message key="form.edit_brand"/></h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body" id="attachment-body-content">
                    <jsp:include page="/WEB-INF/form/edit-brand-admin.jsp"/>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary" form="edit-brand-form" value="Submit"><fmt:message
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