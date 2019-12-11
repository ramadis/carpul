import React, { useState, useEffect, useCallback } from "react";
import { useTranslation } from "react-i18next";
import { connect } from "react-redux";
import MDSpinner from "react-md-spinner";
import { useParams, useHistory } from "react-router-dom";
import Rating from "react-rating";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar } from "@fortawesome/free-solid-svg-icons";
import styled from "styled-components";
import { isEmpty } from "lodash";
import { NotificationManager } from "react-notifications";

import { getTripById } from "../services/Trip.js";
import { reviewTrip, addReviewImage } from "../services/Review.js";

import { routes } from "../App";
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

const SubmitButton = styled.button`
  width: 150px;
  padding: 10px 0;
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

  &:hover {
    background: #e36f4a;
  }
`;

const Review = ({ user }) => {
  const { t, i18n } = useTranslation();
  const { id: tripId } = useParams();
  const history = useHistory();

  const [image, setImage] = useState();
  const [trip, setTrip] = useState();
  const [stars, setStars] = useState(0);
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);
  const [errors, setErrors] = useState({ clean: true });

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

      NotificationManager.success(
        "Review added successfully!",
        "Thanks for leaving your mark."
      );
      history.push(routes.profile(user.id));
      setLoading(false);
    } catch (error) {
      console.error(error);
      NotificationManager.error(error.message.subtitle, error.message.title);

      if (error.origin === "review" && error.code === 409) {
        history.push(routes.profile(user.id));
      }

      setLoading(false);
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
                  onChange={stars => {
                    setStars(stars);
                    errors.message ||
                    message.length < 10 ||
                    message.length > 300
                      ? setErrors({ message: true })
                      : setErrors({});
                  }}
                  emptySymbol={
                    <FontAwesomeIcon icon={faStar} size="1x" color="#808080" />
                  }
                  fullSymbol={
                    <FontAwesomeIcon icon={faStar} size="1x" color="#f39c12" />
                  }
                />
                {errors.stars ? (
                  <div style={{ marginTop: 5 }}>
                    <label className="label-error">
                      {t("review.add.errors.stars")}
                    </label>
                  </div>
                ) : null}
              </Field>
              <Field>
                <textarea
                  value={message}
                  onChange={e => {
                    const message = e.target.value;
                    setMessage(message);
                    if (message.length < 10 || message.length > 300) {
                      setErrors(
                        stars === 0
                          ? { stars: true, message: true }
                          : { message: true }
                      );
                    } else setErrors(stars === 0 ? { stars: true } : {});
                  }}
                  className="field review-textarea"
                  placeholder={t("review.add.placeholder")}
                  required={true}
                  multiline="true"
                  name="message"
                  path="message"
                  type="text"
                />
              </Field>
              {errors.message ? (
                <label className="label-error">
                  {t("review.add.errors.message")}
                </label>
              ) : null}
            </div>

            <div className="actions" style={{ marginBottom: 10 }}>
              <SubmitButton
                type="submit"
                disabled={!isEmpty(errors) || loading}
                onClick={review}
              >
                {loading ? <MDSpinner size={24} /> : t("review.add.submit")}
              </SubmitButton>
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
