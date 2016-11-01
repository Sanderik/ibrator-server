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
    <link href="${contextPath}/resources/css/nouislider.min.css" rel="stylesheet">
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
            <table class="table table-responsive">
                <tr>
                    <th>Name</th>
                    <th>Control</th>
                </tr>

                <c:forEach var="device" items="${devices}">
                    <form id="form-control-device-${device.id}" method="post" action="${contextPath}/device/control">
                        <tr style="height: 105px;">
                            <td class="col-md-4 col-xs-3"><c:out value="${device.name}"/></td>
                            <td class="col-md-8 col-xs-9">
                                <div class="slider shor" id="device-${device.id}"></div>
                                <div class="noUi-pips noUi-pips-horizontal" id="pips-range-${device.id}">
                                </div>
                                <input type="hidden" name="intensity">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="hidden" name="deviceId" value="${device.id}">
                                <br>
                                <span id="error-device-${device.id}" class="form-error hidden">No active session found, please turn on your device first and make sure it's connected to the internet.</span>
                            </td>
                        </tr>
                    </form>
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
    <script src="${contextPath}/resources/js/nouislider.min.js"></script>
    <script>
        $(function () {
            $.material.init();
        });


        $(document).ready(function() {
            $('.slider').each(function() {
                var slider = this;

                var range_all_sliders = {
                    'min': [ 0 ],
                    '50%': [ 50 ],
                    'max': [ 100 ]
                };

                noUiSlider.create(slider, {
                    start: [ 0 ],

                    range: range_all_sliders,
                    pips: {
                        mode: 'range',
                        density: 3
                    }
                });

                slider.noUiSlider.on('set', function(value){
                    var intensity = Math.floor(value);
                    $('input[name="intensity"]').val(intensity);

                    var id = this.target.id;

                    $.ajax({
                        type: 'post',
                        url: '${contextPath}/device/control',
                        data: $('#form-control-' + this.target.id).serialize(),
                        success: function (data, textStatus, xhr) {
                            if (xhr.status != 200) {
                                raiseNoConnection(id);
                            }
                        },error: function(data, textStatus, xhr){
                            raiseNoConnection(id);
                        }
                    });
                });
            });
        });

        function raiseNoConnection(id) {
            var span = $('#error-' + id);
            console.log(span);
            span.removeClass("hidden");
        }
    </script>

    <script src="${contextPath}/resources/js/bootstrap-switch.min.js"></script>
    <script src="${contextPath}/resources/js/devices.js"></script>
</body>
</html>
