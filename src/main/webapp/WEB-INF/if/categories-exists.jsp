<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>

<c:if test="${not empty categories}">
    <div class="container-fluid">
        <div class="col-lg-auto">
            <div class="panel panel-default">
                <div class="panel-heading text-center">
                    <fmt:message key="form.categories_label"/>
                </div>
                <div class="panel-body table-responsive">
                    <table class="table table-condensed table-striped text-center">
                        <thead class="thead-dark">
                        <tr>
                            <th><fmt:message key="form.category_id"/></th>
                            <th><fmt:message key="form.category_name"/></th>
                            <th><fmt:message key="form.category_price"/></th>
                            <th colspan="1"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${categories}" var="category" varStatus="loop">
                            <tr data-toggle="collapse" class="accordion-toggle">
                                <td>${category.id}</td>
                                <td>
                                        ${category.name}
                                        <%--                                    <c:choose>--%>
                                        <%--                                        <c:when test="${category.name == 'ECONOMY'}">--%>
                                        <%--                                            <fmt:message key="form.model_category_economy"/>--%>
                                        <%--                                        </c:when>--%>
                                        <%--                                        <c:when test="${category.name == 'SPORT'}">--%>
                                        <%--                                            <fmt:message key="form.model_category_sport"/>--%>
                                        <%--                                        </c:when>--%>
                                        <%--                                        <c:when test="${category.name == 'LUXURY'}">--%>
                                        <%--                                            <fmt:message key="form.model_category_luxury"/>--%>
                                        <%--                                        </c:when>--%>
                                        <%--                                        <c:when test="${category.name == 'BUSINESS'}">--%>
                                        <%--                                            <fmt:message key="form.model_category_business"/>--%>
                                        <%--                                        </c:when>--%>
                                        <%--                                    </c:choose>--%>
                                </td>
                                <td>${category.pricePerDay}$</td>
                                <td>
                                    <button type="button" class="btn btn-dark btn-xs editcategorydbtn"
                                            data-toggle="modal"
                                            data-target="#edit-category-modal">
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
    <div class="modal fade" id="edit-category-modal" tabindex="-1" role="dialog" aria-labelledby="edit-modal-label"
         aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="edit-modal-label"><fmt:message key="form.edit_category"/></h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body" id="attachment-body-content">
                    <jsp:include page="/WEB-INF/form/edit-category-admin.jsp"/>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary" form="edit-category-form" value="Submit"><fmt:message
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