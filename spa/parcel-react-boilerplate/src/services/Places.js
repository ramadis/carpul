const token = "98d02695e58b23";
export const getMap = (places = []) => {
  const [origin, destination] = places.map(
    place =>
      place.position && `${place.position.latitude},${place.position.longitude}`
  );
  return `https://maps.locationiq.com/v2/staticmap?key=${token}&size=500x300&markers=size:small|color:red|${origin}&markers=size:small|color:blue|${destination}`;
};

export const autocompletePlaces = async search => {
  const query = encodeURIComponent(search);

  try {
    const res = await fetch(
      `https://us1.locationiq.com/v1/search.php?key=${token}&q=${query}&format=json&countrycodes=ar&addressdetails=1&normalizecity=1`
    );

    return res.ok ? await res.json() : [];
  } catch (e) {
    console.error(e);
    return [];
  }
};

export const formatCity = place => {
  return (
    place.address.city || place.address.town || place.address.city_district
  );
};
