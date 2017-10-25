var initialLat = -34.6027939;
var initialLng = -58.3678814;
var finishLat = -34.6019644;
var finishLng = -58.3703602;

function RemoveAccents(str) {
  var accents    = 'ÀÁÂÃÄÅàáâãäåßÒÓÔÕÕÖØòóôõöøÈÉÊËèéêëðÇçÐÌÍÎÏìíîïÙÚÛÜùúûüÑñŠšŸÿýŽž';
  var accentsOut = "AAAAAAaaaaaaBOOOOOOOooooooEEEEeeeeeCcDIIIIiiiiUUUUuuuuNnSsYyyZz";
  str = str.split('');
  var strLen = str.length;
  var i, x;
  for (i = 0; i < strLen; i++) {
    if ((x = accents.indexOf(str[i])) != -1) {
      str[i] = accentsOut[x];
    }
  }
  return str.join('');
}

var geocoder = new google.maps.Geocoder;
var map = new GMaps({
  div: '#map',
  language: 'es',
  lat: initialLat,
  lng: initialLng,
});

function debounce(func, wait, immediate) {
	var timeout;
	return function() {
		var context = this, args = arguments;
		var later = function() {
			timeout = null;
			if (!immediate) func.apply(context, args);
		};
		var callNow = immediate && !timeout;
		clearTimeout(timeout);
		timeout = setTimeout(later, wait);
		if (callNow) func.apply(context, args);
	};
};

function showPosition(gps) {
  lat = gps.coords.latitude;
  lng = gps.coords.longitude;
  var prompt = window.confirm("Would you like to set the departure location to yours?", "Change departure location");
  if (!prompt) return;

  var marker = map.markers[0];
  marker.setPosition(new google.maps.LatLng(lat, lng));
  map.panTo(marker.getPosition())
  updateStart({latLng:{lat: function() {return lat }, lng: function() { return lng } }});

}

function getLocation() {
  if (navigator.geolocation) navigator.geolocation.getCurrentPosition(showPosition);
}

function updateStartField(value) {
  geocoder.geocode( { 'address': value }, function(results, status) {
    if (status == 'OK') {
      console.log(results)
    }
  });
}

function update(field, latlng) {

  var input = {
    'from' : 'from_city',
    'to' : 'to_city'
  };

  var position = {
    'from': 'etd',
    'to': 'eta'
  };

  $("input[name=" + position[field] + "_latitude]").val(latlng.lat);
  $("input[name=" + position[field] + "_longitude]").val(latlng.lng);

  return function(results, status) {
    if (status === 'OK' && results[1]) {
      var result = results[1];
      var city = result.address_components.find(function(comp) { return comp.types.includes("locality") || comp.types.includes("administrative_area_level_2") || comp.types.includes("administrative_area_level_1")});
      $("input[name=" + input[field] + "]").val(RemoveAccents(city.long_name));
    }
  }
}

function updateStart(event) {
  var lat = event.latLng.lat();
  var lng = event.latLng.lng();
  var latlng = { lat: lat, lng: lng };

  geocoder.geocode({'location': latlng}, update('from', latlng));
}

function updateFinish(event) {
  var lat = event.latLng.lat();
  var lng = event.latLng.lng();
  var latlng = { lat: lat, lng: lng };

  geocoder.geocode({'location': latlng}, update('to', latlng));
}


map.addMarker({
  lat: initialLat,
  lng: initialLng,
  draggable: true,
  dragend: updateStart,
  title: 'Departure marker',
  infoWindow: {
    content: "My trip will depart from here."
  }
});

map.addMarker({
  lat: finishLat,
  lng: finishLng,
  draggable: true,
  dragend: updateFinish,
  title: 'Arrival marker',
  infoWindow: {
    content: "My trip will arrive here."
  }
});

$("input[etd_latitude]").val(initialLat);
$("input[etd_longitude]").val(initialLng);
$("input[eta_latitude]").val(finishLat);
$("input[eta_longitude]").val(finishLng);

getLocation();
updateStart({latLng:{lat: function() {return initialLat }, lng: function() { return initialLng} }});
updateFinish({latLng:{lat: function() {return finishLat }, lng: function() { return finishLng} }});

function calculateTime(datetime) {
  var departure_lat = $("input[etd_latitude]").val();
  var departure_lon = $("input[etd_longitude]").val();
  var arrival_lat = $("input[eta_latitude]").val();
  var arrival_lon = $("input[eta_longitude]").val();

  var departure = departure_lat + ", " + departure_lon;
  var arrival = arrival_lat + ", " + arrival_lon;

  var request = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" + departure + "&destinations=" + arrival + "&key=AIzaSyBNIjxxJhHZAxRdT52yWRQrcNBLzd9YZAk";
  fetch(request).then(function(response) {
    if (!response.ok) console.error(response);
    response.json().then(function(obj) {
      obj
      debugger;
    });
  });
}
