export const autocompletePlaces = async search => {
  const token = "98d02695e58b23";
  const query = encodeURIComponent(search);

  try {
    const res = await fetch(
      `https://us1.locationiq.com/v1/search.php?key=${token}&q=${query}&format=json&countrycodes=ar&addressdetails=1`
    );

    return res.ok ? await res.json() : [];
  } catch (e) {
    console.error(e);
    return [];
  }
};

export const formatCity = place => {
  const data = [
    place.address.city || place.address.town,
    place.address.state,
    place.address.country,
  ];
  return data.filter(Boolean).join(", ");
};
