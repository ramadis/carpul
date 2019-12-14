import React, { useState } from "react";
import profileCss from "../../styles/profile";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import { routes } from "../../App.jsx";

function TripPast({ reservation }) {
  const { t, i18n } = useTranslation();
  return (
    <React.Fragment>
      <style jsx>{profileCss}</style>
      <li className="destiny-item past-item" style={{ width: "auto" }}>
        <div className="inline-block no-margin">
          <div className="destiny-cost">{t("user.trip_past.message")}</div>
          <span className="destiny-name">{reservation.to_city}</span>
          <Link
            to={routes.review(reservation.id)}
            className="login-button review-button"
            name="button"
          >
            {t("user.trip_past.review")}
          </Link>
        </div>
      </li>
    </React.Fragment>
  );
}

export default TripPast;
