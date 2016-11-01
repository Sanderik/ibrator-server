<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="SandErik">

    <title>iBrator</title>

    <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Open+Sans" />
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/bootstrap-material-design.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
</head>

<body>

<%@include  file="navbar.jsp" %>

<div class="container">

    <form method="POST" action="${contextPath}/login" class="form-signin">
        <h2 class="text-center">Log in</h2><br/>

        <div class="form-group ${error != null ? 'has-error' : ''}">
            <div class="text-center">${message}</div>
            <input name="username" type="text" class="form-control" placeholder="E-mail" autofocus="true"/>
            <input name="password" type="password" class="form-control" placeholder="Password"/>
            <div class="text-center">${error}</div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <br/>
            <a href="#">Forgot password?</a>

            <button class="btn btn-lg btn-raised btn-primary btn-block" type="submit">Log in</button> <br/>
            <div class="text-center">New here?</div>
            <h4 class="text-center"><a href="${contextPath}/registration">Create an account</a></h4>
        </div>
    </form>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>

<script src="${contextPath}/resources/js/material.min.js"></script>
<script src="${contextPath}/resources/js/ripples.min.js"></script>
<script>
    $(function () {
        $.material.init();
    })
</script>
</body>
</html>
