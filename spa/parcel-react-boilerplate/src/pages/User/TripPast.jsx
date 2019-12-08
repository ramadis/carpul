import React, { useState } from "react";
import profileCss from "../../styles/profile";
import { useTranslation } from "react-i18next";

function TripPast({ reservation }) {
  const { t, i18n } = useTranslation();
  return (
    <React.Fragment>
      <style jsx>{profileCss}</style>
      <li className="destiny-item past-item" data-id="${reservation.id}">
        <div className="inline-block no-margin">
          <div className="destiny-cost">{t("user.trip_past.message")}</div>
          <span className="destiny-name">{reservation.to_city}</span>
          <a
            href={`/review/${reservation.id}`}
            className="login-button review-button"
            name="button"
          >
            {t("user.trip_past.review")}
          </a>
        </div>
      </li>
    </React.Fragment>
  );
}

export default TripPast;
