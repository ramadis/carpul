import React, { useState, useEffect, useCallback } from "react";
import { useDropzone } from "react-dropzone";
import { useTranslation } from "react-i18next";
import { connect } from "react-redux";
import MDSpinner from "react-md-spinner";
import { useParams } from "react-router-dom";
import Rating from "react-rating";

import { getTripById } from "../services/Trip.js";
import { reviewTrip } from "../services/Review.js";

import Hero from "../components/Hero";

import profileHeroCss from "../styles/profile_hero";
import poolListCss from "../styles/pool_list";
import profileCss from "../styles/profile";
import reviewItemCss from "../styles/review_item";

const Dropzone = () => {
  const { t, i18n } = useTranslation();

  const onDrop = useCallback(acceptedFiles => {
    // Do something with the files
  }, []);
  const { getRootProps, getInputProps, isDragActive } = useDropzone({ onDrop });

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
      <p>{t("review.add.dropzone")}</p>
    </div>
  );
};

const Review = ({ user }) => {
  const { t, i18n } = useTranslation();
  const { id: tripId } = useParams();

  const [trip, setTrip] = useState();
  const [stars, setStars] = useState(0);
  const [message, setMessage] = useState("");

  const review = () => reviewTrip(trip.id, { stars, message });

  useEffect(() => {
    getTripById(tripId).then(setTrip);
  }, []);

  window.document.title = t("review.add.page_title");

  const isLoading = !user || !trip;

  if (isLoading) {
    return (
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
  }

  return (
    <React.Fragment>
      <style jsx>{poolListCss}</style>
      <style jsx>{profileCss}</style>
      <style jsx>{reviewItemCss}</style>
      <style jsx>{profileHeroCss}</style>
      <Hero hero_message={t("review.add.hero")} user={user} />

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
            <label path="message" className="field-label" for="message">
              {t("review.add.message")}
            </label>
            <Rating initialRating={stars} onChange={setStars} />
            <Dropzone />
            <textarea
              value={message}
              onChange={e => setMessage(e.target.value)}
              className="field"
              required="true"
              multiline="true"
              name="message"
              path="message"
              type="text"
            />
          </div>

          <div className="actions">
            <button type="submit" onClick={review} className="login-button">
              {t("review.add.submit")}
            </button>
          </div>
        </div>
      </div>
    </React.Fragment>
  );
};

export default connect(state => ({ user: state.user }))(Review);
