import { unlogUser } from "./Auth";
import store from "../state/store";
import { API_URL } from "../api";

const withAuth = async (params, contentType = "application/json") => {
  const state = store.getState();
  return {
    ...params,
    headers: {
      Accept: "application/json",
      "Content-Type": contentType,
      Authorization: state.token
    }
  };
};

const methods = ["GET", "POST", "PATCH", "PUT", "DELETE"];
module.exports = methods.reduce((pv, cv) => {
  const fn = async (uri, body) => {
    const res = await fetch(
      `${API_URL}${uri}`,
      await withAuth({ method: cv, body: body && JSON.stringify(body) })
    );
    const isUnauthorized = res.status == 403 || res.status == 401;

    if (isUnauthorized) {
      // alertError({ title: 'Error de sesión', message: 'Por seguridad, tenemos que pedirte tus datos nuevamente' });
      await unlogUser();
      res.ok = false;
    }

    if (res.ok && res.headers.get("Content-Type") === "application/json") {
      return await res.json();
    }

    return { ...res, isRawResponse: true };
  };

  return { ...pv, [`${cv}withAuth`]: fn };
}, {});
