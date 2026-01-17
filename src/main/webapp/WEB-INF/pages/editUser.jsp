<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<t:pageTemplate pageTitle="Edit User">
    <div class="container">
        <h1>Edit User</h1>
        <form class="needs-validation" method="POST" action="${pageContext.request.contextPath}/EditUser">
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="username">Username</label>
                    <input type="text" class="form-control" id="username" name="username"
                           value="${user.username}" readonly>
                    <small class="text-muted">Username cannot be changed.</small>
                </div>
                <div class="col-md-6 mb-3">
                    <label for="email">Email</label>
                    <input type="email" class="form-control" id="email" name="email"
                           value="${user.email}" required>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="password">Password (Leave blank to keep current)</label>
                    <input type="password" class="form-control" id="password" name="password">
                </div>
                <div class="col-md-6 mb-3">
                    <label>Update Roles</label>
                        <%--
                            Note: In a more advanced version, you would check if the user
                            already has these roles to pre-check the boxes.
                        --%>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" name="userGroups" value="READ_CARS" id="roleCars">
                        <label class="form-check-label" for="roleCars">READ_CARS</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" name="userGroups" value="READ_USERS" id="roleUsers">
                        <label class="form-check-label" for="roleUsers">READ_USERS</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" name="userGroups" value="WRITE_CARS" id="roleWriteCars">
                        <label class="form-check-label" for="roleWriteCars">WRITE_CARS</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" name="userGroups" value="WRITE_USERS" id="roleWriteUsers">
                        <label class="form-check-label" for="roleWriteUsers">WRITE_USERS</label>
                    </div>
                </div>
            </div>

            <hr class="mb-4">
            <input type="hidden" name="user_id" value="${user.id}">
            <button class="w-100 btn btn-primary btn-lg" type="submit">Save Changes</button>
        </form>
    </div>
</t:pageTemplate>