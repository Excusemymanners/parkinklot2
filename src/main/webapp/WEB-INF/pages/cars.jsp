<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<t:pageTemplate pageTitle="Cars">
    <h1>Cars</h1>
    <p>Free Spots: ${numberOfFreeParkingSpots}</p>

    <%-- Show 'Add Car' button only if the user has WRITE_CARS role --%>
    <c:if test="${pageContext.request.isUserInRole('WRITE_CARS')}">
        <div class="mb-3">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/AddCar">Add Car</a>
        </div>
    </c:if>

    <form method="POST" action="${pageContext.request.contextPath}/DeleteCar">
        <div class="container text-center">
            <div class="row font-weight-bold border-bottom pb-2 mb-2">
                <div class="col-1">#</div>
                <div class="col">License Plate</div>
                <div class="col">Parking Spot</div>
                <div class="col">Owner</div>
                <div class="col">Actions</div>
            </div>

            <c:forEach var="car" items="${cars}">
                <div class="row py-2 border-bottom align-items-center">
                    <div class="col-1">
                            <%-- Only show checkboxes if user can delete --%>
                        <c:choose>
                            <c:when test="${pageContext.request.isUserInRole('WRITE_CARS')}">
                                <input type="checkbox" name="carIds" value="${car.id}">
                            </c:when>
                            <c:otherwise>
                                <i class="bi bi-lock-fill text-muted"></i>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col">${car.licensePlate}</div>
                    <div class="col">${car.parkingSpot}</div>
                    <div class="col">${car.ownerName}</div>
                    <div class="col">
                            <%-- Only show Edit button if user has permission --%>
                        <c:if test="${pageContext.request.isUserInRole('WRITE_CARS')}">
                            <a class="btn btn-secondary btn-sm" href="${pageContext.request.contextPath}/EditCar?id=${car.id}">Edit</a>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </div>

            <%-- Only show 'Delete Selected' button if the user has WRITE_CARS role --%>
        <c:if test="${not empty cars and pageContext.request.isUserInRole('WRITE_CARS')}">
            <div class="mt-3">
                <button type="submit" class="btn btn-danger">Delete Selected</button>
            </div>
        </c:if>
    </form>
</t:pageTemplate>