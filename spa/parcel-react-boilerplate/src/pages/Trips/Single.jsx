import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { connect } from "react-redux";
import MDSpinner from "react-md-spinner";
import styled from "styled-components";
import { useParams, Link } from "react-router-dom";
import Confetti from "react-confetti";
import AddToCalendar from "react-add-to-calendar";

import { useWindowSize } from "../../utils/hooks";
import { requestCatch } from "../../utils/fetch";

import { routes } from "../../services/Routes";
import { getTripById } from "../../services/Trip";
import { search } from "../../services/Search";

import Reservation from "../User/Reservation";
import { Trip } from "../Search";

import "react-add-to-calendar/dist/react-add-to-calendar.css";
import profileHeroCss from "../../styles/profile_hero";
import poolListCss from "../../styles/pool_list";
import profileCss from "../../styles/profile";
import reviewItemCss from "../../styles/review_item";

const LinksContainer = styled.div`
  display: flex;
  align-items: center;
`;

const Letter = styled.span`
  font-size: 12px;
  color: grey;
  letter-spacing: 2;
  margin-bottom: 20px;
  display: inline-block;
`;

function Single({ user }) {
  const { t, i18n } = useTranslation();
  const { tripId } = useParams();
  const [trip, setTrip] = useState();

  useEffect(() => {
    const fetches = async () => {
      try {
        const trip = await getTripById(tripId).then(setTrip);
      } catch (error) {
        requestCatch(error);
      }
    };
    fetches();
  }, []);

  const isLoading = !trip;
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
        style={{ marginTop: 20, flexDirection: "column", marginBottom: 20 }}
      >
        <div className="new-trip-form">
          {user && trip.driver.id === user.id ? (
            <Letter>{t("single.letter")}</Letter>
          ) : null}
          <h3>{t("single.title", { city: trip.to_city })}</h3>
          <h2>{t("single.subtitle")}</h2>
        </div>
        <div className="flex-center" style={{ flexDirection: "column" }}>
          <div style={{ marginLeft: -150 }}>
            <Trip hideShare={true} trip={trip} />
          </div>
          <Link
            className="login-button empty-button"
            to={routes.search({
              to: { city: trip.to_city },
              from: { city: "" },
            })}
            style={{
              display: "inline-block",
              width: "auto",
              marginLeft: 0,
              marginTop: 20,
            }}
          >
            {t("reservation.unreserve.search")}
          </Link>
        </div>
      </div>
    </React.Fragment>
  );
}

export default connect(state => ({ user: state.user }))(Single);
