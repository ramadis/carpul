import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { connect } from "react-redux";
import MDSpinner from "react-md-spinner";
import { useParams, Link } from "react-router-dom";
import Confetti from "react-confetti";

import { useWindowSize } from "../../utils/hooks.js";

import { getTripById } from "../../services/Trip.js";

import Reservation from "../User/Reservation";

import profileHeroCss from "../../styles/profile_hero";
import poolListCss from "../../styles/pool_list";
import profileCss from "../../styles/profile";
import reviewItemCss from "../../styles/review_item";

const ConfettiContainer = () => {
  const { width, height } = useWindowSize();
  return <Confetti recycle={false} width={width} height={height} />;
};

function Reserved({ user }) {
  const { t, i18n } = useTranslation();
  const { tripId } = useParams();
  const [trip, setTrip] = useState();

  useEffect(() => {
    getTripById(tripId).then(setTrip);
  }, []);

  const isLoading = !user || !trip;
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
      <ConfettiContainer />
      <div
        className="profile-form-container flex-center"
        style={{ marginTop: 20 }}
      >
        <div className="new-trip-form">
          <h3>{t("reservation.title")}</h3>
          <h2>{t("reservation.subtitle")}</h2>
          <Reservation trip={trip} />
          <h2 style={{ marginTop: 10 }}>
            Contact your driver to the email{" "}
            <a
              href={`mailto:${trip.driver.username}`}
              style={{ color: "#e36f4a" }}
            >
              {trip.driver.username}
            </a>
          </h2>
          <Link
            className="login-button empty-button"
            to={`/user/${user.id}`}
            style={{
              display: "inline-block",
              width: "auto",
              marginTop: 10,
              marginLeft: 0,
            }}
          >
            Check out the trip in your profile
          </Link>
        </div>
      </div>
    </React.Fragment>
  );
}

export default connect(state => ({ user: state.user }))(Reserved);
