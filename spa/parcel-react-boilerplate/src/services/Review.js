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
  const response = await POSTwithAuth(`/trips/${id}/reviews`, reviewContent);

  if (response.isRawResponse) {
    const errors = {
      400: {
        title: "The review content has some problems",
        subtitle: "Try fixing the errors and submitting it again.",
      },
      409: {
        title: "You've already reviewed this trip",
        subtitle: "We'll take you back to your profile.",
      },
      default: {
        title: "Something went wrong",
        subtitle: "And we don't know what it is, sorry :(.",
      },
    };

    throw {
      message: errors[response.status] || errors.default,
      code: response.status,
    };
  }

  return response;
};

export const addReviewImage = async (id, image) => {
  // prepare data
  const data = new FormData();
  data.append("file", image.file);

  // make request
  const review = await PUTwithAuth(
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
