var initialLat = -34.6027939;
var initialLng = -58.3678814;
var finishLat = -34.6019644;
var finishLng = -58.3703602;

var geocoder = new google.maps.Geocoder;
var map = new GMaps({
  div: '#map',
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

function updateStart(event) {
  var lat = event.latLng.lat();
  var lng = event.latLng.lng();
  var latlng = { lat: lat, lng: lng };

  geocoder.geocode({'location': latlng}, function(results, status) {
    if (status === 'OK' && results[1]) {
      var result = results[1];
      var city = result.address_components.find(function(comp) { return comp.types.includes("locality")});
      $("input[name=from_city]").val(city.long_name);
    }
  });
}

function updateFinish(event) {
  var lat = event.latLng.lat();
  var lng = event.latLng.lng();
  var latlng = { lat: lat, lng: lng };

  geocoder.geocode({'location': latlng}, function(results, status) {
    if (status === 'OK' && results[1]) {
      var result = results[1];
      var city = result.address_components.find(function(comp) { return comp.types.includes("locality")});
      $("input[name=to_city]").val(city.long_name);
    }
  });
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

getLocation();
updateStart({latLng:{lat: function() {return initialLat }, lng: function() { return initialLng} }});
updateFinish({latLng:{lat: function() {return finishLat }, lng: function() { return finishLng} }});
