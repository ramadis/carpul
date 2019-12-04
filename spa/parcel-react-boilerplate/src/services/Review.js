import { GETwithAuth, POSTwithAuth } from "./Utils";
import { NotificationManager } from "react-notifications";

export const getReviewsByUser = async id => {
  const reviews = await GETwithAuth(`/users/${id}/reviews`).then(res => {
    if (res.isRawResponse) {
      // TODO: Handle specific error messages
      return;
    }
    return res;
  });
  return reviews;
};

export const getReviewById = async id => {
  const review = await GETwithAuth(`/reviews/${id}`).then(res => {
    if (res.isRawResponse) {
      // TODO: Handle specific error messages
      return;
    }
    return res;
  });
  return review;
};

export const reviewTrip = async (id, reviewContent) => {
  const review = await POSTwithAuth(`/trips/${id}/reviews`, reviewContent).then(
    res => {
      if (res.isRawResponse) {
        const errors = {
          400: "The review content seems to have some problems.",
          default: "And we don't know what it is. Sorry :(",
        };
        console.log(res);
        NotificationManager.error(
          errors[res.status] || errors.default,
          "Something went wrong"
        );
        // TODO: Handle specific error messages
        return;
      }
      return res;
    }
  );
  return review;
};

export const addReviewImage = async (id, image) => {
  // prepare data
  const data = new FormData();
  data.append("file", image.file);

  // make request
  const review = await POSTwithAuth(
    `/reviews/${id}/image`,
    {
      isRaw: true,
      content: data,
    },
    { "Content-Type": null }
  ).then(res => {
    if (res.isRawResponse) {
      // TODO: Handle specific error messages
      return;
    }
    return res;
  });
  return review;
};
