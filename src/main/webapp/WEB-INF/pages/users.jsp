<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<t:pageTemplate pageTitle="Users">
    <h1>Users</h1>

    <%-- Only show 'Add User' button if user has WRITE_USERS role --%>
    <c:if test="${pageContext.request.isUserInRole('WRITE_USERS')}">
        <div class="mb-3">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/AddUser">Add User</a>
        </div>
    </c:if>

    <form method="POST" action="${pageContext.request.contextPath}/DeleteUser">
        <div class="container text-center">
            <div class="row font-weight-bold border-bottom pb-2 mb-2">
                <div class="col-1">#</div>
                <div class="col">Username</div>
                <div class="col">Email</div>
                    <%-- Actions header only visible to writers --%>
                <c:if test="${pageContext.request.isUserInRole('WRITE_USERS')}">
                    <div class="col">Actions</div>
                </c:if>
            </div>

            <c:forEach var="user" items="${users}">
                <div class="row py-2 border-bottom align-items-center">
                    <div class="col-1">
                            <%-- Hide checkboxes if user cannot delete --%>
                        <c:if test="${pageContext.request.isUserInRole('WRITE_USERS')}">
                            <input type="checkbox" name="userIds" value="${user.id}">
                        </c:if>
                    </div>
                    <div class="col">${user.username}</div>
                    <div class="col">${user.email}</div>

                        <%-- Edit button only visible to writers --%>
                    <c:if test="${pageContext.request.isUserInRole('WRITE_USERS')}">
                        <div class="col">
                            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/EditUser?id=${user.id}">Edit</a>
                        </div>
                    </c:if>
                </div>
            </c:forEach>
        </div>

            <%-- Only show 'Delete' button if the user has WRITE_USERS role --%>
        <c:if test="${not empty users and pageContext.request.isUserInRole('WRITE_USERS')}">
            <div class="mt-3">
                <button type="submit" class="btn btn-danger">Delete Selected</button>
            </div>
        </c:if>
    </form>
</t:pageTemplate>