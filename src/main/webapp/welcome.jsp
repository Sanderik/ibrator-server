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

            <h3> My iBrators: </h3>
            <form id="changeDeviceState" method="POST" action="${contextPath}/device/change">
            <table class="table table-responsive">
                <tr>
                    <th>Name</th>
                    <th>&nbsp;</th>
                    <th>Active</th>
                </tr>

                <c:forEach var="device" items="${devices}">
                    <tr>
                        <td><c:out value="${device.name}"/></td>
                        <td><a href="${contextPath}/device/${device.id}/control">Control</a> </td>
                        <td>
                            <c:choose>
                                <c:when test="${device.active == true}">
                                    <input type="checkbox" id="is-active" name="active" checked data-size="mini">
                                </c:when>
                                <c:otherwise>
                                    <input type="checkbox" id="is-active" name="active" data-size="mini">
                                </c:otherwise>
                            </c:choose>

                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" name="id" value="${device.id}"/>
                        </td>
                    </tr>
                </c:forEach>
                </table>
                </form>

            <form id="addDeviceForm" method="POST" action="${contextPath}/device/add">
                <h3> Add an iBrator </h3>

                <div class="form-group">
                    <input class="form-control" type="text" name="name" required placeholder="Name"/>
                </div>

                <div class="form-group">
                    <input class="form-control" type="text" name="connectionToken" required placeholder="Connection token*"/>
                </div>

                <span class="form-error"><c:out value="${model.error}"/></span>

                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="submit" class="btn btn-raised btn-primary" value="Add">
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
    <script src="${contextPath}/resources/js/devices.js"></script>
</body>
</html>
