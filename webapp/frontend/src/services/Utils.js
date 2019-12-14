import { unlogUser } from "./Auth";
import store from "../state/store";
import { API_URL } from "../api";
import { NotificationManager } from "react-notifications";

const withAuth = async (params, contentType = "application/json") => {
  const state = store.getState();
  params.headers = params.headers || {};
  const options = {
    ...params,
    headers: {
      Accept: "application/json",
      "Content-Type": contentType,
      Authorization: state.token,
      ...params.headers,
    },
  };
  if (params.headers["Content-Type"] === null) {
    delete options.headers["Content-Type"];
  }
  return options;
};

const methods = ["GET", "POST", "PATCH", "PUT", "DELETE"];
module.exports = methods.reduce((pv, cv) => {
  const fn = async (uri, body, headers) => {
    const res = await fetch(
      `${API_URL}${uri}`,
      await withAuth({
        method: cv,
        body: body && (body.isRaw ? body.content : JSON.stringify(body)),
        headers,
      })
    );
    const isUnauthorized = res.status == 401;

    if (isUnauthorized) {
      console.log("IS UNAUTHORIZED");
      await unlogUser();
      NotificationManager.error(
        "Some actions require you to be a user of carpul",
        "You have to login"
      );
      return "";
    }

    if (res.ok && res.headers.get("Content-Type") === "application/json") {
      return await res.json();
    } else if (res.status === 204 || res.status === 201) {
      return "";
    }

    // TODO: { ...res } doesn't work: it returns {}
    return { ok: res.ok, status: res.status, isRawResponse: true };
  };

  return { ...pv, [`${cv}withAuth`]: fn };
}, {});
