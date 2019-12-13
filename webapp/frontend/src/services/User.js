import { GETwithAuth, POSTwithAuth, PUTwithAuth } from "./Utils";
import store from "../state/store";

export const userid = () => {
  const state = store.getState();
  return state.user && state.user.id;
};

export const getProfileById = async id => {
  const user = await GETwithAuth(`/users/${id}`).then(res => {
    if (res.isRawResponse) {
      const errors = {
        404: {
          title: "We can't find the user",
          subtitle:
            "You sure you are trying to access an existing user profile?",
        },
        default: {
          title: "Something went wrong",
          subtitle: "And we don't know what it is, sorry :(.",
        },
      };

      throw {
        origin: "profile-id",
        message: errors[res.status] || errors.default,
        code: res.status,
      };
    }
    return res;
  });
  return user;
};

export const getProfile = async () => {
  const user = await GETwithAuth("/users").then(res => {
    if (res.isRawResponse) return;
    return res;
  });

  localStorage.setItem("user", JSON.stringify(user));
  return user;
};

export const updateProfileById = async (id, profile) => {
  const user = await PUTwithAuth(`/users/${id}/profile`, profile).then(res => {
    if (res.isRawResponse) return;
    return res;
  });
  return user;
};

export const updateCoverImageById = async (id, image) => {
  // prepare data
  const data = new FormData();
  data.append("file", image.file);

  const user = await PUTwithAuth(
    `/users/${id}/cover`,
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
          title: "You can't add a cover image to this user",
          subtitle: "Only the owner can, sorry.",
        },
        404: {
          title: "We can't find the user",
          subtitle:
            "You sure you are trying to add an image to an existing user?",
        },
        default: {
          title: "Something went wrong",
          subtitle: "And we don't know what it is, sorry :(.",
        },
      };

      throw {
        origin: "cover-image",
        message: errors[res.status] || errors.default,
        code: res.status,
      };
    }
    return res;
  });
  return user;
};

export const updateProfileImageById = async (id, image) => {
  // prepare data
  const data = new FormData();
  data.append("file", image.file);

  const user = await PUTwithAuth(
    `/users/${id}/image`,
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
          title: "You can't add an image to this user",
          subtitle: "Only the owner can, sorry.",
        },
        404: {
          title: "We can't find the user",
          subtitle:
            "You sure you are trying to add an image to an existing user?",
        },
        default: {
          title: "Something went wrong",
          subtitle: "And we don't know what it is, sorry :(.",
        },
      };

      throw {
        origin: "profile-image",
        message: errors[res.status] || errors.default,
        code: res.status,
      };
    }
    return res;
  });
  return user;
};

export const signupUser = async profile => {
  const user = await POSTwithAuth("/users", profile).then(res => {
    if (res.isRawResponse) {
      const errors = {
        400: {
          title: "The signup form has some problems",
          subtitle: "Try fixing the issues and submitting it again.",
        },
        409: {
          title: "An user with that email already exists",
          subtitle: "Try using a different email to access Carpul",
        },
        default: {
          title: "Something went wrong",
          subtitle: "And we don't know what it is, sorry :(.",
        },
      };

      throw {
        origin: "signup",
        message: errors[res.status] || errors.default,
        code: res.status,
      };
    }
    return res;
  });
  return user;
};

export const updateProfile = async profile => {
  const updatedUser = await POSTwithAuth(
    `/users/${userid()}/profile`,
    profile
  ).then(res => {
    if (res.isRawResponse) return;
    return res;
  });

  return updatedUser;
};
