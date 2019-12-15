export const routes = {
  search: ({ from, to, date = new Date() }) => {
    console.log(from);
    const depLat = (from.position && from.position.latitude) || "";
    const depLon = (from.position && from.position.longitude) || "";
    const arrLat = (to.position && to.position.latitude) || "";
    const arrLon = (to.position && to.position.longitude) || "";
    return `/search?from=${from.city}&to=${
      to.city
    }&depLat=${depLat}&depLon=${depLon}&arrLat=${arrLat}&arrLon=${arrLon}&when=${date &&
      date.getTime()}`;
  },
};
