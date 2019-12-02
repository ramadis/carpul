import React, { useState, useEffect } from "react";
import Hero from "../components/Hero";
import { useTranslation } from "react-i18next";
import { connect } from "react-redux";
import MDSpinner from "react-md-spinner";
import { useParams } from "react-router-dom";

import { getTripById } from "../services/Trip.js";

import profileHeroCss from "../styles/profile_hero";
import poolListCss from "../styles/pool_list";
import profileCss from "../styles/profile";
import reviewItemCss from "../styles/review_item";

const Review = ({ user }) => {
  const { t, i18n } = useTranslation();
  const { id: tripId } = useParams();
  const [trip, setTrip] = useState();

  useEffect(() => {
    getTripById(tripId).then(setTrip);
  }, []);

  window.document.title = `Carpul | Leave your mark`;

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

      <div class="profile-form-container flex-center">
        <form
          class="new-trip-form"
          modelAttribute="reviewForm"
          action="../review/${trip.id}"
        >
          <h3>{t("review.add.title")}</h3>
          <h2>
            {t("review.add.subtitle", {
              "0": user.first_name,
              "1": trip.driver.first_name,
              "2": trip.from_city,
              "3": trip.to_city,
            })}
          </h2>

          <div class="field-container">
            <label path="message" class="field-label" for="message">
              {t("review.add.message")}
            </label>
            <div id="stars" class="stars" />
            <input
              required="required"
              class="field hide"
              name="stars"
              value="0"
              readonly="true"
              path="stars"
              min="0"
              max="5"
              type="number"
            />
            <textarea
              class="field"
              required="true"
              multiline="true"
              name="message"
              path="message"
              type="text"
            />
            <errors path="message" class="form-error" element="p" />
          </div>

          <div class="actions">
            <button type="submit" class="login-button">
              {t("review.add.submit")}
            </button>
          </div>
        </form>
      </div>
    </React.Fragment>
  );
};

export default connect(state => ({ user: state.user }))(Review);
