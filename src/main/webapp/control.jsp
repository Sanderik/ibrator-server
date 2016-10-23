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
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">

    <!-- /container -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${contextPath}/resources/js/bootstrap-switch.min.js"></script>
    <script src="${contextPath}/resources/js/devices.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>

<c:if test="${pageContext.request.userPrincipal.name != null}">
    <nav class="navbar navbar-inverse navbar-static-top">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">iBrator</a>
            </div>
            <div class="navbar-right">
                <span class="navbar-brand">Welcome ${pageContext.request.userPrincipal.name} | <a class="logout-button" onclick="document.forms['logoutForm'].submit()">Logout</a></span>
            </div>
        </div>
    </nav>

    <div class="container">

        <%-- Logout --%>
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <%-- Control --%>
        <div>
            <h2>Control - <c:out value="${device.name}"/></h2>

            <form method="post" action="${contextPath}/device/control">
                <div class="form-group">
                    <input class="form-control" type="text" name="duration" required placeholder="Duration"/>
                </div>

                <div class="form-group">
                    <input class="btn btn-default" type="submit" name="duration" required placeholder="Start"/>
                </div>
            </form>

            <%-- TODO : Stop--%>
        </div>

    </div>
</c:if>

</body>
</html>
