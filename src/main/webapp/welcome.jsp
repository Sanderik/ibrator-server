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

    <title>Create an account</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <div class="container">

            <form id="logoutForm" method="POST" action="${contextPath}/logout">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>

            <h2>IBrator - Welcome ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2><br/>

            <h3> My iBrators: </h3>
            <table class="table table-responsive">
                <tr>
                    <th>Name</th>
                    <th>Connection token</th>
                    <th>Active</th>
                </tr>

                <c:forEach var="device" items="${devices}">
                    <tr>
                        <td><c:out value="${device.name}"/></td>
                        <td><c:out value="${device.connectionToken}"/></td>
                        <td><c:out value="${device.active == true ? 'Yes' : 'No'}"/></td>
                    </tr>
                </c:forEach>
                </table>

            <form id="addDeviceForm" method="POST" action="${contextPath}/device/add">
                <h3> Add an iBrator </h3>

                <div class="form-group">
                    <input class="form-control" type="text" name="name" required placeholder="Name"/>
                </div>

                <div class="form-group">
                    <input class="form-control" type="text" name="connectionToken" required placeholder="Connection token*"/>
                </div>


                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="submit" class="btn btn-default" value="Add">
            </form>

        </div>
    </c:if>

<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
