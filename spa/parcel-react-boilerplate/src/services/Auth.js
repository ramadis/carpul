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
  const token = await fetch(`${API_URL}/login`, {
    method: "POST",
    body: encodedData
  }).then(res => {
    if (res.ok) {
      const token = res.headers.get("Authorization");
      localStorage.setItem("token", token);
      return token;
    }

    // TODO: handle errors
  });

  store.dispatch({ type: "LOGIN", token });
};

export const unlogUser = async () => {
  localStorage.removeItem("token");
  setTimeout(() => (window.location.href = "/login"));
  store.dispatch("LOGOUT");
};
