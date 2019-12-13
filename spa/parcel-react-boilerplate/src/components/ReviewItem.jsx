import React, { useState, Fragment } from "react";
import { useTranslation } from "react-i18next";
import { format } from "date-fns";
import Rating from "react-rating";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar } from "@fortawesome/free-solid-svg-icons";
import styled from "styled-components";

import { routes } from "../App";

import profileCss from "../styles/profile";
import reviewItemCss from "../styles/review_item";

const StarsContainer = styled.div`
  margin-bottom: 5px;
`;

const ImageContainer = styled.a`
  margin: 5px 0;
  display: inline-block;
`;

const Message = ({ review }) => {
  const maxDisplayLength = 50;
  const [full, setFull] = useState(false);
  const readMore = () => setFull(true);
  const message = (review.message || "").split("\n").map(
    line =>
      line && (
        <Fragment key={line}>
          <style jsx>{reviewItemCss}</style>
          <p>{line}</p>
        </Fragment>
      )
  );

  if (review.message.length <= maxDisplayLength) {
    return (
      <React.Fragment>
        <style jsx>{reviewItemCss}</style>
        <div className="review-message">{message}</div>
      </React.Fragment>
    );
  }

  if (full) {
    return (
      <React.Fragment>
        <style jsx>{reviewItemCss}</style>
        <div className="review-message">{message}</div>
      </React.Fragment>
    );
  }

  return (
    <React.Fragment>
      <style jsx>{reviewItemCss}</style>
      <div className="review-message">
        {review.message.substr(0, maxDisplayLength)}...
      </div>
      <div className="review-message read-more-button" onClick={readMore}>
        Read more
      </div>
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
        <Link to={routes.profile(review.owner.id)}>
          <img
            width="75"
            className="profile-image"
            height="75"
            src={review.owner.image || defaultProfile}
            alt=""
          />
        </Link>
        <div className="review-item-content">
          <StarsContainer>
            <Rating
              initialRating={review.stars}
              readonly
              emptySymbol={
                <FontAwesomeIcon icon={faStar} size="xs" color="#e2e2e2" />
              }
              fullSymbol={
                <FontAwesomeIcon icon={faStar} size="xs" color="#f39c12" />
              }
            />
          </StarsContainer>
          {review.image ? (
            <ImageContainer href={review.image}>
              <img target="_blank" height={75} src={review.image} />
            </ImageContainer>
          ) : null}
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
