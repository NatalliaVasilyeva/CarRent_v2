<%--
  Created by IntelliJ IDEA.
  User: natallia.vasilyeva
  Date: 25/07/2022
  Time: 15:30
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${not empty error_message}">
    <div class="alert alert-danger">
        <p>${error_message}</p>
    </div>
</c:if>