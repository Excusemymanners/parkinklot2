<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<t:pageTemplate pageTitle="Cars">
    <h1>Cars</h1>
    <p>Free Spots: ${numberOfFreeParkingSpots}</p>

    <div class="mb-3">
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/AddCar">Add Car</a>
    </div>

    <form method="POST" action="${pageContext.request.contextPath}/DeleteCar">
        <div class="container text-center">
            <div class="row font-weight-bold border-bottom pb-2 mb-2">
                <div class="col-1">#</div> <%-- Checkbox column --%>
                <div class="col">License Plate</div>
                <div class="col">Parking Spot</div>
                <div class="col">Owner</div>
                <div class="col">Actions</div>
            </div>

            <c:forEach var="car" items="${cars}">
                <div class="row py-2 border-bottom align-items-center">
                    <div class="col-1">
                        <input type="checkbox" name="carIds" value="${car.id}">
                    </div>
                    <div class="col">${car.licensePlate}</div>
                    <div class="col">${car.parkingSpot}</div>
                    <div class="col">${car.ownerName}</div>
                    <div class="col">
                        <a class="btn btn-secondary btn-sm" href="${pageContext.request.contextPath}/EditCar?id=${car.id}">Edit</a>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div class="mt-3">
            <button type="submit" class="btn btn-danger">Delete Selected</button>
        </div>
    </form>
</t:pageTemplate>