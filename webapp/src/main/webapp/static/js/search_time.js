$(document).ready(function() {
  jQuery.datetimepicker.setLocale('en');
  var DateConfigs = {
    minDate: 0,
    onChangeDateTime: changeEtd,
    timepicker:false,
    format: 'd/m/y'
  };

  $("#when").datetimepicker(DateConfigs);

  function changeEtd(datetime) {
    if(!datetime) return;
    $("input[name=when]").val(datetime.getTime());
  }
});
