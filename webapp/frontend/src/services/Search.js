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
  const results = [];
  const MAX_RESULTS = 30;
  for (let page = 0; page < 3; page++) {
    if (results.length >= MAX_RESULTS) break;
    const partialResults = await GETwithAuth(
      `/search${query({
        from,
        when: new Date().getTime(),
        depLat,
        depLon,
        page,
        per_page: 20,
      })}`
    ).then(res => {
      if (res.isRawResponse) return [];
      return res;
    });
    results.push(...partialResults);
  }
  return results.slice(0, MAX_RESULTS);
};
