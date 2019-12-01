import { GETwithAuth } from "./Utils";

export const search = async params => {
  const results = await GETwithAuth(`/search`).then(res => {
    if (res.isRawResponse) {
      // TODO: Handle specific error messages
      return;
    }
    return res;
  });
  return results;
};
