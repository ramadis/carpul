import React, { useState } from "react";
import { Link, Redirect } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { connect } from "react-redux";
import { signupUser } from "../../services/User";
import { loginUser } from "../../services/Auth";

const Register = ({ dispatch, user }) => {
  // TODO: Add form validation
  const [first_name, setFirstName] = useState("");
  const [last_name, setLastName] = useState("");
  const [phone_number, setPhone] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const { t, i18n } = useTranslation();

  const register = async () => {
    await signupUser({
      first_name,
      last_name,
      phone_number,
      username,
      password
    });
    await loginUser(username, password);
  };

  const setFormField = setter => event => setter(event.target.value);

  const isLogged = !!user;
  if (isLogged) return <Redirect to={`/user/profile/${user.id}`} />;
  return (
    <div className="flex-center full-height">
      <div className="user-form">
        <div className="top-border" />
        <div className="text-container">
          <span>carpul</span>
          <span className="catchphrase">{t("user.register.title")}</span>
          <span className="catchphrase-description">
            {t("user.register.subtitle")}
          </span>
        </div>

        <div className="field-container">
          <label path="first_name" className="field-label" htmlFor="first_name">
            {t("user.register.first_name")}
          </label>
          <input
            required
            className="field"
            path="first_name"
            type="text"
            name="first_name"
            value={first_name}
            onChange={setFormField(setFirstName)}
          />

          <label path="last_name" className="field-label" htmlFor="last_name">
            {t("user.register.last_name")}
          </label>
          <input
            required
            className="field"
            path="last_name"
            type="text"
            name="last_name"
            value={last_name}
            onChange={setFormField(setLastName)}
          />

          <label
            path="phone_number"
            className="field-label"
            htmlFor="phone_number"
          >
            {t("user.register.phone_number")}
          </label>
          <input
            required
            className="field"
            path="phone_number"
            type="text"
            name="phone_number"
            value={phone_number}
            onChange={setFormField(setPhone)}
          />

          <label path="username" className="field-label" htmlFor="username">
            {t("user.register.username")}
          </label>
          <input
            required
            className="field"
            name="username"
            path="username"
            type="email"
            value={username}
            onChange={setFormField(setUsername)}
          />

          <label path="password" className="field-label" htmlFor="password">
            {t("user.register.password")}
          </label>
          <input
            required
            className="field"
            pattern=".{6,100}"
            path="password"
            type="password"
            name="password"
            value={password}
            onChange={setFormField(setPassword)}
          />
        </div>

        <div className="actions">
          <Link to="/user/login" className="create-account">
            {t("user.register.login")}
          </Link>
          <button type="submit" className="login-button" onClick={register}>
            {t("user.register.submit")}
          </button>
        </div>
      </div>
    </div>
  );
};

export default connect(state => ({ user: state.user }))(Register);
