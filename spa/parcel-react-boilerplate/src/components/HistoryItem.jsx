import React from "react";
import { useTranslation } from "react-i18next";
import { format } from "date-fns";
import { Link } from "react-router-dom";

import profileCss from "../styles/profile.js";
import reviewItemCss from "../styles/review_item";

const HistoryItem = ({ history }) => {
  const { t, i18n } = useTranslation();
  const fmtetd = format(history.trip.etd, "DD/MM/YYYY");
  const fmtdate = format(history.created, "DD/MM/YYYY HH:mm");
  const defaultProfile = `https://ui-avatars.com/api/?rounded=true&size=200&background=e36f4a&color=fff&name=${
    history.user.first_name
  } ${history.user.last_name}`;
  return (
    <React.Fragment>
      <style jsx>{profileCss}</style>
      <style jsx>{reviewItemCss}</style>
      <li className="review-item-container">
        <Link to={`/user/${history.user.id}`}>
          <img
            width="50"
            height="50"
            className="profile-image"
            src={history.user.image || defaultProfile}
            alt=""
          />
        </Link>
        <div className="review-item-content history-content">
          {history.type === "RESERVE" && (
            <div className="review-message">
              {t("history.item.message", {
                "0": history.user.first_name,
                "1": t("history.item.reserved"),
                "2": history.trip.to_city,
                "3": fmtetd,
                "4": t("history.item.all_set"),
              })}
            </div>
          )}
          {history.type === "UNRESERVE" && (
            <div className="review-message">
              {t("history.item.message", {
                "0": history.user.first_name,
                "1": t("history.item.unreserved"),
                "2": history.trip.to_city,
                "3": fmtetd,
                "4": t("history.item.bummer"),
              })}
            </div>
          )}
          {history.type === "DELETE" && (
            <div className="review-message">
              {t("history.item.deleted_message", {
                "0": history.trip.to_city,
                "1": fmtetd,
                "2": t("history.item.just_deleted"),
              })}
            </div>
          )}
          {history.type === "KICKED" && (
            <div className="review-message">
              {t("history.item.kicked_message", {
                "0": history.trip.to_city,
                "1": fmtetd,
                "2": t("history.item.just_deleted"),
              })}
            </div>
          )}
          <span className="review-meta">
            {t("history.item.happened", { "0": fmtetd })}
          </span>
        </div>
      </li>
    </React.Fragment>
  );
};

export default HistoryItem;
