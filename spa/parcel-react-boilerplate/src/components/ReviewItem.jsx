import React, { useState } from "react";
import { useTranslation } from "react-i18next";
import { format } from "date-fns";
import Rating from "react-rating";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar } from "@fortawesome/free-solid-svg-icons";
import styled from "styled-components";

import profileCss from "../styles/profile";
import reviewItemCss from "../styles/review_item";

const StarsContainer = styled.div`
  margin-bottom: 5px;
`;

const Message = ({ review }) => {
  const maxDisplayLength = 50;
  const [full, setFull] = useState(false);
  const readMore = () => {
    setFull(true);
  };

  if (review.message.length <= maxDisplayLength) {
    return (
      <React.Fragment>
        <style jsx>{reviewItemCss}</style>
        <span className="review-message">{review.message}</span>
      </React.Fragment>
    );
  }

  if (full) {
    return (
      <React.Fragment>
        <style jsx>{reviewItemCss}</style>
        <span className="review-message">{review.message}</span>
      </React.Fragment>
    );
  }

  return (
    <React.Fragment>
      <style jsx>{reviewItemCss}</style>
      <span className="review-message">
        {review.message.substr(0, maxDisplayLength)}...
      </span>
      <span className="review-message read-more-button" onClick={readMore}>
        Read more
      </span>
    </React.Fragment>
  );
};

const Review = ({ review }) => {
  const { t, i18n } = useTranslation();
  const defaultProfile = `https://ui-avatars.com/api/?rounded=true&size=200&background=e36f4a&color=fff&name=${
    review.owner.first_name
  } ${review.owner.last_name}`;

  return (
    <React.Fragment>
      <style jsx>{profileCss}</style>
      <style jsx>{reviewItemCss}</style>
      <li className="review-item-container">
        <img
          width="75"
          height="75"
          src={review.owner.image || defaultProfile}
          alt=""
        />
        <div className="review-item-content">
          <StarsContainer>
            <Rating
              initialRating={review.stars}
              readonly={true}
              emptySymbol={
                <FontAwesomeIcon icon={faStar} size="xs" color="#e2e2e2" />
              }
              fullSymbol={
                <FontAwesomeIcon icon={faStar} size="xs" color="#f39c12" />
              }
            />
          </StarsContainer>
          <Message review={review} />
          <span className="review-meta review-trip">
            {t("review.item.from")}
            <span className="bold inline">{review.trip.from_city}</span>
            {t("review.item.to")}
            <span className="bold inline">{review.trip.to_city}</span>
          </span>
        </div>
      </li>
    </React.Fragment>
  );
};

export default Review;
