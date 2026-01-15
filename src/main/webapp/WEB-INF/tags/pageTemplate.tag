<%@tag description="base page template" pageEncoding="UTF-8"%>
<%@attribute name="pageTitle"%>

<!DOCTYPE html>
<html>
<head>
    <title>${pageTitle}</title>
</head>
<body>

    <a href="${pageContext.request.contextPath}/index.jsp">Home</a>
    <a href="${pageContext.request.contextPath}/about.jsp">About</a>


<jsp:doBody/>
</body>
</html>