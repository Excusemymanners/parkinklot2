<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<t:pageTemplate pageTitle="Users">
    <h1>Users</h1>

    <c:if test="${pageContext.request.isUserInRole('WRITE_USERS')}">
        <div class="mb-3">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/AddUser">Add User</a>
        </div>
    </c:if>

    <%-- Form updated to action="/Users" as requested --%>
    <form method="POST" action="${pageContext.request.contextPath}/Users">
        <div class="container text-center">
            <div class="row font-weight-bold border-bottom pb-2 mb-2">
                <div class="col-1">#</div>
                <div class="col">Username</div>
                <div class="col">Email</div>
                <c:if test="${pageContext.request.isUserInRole('WRITE_USERS')}">
                    <div class="col">Actions</div>
                </c:if>
            </div>

            <c:forEach var="user" items="${users}">
                <div class="row py-2 border-bottom align-items-center">
                    <div class="col-1">
                            <%-- Checkbox name changed to "user_ids" --%>
                        <input type="checkbox" name="user_ids" value="${user.id}">
                    </div>
                    <div class="col">${user.username}</div>
                    <div class="col">${user.email}</div>

                    <c:if test="${pageContext.request.isUserInRole('WRITE_USERS')}">
                        <div class="col">
                            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/EditUser?id=${user.id}">Edit</a>
                        </div>
                    </c:if>
                </div>
            </c:forEach>
        </div>

        <c:if test="${not empty users}">
            <div class="mt-3">
                    <%-- New Invoice button --%>
                <button type="submit" name="invoice" class="btn btn-outline-primary">Invoice</button>

                    <%-- Keep Delete button if user has permissions --%>
                <c:if test="${pageContext.request.isUserInRole('WRITE_USERS')}">
                    <button type="submit" class="btn btn-danger" formaction="${pageContext.request.contextPath}/DeleteUser">Delete Selected</button>
                </c:if>
            </div>
        </c:if>

            <%-- Only display the section if the 'invoices' list is not empty --%>
        <c:if test="${not empty invoices}">
            <h2>Invoices</h2>
            <c:forEach var="username" items="${invoices}" varStatus="status">
                <%-- Display indexed list: 1. Username, 2. Username, etc. --%>
                ${status.index + 1}. ${username}
                <br/>
            </c:forEach>
        </c:if>
    </form>
</t:pageTemplate>