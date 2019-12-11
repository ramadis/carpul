import { GETwithAuth, POSTwithAuth } from "./Utils";
import { NotificationManager } from "react-notifications";

export const getReviewsByUser = async id => {
  const reviews = await GETwithAuth(`/users/${id}/reviews`).then(response => {
    if (response.isRawResponse) {
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
        origin: "get-review",
        message: errors[response.status] || errors.default,
        code: response.status,
      };
    }
    return response;
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
      403: {
        title: "You can't review this trip",
        subtitle: "Only passengers can, sorry.",
      },
      404: {
        title: "We can't find this trip",
        subtitle: "You sure you are trying to review the correct trip?",
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
      origin: "review",
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
      const errors = {
        400: {
          title: "The image content has some problems",
          subtitle: "Try uploading a valid image and submitting it again.",
        },
        403: {
          title: "You can't add an image to this review",
          subtitle: "Only the owner can, sorry.",
        },
        404: {
          title: "We can't find the review",
          subtitle:
            "You sure you are trying to add an image to an existing review?",
        },
        409: {
          title: "You've already uploaded an image for this trip",
          subtitle: "Try reviewing some other trip!",
        },
        default: {
          title: "Something went wrong",
          subtitle: "And we don't know what it is, sorry :(.",
        },
      };

      throw {
        origin: "review-image",
        message: errors[res.status] || errors.default,
        code: res.status,
      };
      return;
    }
    return res;
  });
  return review;
};
