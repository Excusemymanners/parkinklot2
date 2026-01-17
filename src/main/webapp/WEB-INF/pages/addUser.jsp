<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:pageTemplate pageTitle="Add User">
    <h1>Add User</h1>
    <form class="needs-validation" method="POST" action="${pageContext.request.contextPath}/AddUser">
        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="username">Username</label>
                <input type="text" class="form-control" id="username" name="username" required>
            </div>
            <div class="col-md-6 mb-3">
                <label for="email">Email</label>
                <input type="email" class="form-control" id="email" name="email" required>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="password">Password</label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>
            <div class="col-md-6 mb-3">
                <label>Groups</label>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" name="userGroups" value="READ_CARS" id="group1">
                    <label class="form-check-label" for="group1">Read Cars</label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" name="userGroups" value="READ_USERS" id="group2">
                    <label class="form-check-label" for="group2">Read Users</label>
                </div>
            </div>
        </div>

        <button class="btn btn-primary btn-lg" type="submit">Save User</button>
    </form>
</t:pageTemplate>