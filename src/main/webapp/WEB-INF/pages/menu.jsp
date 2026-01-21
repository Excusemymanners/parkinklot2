<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<header data-bs-theme="dark">
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}">ParkingLot</a>

            <div class="collapse navbar-collapse" id="navbarCollapse">
                <ul class="navbar-nav me-auto mb-2 mb-md-0">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/about.jsp">About</a>
                    </li>

                    <%-- Show 'Cars' link to users with READ_CARS role --%>
                    <c:if test="${pageContext.request.isUserInRole('READ_CARS')}">
                        <li class="nav-item">
                            <a class="nav-link ${pageContext.request.requestURI.endsWith('/Cars') ? 'active' : ''}"
                               href="${pageContext.request.contextPath}/Cars">Cars</a>
                        </li>
                    </c:if>

                    <%-- Show 'Users' management link to users with READ_USERS role --%>
                    <c:if test="${pageContext.request.isUserInRole('READ_USERS')}">
                        <li class="nav-item">
                            <a class="nav-link ${pageContext.request.requestURI.endsWith('/Users') ? 'active' : ''}"
                               href="${pageContext.request.contextPath}/Users">Users</a>
                        </li>

                    </c:if>
                </ul>

                <%-- Authentication Section --%>
                <ul class="navbar-nav ms-auto">
                    <c:choose>
                        <c:when test="${empty pageContext.request.remoteUser}">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/Login">Login</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item d-flex align-items-center">
                                <span class="navbar-text me-3 text-light">User: ${pageContext.request.remoteUser}</span>
                                <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/Logout">Logout</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                    
                </ul>
            </div>
        </div>
    </nav>
</header>