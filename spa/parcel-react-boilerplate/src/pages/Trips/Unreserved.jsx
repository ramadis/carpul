import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { connect } from "react-redux";
import MDSpinner from "react-md-spinner";
import styled from "styled-components";
import { useParams, Link } from "react-router-dom";
import Confetti from "react-confetti";
import AddToCalendar from "react-add-to-calendar";

import { useWindowSize } from "../../utils/hooks.js";

import { getTripById } from "../../services/Trip.js";

import Reservation from "../User/Reservation";

import "react-add-to-calendar/dist/react-add-to-calendar.css";
import profileHeroCss from "../../styles/profile_hero";
import poolListCss from "../../styles/pool_list";
import profileCss from "../../styles/profile";
import reviewItemCss from "../../styles/review_item";

const LinksContainer = styled.div`
  display: flex;
  align-items: center;
`;

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
      <div
        className="profile-form-container flex-center"
        style={{ marginTop: 20 }}
      >
        <div className="new-trip-form">
          <h3>You've unreserved your trip to {trip.to_city}</h3>
          <h2>
            Not everything is lost though! May we suggest you a few other
            alternatives to your destination?
          </h2>
          <Reservation trip={trip} />
          <LinksContainer>
            <Link
              className="login-button empty-button"
              to={`/user/${user.id}`}
              style={{
                display: "inline-block",
                width: "auto",
                marginLeft: 0,
              }}
            >
              {t("reservation.profile")}
            </Link>
          </LinksContainer>
        </div>
      </div>
    </React.Fragment>
  );
}

export default connect(state => ({ user: state.user }))(Reserved);
