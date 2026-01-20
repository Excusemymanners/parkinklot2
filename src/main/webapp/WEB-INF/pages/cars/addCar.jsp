    <%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
    <%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
    <%@taglib prefix="c" uri="jakarta.tags.core" %> <%-- Updated URI for Jakarta EE 10 --%>


    <t:pageTemplate pageTitle="AddCar">
        <div class="container mt-5">
            <h2>Add New Car</h2>

            <form class="needs-validation" novalidate method="POST" action="${pageContext.request.contextPath}/AddCar">

                <div class="row">
                    <div class="col-md-4 mb-3">
                        <label for="license_Plate" class="form-label">License Plate</label>

                            <%-- Added name="licensePlate" below --%>
                        <input type="text"
                               class="form-control"
                               id="license_Plate"
                               name="licensePlate"
                               placeholder="B-123-ABC"
                               required>

                        <div class="invalid-feedback">
                            License plate is required.
                        </div>
                        <label for="parking_Spot" class="form-label">ParkingSpot</label>
                        <input type="text"
                        class="form-control"
                        id="parking_spot"
                        name="parkingSpot"
                        placeholder="Parking Spot"
                               required>
                        <div class="invalid-feedback">
                            Choose a valid Parking Spot.
                        </div>

                        <div class="mb-3">
                            <label for="ownerName" class="form-label">Owner</label>
                                <%-- Change name="ownerName" to name="ownerId" --%>
                            <select class="form-select" id="owner_id" name="ownerId" required>
                                <option value="" selected disabled>Choose Owner...</option>
                                <c:forEach var="user" items="${users}">
                                    <option value="${user.id}">${user.username}</option>
                                </c:forEach>
                            </select>
                            <div class="invalid-feedback">Owner is required.</div>
                        </div>

                    </div>
                </div>

                <hr class="mb-4">

                <button class="w-100 btn btn-primary btn-lg" type="submit">
                    Save Car
                </button>

            </form>
        </div>
    </t:pageTemplate>