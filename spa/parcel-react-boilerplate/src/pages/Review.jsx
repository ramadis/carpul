import React, { useState, useEffect, useCallback } from "react";
import { useTranslation } from "react-i18next";
import { connect } from "react-redux";
import MDSpinner from "react-md-spinner";
import { useParams } from "react-router-dom";
import Rating from "react-rating";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar } from "@fortawesome/free-solid-svg-icons";
import styled from "styled-components";

import { getTripById } from "../services/Trip.js";
import { reviewTrip, addReviewImage } from "../services/Review.js";

import Hero from "../components/Hero";
import Dropzone from "../components/Dropzone";

import profileHeroCss from "../styles/profile_hero";
import poolListCss from "../styles/pool_list";
import profileCss from "../styles/profile";
import reviewItemCss from "../styles/review_item";

const Field = styled.div`
  margin-top: 10px;
`;

const UploadedImageContainer = styled.div`
  position: relative;
`;

const RemoveImageButton = styled.button`
  position: absolute;
  top: -5;
  cursor: pointer;
  left: -10;
  border-radius: 50%;
  height: 25px;
  width: 25px;
  background: #e36f49;
  color: white;
`;

const Review = ({ user }) => {
  const { t, i18n } = useTranslation();
  const { id: tripId } = useParams();

  const [image, setImage] = useState();
  const [trip, setTrip] = useState();
  const [stars, setStars] = useState(0);
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);

  const onImageLoaded = (imageURL, imageRAW, file) => {
    const image = new Image();
    image.src = imageURL;
    image.onload = () =>
      setImage({
        URL: imageURL,
        RAW: imageRAW,
        element: image,
        file,
      });
  };

  const review = async () => {
    try {
      setLoading(true);
      const review = await reviewTrip(trip.id, { stars, message });
      if (image) await addReviewImage(review.id, image);
      setLoading(false);
    } catch (e) {
      setLoading(false);
      console.error(e);
    }
  };

  useEffect(() => {
    getTripById(tripId).then(setTrip);
  }, []);

  window.document.title = t("review.add.page_title");

  const isLoading = !user && !trip;

  const Loading = (
    <React.Fragment>
      <style jsx>{poolListCss}</style>
      <style jsx>{profileCss}</style>
      <style jsx>{reviewItemCss}</style>
      <style jsx>{profileHeroCss}</style>
      <div className="flex-center spinner-class">
        <MDSpinner size={36} />
      </div>
    </React.Fragment>
  );

  if (isLoading) return Loading;

  return (
    <React.Fragment>
      <style jsx>{poolListCss}</style>
      <style jsx>{profileCss}</style>
      <style jsx>{reviewItemCss}</style>
      <style jsx>{profileHeroCss}</style>
      <Hero hero_message={t("review.add.hero")} user={user} />

      {trip ? (
        <div className="profile-form-container flex-center">
          <div className="new-trip-form" action="../review/${trip.id}">
            <h3>{t("review.add.title")}</h3>
            <h2>
              {t("review.add.subtitle", {
                "0": user.first_name,
                "1": trip.driver.first_name,
                "2": trip.from_city,
                "3": trip.to_city,
              })}
            </h2>

            <div className="field-container">
              <label path="message" className="field-label" htmlFor="message">
                {t("review.add.message")}
              </label>
              <Field>
                {image ? (
                  <UploadedImageContainer className="uploaded-image-container">
                    <img
                      src={image.URL}
                      height={100}
                      width={(100 * image.element.width) / image.element.height}
                    />
                    <RemoveImageButton onClick={() => setImage(null)}>
                      X
                    </RemoveImageButton>
                  </UploadedImageContainer>
                ) : (
                  <Dropzone onLoad={onImageLoaded} />
                )}
              </Field>
              <Field>
                <Rating
                  initialRating={stars}
                  onChange={setStars}
                  emptySymbol={
                    <FontAwesomeIcon icon={faStar} size="1x" color="#808080" />
                  }
                  fullSymbol={
                    <FontAwesomeIcon icon={faStar} size="1x" color="#f39c12" />
                  }
                />
              </Field>
              <Field>
                <textarea
                  value={message}
                  onChange={e => setMessage(e.target.value)}
                  className="field review-textarea"
                  placeholder={t("review.add.placeholder")}
                  required={true}
                  multiline="true"
                  name="message"
                  path="message"
                  type="text"
                />
              </Field>
            </div>

            <div className="actions">
              <button type="submit" onClick={review} className="login-button">
                {loading ? <MDSpinner size={24} /> : t("review.add.submit")}
              </button>
            </div>
          </div>
        </div>
      ) : (
        Loading
      )}
    </React.Fragment>
  );
};

export default connect(state => ({ user: state.user }))(Review);
