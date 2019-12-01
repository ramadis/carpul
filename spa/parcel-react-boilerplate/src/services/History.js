import { GETwithAuth } from "./Utils";

export const getHistoryByUser = async id => {
  const history = await GETwithAuth(`/users/${id}/history`).then(res => {
    if (res.isRawResponse) {
      // TODO: Handle specific error messages
      return;
    }
    return res;
  });
  return history;
};
