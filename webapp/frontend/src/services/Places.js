const token = "98d02695e58b23";
export const getMap = (places = []) => {
  const [origin, destination] = places.map(
    place =>
      place.position && `${place.position.latitude},${place.position.longitude}`
  );
  return `https://maps.locationiq.com/v2/staticmap?key=${token}&size=500x300&markers=size:small|color:red|${origin}&markers=size:small|color:blue|${destination}`;
};

export const getLocation = async () => {
  const url = (lat, lon) =>
    `https://us1.locationiq.com/v1/reverse.php?key=${token}&lat=${lat}&lon=${lon}&format=json&countrycodes=ar&normalizecity=1`;

  try {
    if ("geolocation" in navigator) {
      const currentPos = await new Promise((res, rej) =>
        navigator.geolocation.getCurrentPosition(res, rej, {
          timeout: 5 * 1000, // milliseconds
          maximumAge: 5 * 60 * 1000, // milliseconds
        })
      );

      const res = await fetch(
        url(currentPos.coords.latitude, currentPos.coords.longitude)
      );

      if (!res.ok) throw "Bad request";

      const place = await res.json();
      return place;
    } else throw "No geolocation services";
  } catch (e) {
    console.error(e);
    return "";
  }
};

export const autocompletePlaces = async search => {
  const query = encodeURIComponent(search);

  try {
    const res = await fetch(
      `https://us1.locationiq.com/v1/search.php?key=${token}&q=${query}&format=json&countrycodes=ar&addressdetails=1&normalizecity=1&type=city`
    );

    return res.ok ? await res.json() : [];
  } catch (e) {
    console.error(e);
    return [];
  }
};

export const getCity = place => {
  if (!place || !place.address) return "";
  return (
    place.address.city || place.address.town || place.address.city_district
  );
};
