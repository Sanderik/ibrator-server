$( document ).ready(function() {
    $("[name='active']").bootstrapSwitch();

    var form = document.getElementById("changeDeviceState");

    $('input[name="active"]').on('switchChange.bootstrapSwitch', function(event, state) {
        $('input[name="active"]').val(state);
        form.submit();
        // DOM element
        console.log(state); // true | false
    });
});
