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

<form id="see-car-form" class="form-horizontal">
    <div class="card text-white bg-dark mb-0">
        <div class="card-header">
            <h2 class="m-0">Edit order</h2>
        </div>
        <div class="card-body">
            <img width="250" height="250" class="card-img-top" scr=""
                 id="see-car-img" alt="Card image cap">
            <!-- id -->
            <div class="form-group" hidden>
                <label class="col-form-label" for="see-car-id">Id</label>
                <input type="text" name="carId" class="form-control" id="see-car-id" required readonly>
            </div>
            <!-- /id -->
            <!-- brand -->
            <div class="form-group">
                <label class="col-form-label" for="see-car-brand">Brand </label>
                <input type="text" name="brand" class="form-control" id="see-car-brand" required readonly>
            </div>
            <!-- /brand -->
            <!-- model -->
            <div class="form-group">
                <label class="col-form-label" for="see-car-model">Model </label>
                <input type="text" name="model" class="form-control" id="see-car-model" required readonly>
            </div>
            <!-- /model -->
            <!-- transmission -->
            <div class="form-group">
                <label class="col-form-label" for="see-car-transmission">Transmission </label>
                <input type="text" name="transmission" class="form-control" id="see-car-transmission" required readonly>
            </div>
            <!-- /transmission -->
            <!-- engineType -->
            <div class="form-group">
                <label class="col-form-label" for="see-car-engineType">Engine type </label>
                <input type="text" name="engineType" class="form-control" id="see-car-engineType" required readonly>
            </div>
            <!-- /engineType -->
            <!-- color -->
            <div class="form-group">
                <label class="col-form-label" for="see-car-color">Color </label>
                <input type="text" name="color" class="form-control" id="see-car-color" required readonly>
            </div>
            <!-- /color -->
            <!-- yearOfProduction -->
            <div class="form-group">
                <label class="col-form-label" for="see-car-yearOfProduction">Year of production </label>
                <input type="text" name="yearOfProduction" class="form-control" id="see-car-yearOfProduction" required
                       readonly>
            </div>
            <!-- /yearOfProduction -->
            <!-- pricePerDay -->
            <div class="form-group">
                <label class="col-form-label" for="see-car-pricePerDay">Price per day </label>
                <input type="text" name="pricePerDay" class="form-control" id="see-car-pricePerDay" required readonly>
            </div>
        </div>
    </div>
</form>