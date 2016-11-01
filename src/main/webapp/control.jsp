<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>iBrator</title>

    <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Open+Sans" />
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/bootstrap-material-design.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/bootstrap-switch.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>

<c:if test="${pageContext.request.userPrincipal.name != null}">
    <%@include  file="navbar.jsp" %>

    <div class="container">
        <%-- Logout --%>
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <%-- Control --%>
        <div>
            <h2>Control - <c:out value="${device.name}"/></h2>

            <form method="post" action="${contextPath}/device/control">
                <input type="hidden" name="deviceId" value="${device.id}">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <div class="form-group">
                    <input class="form-control" min="0" max="100" type="number" name="intensity" required placeholder="Duration"/>
                </div>

                <span class="form-error"><c:out value="${error}"/></span><br/><br/>

                <div class="form-group">
                    <input class="btn btn-default" type="submit" name="intensity" required value="Start"/>
                </div>
            </form>

            <%-- TODO : Stop--%>
        </div>

    </div>
</c:if>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="${contextPath}/resources/js/bootstrap.min.js"></script>

    <script src="${contextPath}/resources/js/material.min.js"></script>
    <script src="${contextPath}/resources/js/ripples.min.js"></script>
    <script>
        $(function () {
            $.material.init();
        })
    </script>

    <script src="${contextPath}/resources/js/bootstrap-switch.min.js"></script>
    <script src="${contextPath}/resources/js/devices.js"></script>
</body>
</html>
