import { GETwithAuth } from "./Utils";

export const search = async ({ to, from, when }) => {
  const query = encodeURIComponent({ to, from, when });

  const results = await GETwithAuth(
    `/search?to=${to}&from=${from}&when=${when}`
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
