<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<t:pageTemplate pageTitle="Edit User">
    <div class="container">
        <h1 class="my-4">Edit User</h1>

        <form class="needs-validation" method="POST" action="${pageContext.request.contextPath}/EditUser">
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="username" class="form-label">Username</label>
                    <input type="text" class="form-control" id="username" name="username"
                           value="${user.username}" readonly>
                    <small class="text-muted">Username cannot be changed.</small>
                </div>

                <div class="col-md-6 mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" name="email"
                           value="${user.email}" required>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="password" class="form-label">Password (Leave blank to keep current)</label>
                    <input type="password" class="form-control" id="password" name="password">
                </div>

                <div class="col-md-6 mb-3">
                    <label class="form-label">Update Roles</label>
                    <div class="card p-3 shadow-sm">
                        <c:forEach var="group" items="${allGroups}">
                            <div class="form-check">
                                    <%-- Determine if this group should be checked --%>
                                <c:set var="isChecked" value="false" />
                                <c:forEach var="currentGroup" items="${currentUserGroups}">
                                    <c:if test="${currentGroup eq group}">
                                        <c:set var="isChecked" value="true" />
                                    </c:if>
                                </c:forEach>

                                <input class="form-check-input" type="checkbox" name="userGroups"
                                       value="${group}" id="role_${group}"
                                       <c:if test="${isChecked}">checked</c:if>>
                                <label class="form-check-label" for="role_${group}">
                                        ${group}
                                </label>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <hr class="mb-4">

                <%-- Hidden field to pass the user ID to the Servlet --%>
            <input type="hidden" name="user_id" value="${user.id}">

            <div class="d-flex gap-2">
                <button class="btn btn-primary btn-lg flex-grow-1" type="submit">Save Changes</button>
                <a href="${pageContext.request.contextPath}/Users" class="btn btn-secondary btn-lg">Cancel</a>
            </div>
        </form>
    </div>
</t:pageTemplate>