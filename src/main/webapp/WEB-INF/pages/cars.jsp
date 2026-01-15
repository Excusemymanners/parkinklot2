<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %> <%-- Updated URI for Jakarta EE 10 --%>

<t:pageTemplate pageTitle="Cars">
    <h1>Cars</h1>
    <p>Free Spots: ${numberOfFreeParkingSpots}</p>

    <div class="container text-center">
        <div class="row font-weight-bold">
            <div class="col">License Plate</div>
            <div class="col">Parking Spot</div>
            <div class="col">Owner</div>
        </div>

        <c:forEach var="car" items="${cars}">
            <div class="row">
                <div class="col">
                        ${car.licensePlate}
                </div>
                <div class="col">
                        ${car.parkingSpot}
                </div>
                <div class="col">
                        ${car.ownerName}
                </div>
            </div>
        </c:forEach>
    </div>
</t:pageTemplate>