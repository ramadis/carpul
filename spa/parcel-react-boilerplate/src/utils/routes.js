import { createBrowserHistory } from "history";
import { takeRight, get } from "lodash";

const ROUTER_HISTORY_MAX_LIMIT = 10;

class HistoricRoute {
  constructor(path, user) {
    this.path = path;
    this.user = user;
    this.date = new Date();
  }
}

const getUserFromStorage = () => {
  try {
    const user = JSON.parse(localStorage.getItem("user"));
    const userId = get(user, "id") || null;
    return userId;
  } catch (e) {
    return null;
  }
};

const initializeRouterHistory = () => {
  const emptyRouterHistory = [];
  try {
    return (
      JSON.parse(localStorage.getItem("routerHistory")) || emptyRouterHistory
    );
  } catch (e) {
    return emptyRouterHistory;
  }
};

let routerHistory = initializeRouterHistory();

const defaultPredicate = route => {
  const pathsToOmit = ["/", "/login", "/logout", "/implicit/callback"];
  return !pathsToOmit.includes(route.path);
};

export const getPreviousPath = (
  defaultRoute = "/",
  predicate = defaultPredicate
) => {
  const filteredRoutes = predicate
    ? routerHistory.filter(predicate)
    : routerHistory;
  if (filteredRoutes.length < 1) return defaultRoute;
  return filteredRoutes[filteredRoutes.length - 1].path;
};

export const navigateToPath = path => {
  window.location.replace(`${window.location.origin}${path}`);
};

export const persistRoute = location => {
  routerHistory.push(
    new HistoricRoute(location.pathname, getUserFromStorage())
  );
  routerHistory = takeRight(routerHistory, ROUTER_HISTORY_MAX_LIMIT);
  localStorage.setItem("routerHistory", JSON.stringify(routerHistory));
};

export const initializeHistory = persistRoute;

const browserHistory = createBrowserHistory();
browserHistory.listen(persistRoute);
export default browserHistory;
