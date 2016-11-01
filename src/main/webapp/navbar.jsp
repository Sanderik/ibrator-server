<nav class="navbar navbar-default">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">iBrator</a>
        </div>
        <div class="navbar-right">
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <span class="navbar-brand">Welcome ${pageContext.request.userPrincipal.name} | <a class="logout-button" onclick="document.forms['logoutForm'].submit()">Logout</a></span>
            </c:if>
        </div>
    </div>
</nav>