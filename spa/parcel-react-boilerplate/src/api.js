import axios from "axios";

const auth = () => localStorage.getItem("token");

export const API_URL = "http://feb3206c.ngrok.io/api";

const instance = axios.create({
  baseURL: API_URL,
  transformRequest: [
    function(data, headers) {
      const token = auth();

      if (token) {
        headers["Authorization"] = token;
      } else {
        delete headers.Authorization;
      }

      return data;
    }
  ]
});

export const getUser = userId =>
  instance.get(`/users/${userId}`).then(res => res.data);

export default instance;
