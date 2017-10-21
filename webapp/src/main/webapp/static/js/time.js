$(document).ready(function() {
  jQuery.datetimepicker.setLocale('en');
  var EtdConfigs = {
    minDate: 0,
    onChangeDateTime: changeEtd,
  };

  var EtaConfigs = {
    minDate: 0,
    onChangeDateTime: changeEta,
    onShow: function(ct) {
      console.log(EtaConfigs.minDate);
      this.setOptions({minDate: EtaConfigs.minDate, minTime: EtaConfigs.minTime });
    }
  };

  $("#etd").datetimepicker(EtdConfigs);
  var eta = $("#eta").datetimepicker(EtaConfigs);

  function changeEtd(datetime) {
    if(!datetime) return;
    EtaConfigs.minDate = datetime;
    EtaConfigs.minTime = datetime.getHours() + ':' + datetime.getMinutes();
    $("input[name=etd]").val(datetime.getTime());
  }

  function changeEta(datetime) {
    if(!datetime) return;
    $("input[name=eta]").val(datetime.getTime());
  }
});
