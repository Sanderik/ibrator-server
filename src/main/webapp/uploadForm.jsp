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
    <link href="${contextPath}/resources/css/bootstrap-switch.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/bootstrap-material-design.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>

<c:if test="${pageContext.request.userPrincipal.name != null}">
    <%@include  file="navbar.jsp" %>

    <div class="container">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h3>Firmware uploader</h3>


        <form method="POST" enctype="multipart/form-data" action="/admin">
            <div class="form-group">
                <label for="input-file">File input</label>
                <input type="file" class="form-control-file" name="file" id="input-file" aria-describedby="fileHelp">
                <small id="fileHelp" class="form-text text-muted">Upload new firmware file for ESP8266</small>

                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </div>
            <input type="submit" class="btn btn-default" value="Upload" />
            <br>
            <div class="text-success">${message}</div>
            <div class="text-danger">${error}</div>

        </form>

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
    </body>
</html>