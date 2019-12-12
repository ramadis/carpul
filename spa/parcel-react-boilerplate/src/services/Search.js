import { GETwithAuth } from "./Utils";
import { query } from "../utils/fetch";

export const search = async ({
  to,
  from,
  when,
  page,
  per_page,
  arrLat,
  arrLon,
  depLat,
  depLon,
}) => {
  const results = await GETwithAuth(
    `/search${query({
      to,
      from,
      when,
      page,
      per_page,
      arrLat,
      arrLon,
      depLat,
      depLon,
    })}`
  ).then(res => {
    if (res.isRawResponse) return [];
    return res;
  });

  return results;
};

export const getSuggestions = async origin => {
  const results = await GETwithAuth(
    `/search/suggestions${query({
      origin,
      per_page: 20,
      exclude_driver: true,
    })}`
  ).then(res => {
    if (res.isRawResponse) return [];
    return res;
  });
  return results;
};
