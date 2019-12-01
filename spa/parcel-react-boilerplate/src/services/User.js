import { GETwithAuth, POSTwithAuth } from "./Utils";
import store from "../state/store";

export const userid = () => {
  const state = store.getState();
  return state.user && state.user.id;
};

export const getProfile = async () => {
  const user = await GETwithAuth("/users").then(res => {
    if (res.isRawResponse) {
      // TODO: Handle specific error messages
      return;
    }
    return res;
  });
  return user;
};

export const signupUser = async profile => {
  const user = await POSTwithAuth("/users", profile).then(res => {
    if (res.isRawResponse) {
      // TODO: Handle specific error messages
      return;
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
    if (res.isRawResponse) {
      // TODO: Handle specific error messages
      return;
    }
    return res;
  });

  return updatedUser;
};
