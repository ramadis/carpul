import React from "react";
import { useTranslation } from "react-i18next";
import { format } from "date-fns";

const Review = ({ review }) => {
  const { t, i18n } = useTranslation();
  return (
    <li className="review-item-container">
      <img
        width="75"
        height="75"
        src={`https://ui-avatars.com/api/?rounded=true&size=200&background=e36f4a&color=fff&name=${review.owner.first_name} ${review.owner.last_name}`}
        alt=""
      />
      <div className="review-item-content">
        {review.stars === 0 ? (
          <span className="review-meta">{t("review.item.no_stars")}</span>
        ) : (
          <span className={`stars-${review.stars}`} />
        )}
        <span className="review-message">{review.message}</span>
        <span className="review-meta">
          {t("review.item.from")}{" "}
          <span className="bold inline">{review.trip.from_city}</span>{" "}
          {t("review.item.to")}{" "}
          <span className="bold inline">{review.trip.to_city}</span>
        </span>
      </div>
    </li>
  );
};

export default Review;
