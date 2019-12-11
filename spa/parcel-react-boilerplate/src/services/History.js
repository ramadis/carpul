import { GETwithAuth } from "./Utils";

export const getHistoryByUser = async id => {
  const history = await GETwithAuth(`/users/${id}/history`).then(res => {
    if (res.isRawResponse) {
      const errors = {
        404: {
          title: "We can't find the user",
          subtitle: "You sure you are trying to access the correct one?",
        },
        default: {
          title: "Something went wrong",
          subtitle: "And we don't know what it is, sorry :(.",
        },
      };

      throw {
        message: errors[res.status] || errors.default,
        code: res.status,
      };
    }
    return res;
  });
  return history;
};
