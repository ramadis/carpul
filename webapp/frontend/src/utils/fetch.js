import { NotificationManager } from "react-notifications";

export const query = params =>
  "?" +
    Object.keys(params)
      .filter(key => params[key])
      .map(key => key + "=" + params[key])
      .join("&") || "";

export const requestCatch = error => {
  console.error(error);
  NotificationManager.error(error.message.subtitle, error.message.title);
};
