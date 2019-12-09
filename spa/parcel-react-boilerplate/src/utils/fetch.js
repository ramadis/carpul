export const query = params =>
  "?" +
    Object.keys(params)
      .filter(key => params[key])
      .map(key => key + "=" + params[key])
      .join("&") || "";
