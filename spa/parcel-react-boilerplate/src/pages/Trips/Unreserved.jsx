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
import { search } from "../../services/Search.js";

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

function Reserved({ user }) {
  const { t, i18n } = useTranslation();
  const { tripId } = useParams();
  const [trip, setTrip] = useState();
  const [suggestions, setSuggestions] = useState([]);
  const [isLoading, setLoading] = useState(true);

  useEffect(() => {
    const fetches = async () => {
      const trip = await getTripById(tripId);
      setTrip(trip);
      await search({
        to: trip.to_city,
        from: trip.from_city,
        when: trip.etd,
        per_page: 6,
      }).then(trips => setSuggestions(trips.filter(t => t.id !== trip.id)));
      setLoading(false);
    };
    fetches();
  }, []);

  const showLoading = !user || !trip || isLoading;
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

  if (showLoading) return Loading;

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
          <h3>{t("reservation.unreserve.title", { city: trip.to_city })}</h3>
          <h2>
            {suggestions.length
              ? t("reservation.unreserve.subtitle")
              : t("reservation.unreserve.subtitle_no_options")}
          </h2>
        </div>
        <div>
          {suggestions.map(suggestion => (
            <Trip key={suggestion.id} trip={suggestion} />
          ))}
          {!suggestions.length && (
            <Link
              className="login-button empty-button"
              to={`/`}
              style={{
                display: "inline-block",
                width: "auto",
                marginLeft: 0,
                marginTop: 20,
              }}
            >
              {t("reservation.unreserve.search")}
            </Link>
          )}
        </div>
      </div>
    </React.Fragment>
  );
}

export default connect(state => ({ user: state.user }))(Reserved);
