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

  var when = $("input#when");
  var to = $("input[name=to]");
  var from = $("input[name=from]");

  function enableButton() {
    if (from.val() && to.val() && when.val()) {
      return $("button[type=submit]").removeAttr('disabled');
    }

    $("button[type=submit]").prop('disabled', true);;
  }

  when.change(enableButton);
  to.change(enableButton);
  from.change(enableButton);
});
