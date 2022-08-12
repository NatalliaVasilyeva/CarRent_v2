<%--
  Created by IntelliJ IDEA.
  User: natallia.vasilyeva
  Date: 25/07/2022
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${not empty success_message}">
    <div class="alert alert-success">
        <p>${success_message}</p>
    </div>
</c:if>

<c:if test="${not empty param.success_message}">
    <div class="alert alert-success">
        <p>${param.success_message}</p>
    </div>
</c:if>