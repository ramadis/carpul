import store from "../state/store";
import { API_URL } from "../api";

const urlEncode = ({ username, password }) => {
  const params = new URLSearchParams();
  params.append("username", username);
  params.append("password", password);
  return params;
};

export const isLoggedIn = () => !!localStorage.getItem("token");

export const loginUser = async (username, password) => {
  const encodedData = urlEncode({ username, password });

  try {
    const res = await fetch(`${API_URL}/login`, {
      method: "POST",
      body: encodedData,
    });

    if (res.ok) {
      const token = res.headers.get("Authorization");
      localStorage.setItem("token", token);

      store.dispatch({ type: "LOGIN", token });
    } else {
      throw new Error(res.error);
    }
  } catch (error) {
    throw error;
  }
};

export const unlogUser = async () => {
  localStorage.removeItem("token");
  localStorage.removeItem("user");
  const currentURL = window.location.pathname;
  const validURLs = ["/login", "/register"];

  if (!validURLs.includes(currentURL)) {
    setTimeout(() => (window.location.href = "/login"));
  }

  store.dispatch("LOGOUT");
};
