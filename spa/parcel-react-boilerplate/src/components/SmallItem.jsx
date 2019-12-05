import React, { Fragment } from "react";
import { useTranslation } from "react-i18next";
import { format } from "date-fns";

import profileHeroCss from "../styles/profile_hero";
import poolListCss from "../styles/pool_list";
import profileCss from "../styles/profile";
import reviewItemCss from "../styles/review_item";

const SmallItem = ({ user, trip, hero_message }) => {
  const { t, i18n } = useTranslation();

  return (
    <Fragment>
      <style jsx>{poolListCss}</style>
      <style jsx>{profileCss}</style>
      <style jsx>{reviewItemCss}</style>
      <style jsx>{profileHeroCss}</style>
      <div className="pool-item flex-center">
        <div className="pool-info small-pool-info">
          <div className="header-container">
            <span className="bold header">
              {trip.from_city} {t("home.index.small_to")} {trip.to_city}
            </span>
          </div>

          <div>
            {t("home.index.leave_on")}
            <span className="bold">{format(trip.etd, "DD/MM/YYYY")}</span>
            {t("search.item.at")}
            <span className="bold">{format(trip.etd, "HH:mm")}</span>
          </div>

          <div className="">
            {t("home.index.just_price")}
            <span className="bold">${trip.cost}</span>
          </div>
          {trip.reserved ? (
            <button className="inline-block CTA login-button main-color">
              {t("search.item.unreserve")}
            </button>
          ) : (
            <button className="inline-block CTA login-button">
              {t("search.item.reserve")}
            </button>
          )}
        </div>
      </div>
    </Fragment>
  );
};

export default SmallItem;
