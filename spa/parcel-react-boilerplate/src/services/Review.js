import { GETwithAuth, POSTwithAuth } from "./Utils";

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
        // TODO: Handle specific error messages
        return;
      }
      return res;
    }
  );
  return review;
};

export const addReviewImage = async (id, image) => {
  const review = await POSTwithAuth(`/reviews/${id}/image`, image).then(res => {
    if (res.isRawResponse) {
      // TODO: Handle specific error messages
      return;
    }
    return res;
  });
  return review;
};
