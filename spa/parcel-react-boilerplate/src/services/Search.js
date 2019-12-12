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

export const getSuggestions = async ({ from, depLat, depLon }) => {
  const results = await GETwithAuth(
    `/search${query({
      from,
      when: new Date().getTime(),
      depLat,
      depLon,
      per_page: 20,
    })}`
  ).then(res => {
    if (res.isRawResponse) return [];
    return res;
  });
  return results;
};
