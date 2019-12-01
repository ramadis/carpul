import React, { useState } from "react";
import { Link, Redirect } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { connect } from "react-redux";
import { loginUser } from "../../services/Auth";
import { getProfile } from "../../services/User";
import MDSpinner from "react-md-spinner";

const Login = ({ dispatch, user }) => {
  const [t, i18n] = useTranslation();
  const [username, setUsername] = useState("rolivera+carpul@itba.edu.ar");
  const [password, setPassword] = useState("carpulcarpul");
  const [loading, setLoading] = useState(false);

  const login = async () => {
    setLoading(true);
    try {
      await loginUser(username, password);
    } catch (e) {
      setLoading(false);
    }
  };

  const isLogged = !!user;

  return isLogged ? (
    <Redirect to={`/user/profile/${user.id}`} />
  ) : (
    <div className="flex-center full-height">
      <div className="user-form">
        <div className="top-border" />
        <div className="text-container">
          <span>carpul</span>
          <span className="catchphrase">{t("user.login.title")}</span>
          <span className="catchphrase-description">
            {t("user.login.subtitle1")}
          </span>
          <span className="catchphrase-description">
            {t("user.login.subtitle2")}
          </span>
        </div>
        <div className="field-container">
          <label path="username" className="field-label" htmlFor="username">
            {t("user.login.username")}
          </label>
          <input
            required
            className="field"
            path="username"
            type="text"
            name="username"
            value={username}
            onChange={e => setUsername(e.target.value)}
          />

          <label path="password" className="field-label" htmlFor="password">
            {t("user.login.password")}
          </label>
          <input
            required
            className="field"
            path="password"
            type="password"
            name="password"
            value={password}
            disabled={loading}
            onChange={e => setPassword(e.target.value)}
          />
        </div>
        <div className="actions">
          <Link to="/user/register" className="create-account">
            {t("user.login.create")}
          </Link>
          <button type="submit" className="login-button" onClick={login}>
            {loading ? <MDSpinner size={24} /> : t("user.login.submit")}
          </button>
        </div>
      </div>
    </div>
  );
};

export default connect(state => ({ user: state.user }))(Login);
