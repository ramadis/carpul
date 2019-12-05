import { GETwithAuth } from "./Utils";

export const search = async ({ to, from, when }) => {
  const query = encodeURIComponent({ to, from, when });

  const results = await GETwithAuth(
    `/search?to=${to}&from=${from}&when=${when}`
  ).then(res => {
    if (res.isRawResponse) {
      // TODO: Handle specific error messages
      return;
    }
    return res;
  });
  return results;
};

export const getSuggestions = async origin => {
  const results = await GETwithAuth(
    `/search/suggestions?origin=${origin}`
  ).then(res => {
    if (res.isRawResponse) {
      // TODO: Handle specific error messages
      return;
    }
    return res;
  });
  return results;
};
