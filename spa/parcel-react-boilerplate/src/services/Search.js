import { GETwithAuth } from "./Utils";
import { query } from "../utils/fetch";

export const search = async ({ to, from, when, page, per_page }) => {
  const results = await GETwithAuth(
    `/search${query({ to, from, when, page, per_page })}`
  ).then(res => {
    if (res.isRawResponse) {
      // TODO: Handle specific error messages
      return [];
    }
    return res;
  });
  return results;
};

export const getSuggestions = async origin => {
  const query = origin ? `?origin=${origin}` : "";
  const results = await GETwithAuth(
    `/search/suggestions${query}&per_page=20`
  ).then(res => {
    if (res.isRawResponse) {
      // TODO: Handle specific error messages
      return;
    }
    return res;
  });
  return results;
};
