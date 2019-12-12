import { GETwithAuth } from "./Utils";
import { query } from "../utils/fetch";

export const search = async ({ to, from, when, page, per_page }) => {
  const makeRequest = async (req, page = page) =>
    await GETwithAuth(
      `${req}${query({ to, from, when, page, per_page })}`
    ).then(res => {
      if (res.isRawResponse) return [];
      return res;
    });

  const maxPage = {
    "/search/closest": 0,
    "/search/origin": 0,
    "/search/rest": 0,
  };

  const reqs = ["/search/closest", "/search/origin", "/search/rest"];
  for (let req of reqs) {
    const reqidx = reqs.findIndex(r => r === req);
    const currPage =
      page -
      reqs.reduce((sum, req, idx) => {
        if (idx < reqidx) return sum + maxPage[req];
        return sum;
      }, 0);

    const results = await makeRequest(req, currPage);

    if (results.length) return results;
    maxPage[req] = currPage;
  }

  return [];
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
