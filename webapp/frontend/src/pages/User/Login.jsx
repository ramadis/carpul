import React, { useState } from "react";
import { Link, Redirect } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { connect } from "react-redux";
import MDSpinner from "react-md-spinner";
import { NotificationManager } from "react-notifications";
import styled from "styled-components";

import { loginUser } from "../../services/Auth";

import { routes } from "../../App";

import { getPreviousPath } from "../../utils/routes";

const LoginButton = styled.button`
  width: 70px;
  height: 38px;
  margin-left: 10px;
  font-size: 16px;
  background: #6cd298;
  text-decoration: none;
  cursor: pointer;
  border: 0;
  border-radius: 3px;
  transition: 0.2s ease-out background;
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0;
`;

const Login = ({ dispatch, user }) => {
  const [t, i18n] = useTranslation();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);

  const login = async () => {
    setLoading(true);
    try {
      await loginUser(username, password);
    } catch (e) {
      NotificationManager.error(
        "The email or the password are incorrect",
        "Try again, you may have a typo."
      );
      setLoading(false);
    }
  };

  const isLogged = !!user;

  return isLogged ? (
    <Redirect to={routes.profile(user.id)} />
  ) : (
    <div className="flex-center full-height" style={{ background: "#e36f4a" }}>
      <form className="user-form">
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
          <Link to={routes.register} className="create-account">
            {t("user.login.create")}
          </Link>
          <LoginButton type="submit" onClick={login} disabled={loading}>
            {loading ? <MDSpinner size={16} /> : t("user.login.submit")}
          </LoginButton>
        </div>
      </form>
    </div>
  );
};

export default connect(state => ({ user: state.user }))(Login);
