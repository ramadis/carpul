import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { connect } from "react-redux";
import MDSpinner from "react-md-spinner";
import styled from "styled-components";
import { useParams, Link, useHistory } from "react-router-dom";
import { NotificationManager } from "react-notifications";

import { useWindowSize } from "../../utils/hooks";
import { requestCatch } from "../../utils/fetch";

import { getTripById } from "../../services/Trip";
import { search } from "../../services/Search";

import { routes } from "../../App";
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

function Unreserved({ user }) {
  const { t, i18n } = useTranslation();
  const { tripId } = useParams();
  const [trip, setTrip] = useState();
  const history = useHistory();
  const [suggestions, setSuggestions] = useState([]);
  const [isLoading, setLoading] = useState(true);

  useEffect(() => {
    const fetches = async () => {
      try {
        const trip = await getTripById(tripId);
        setTrip(trip);
        await search({
          to: trip.to_city,
          from: trip.from_city,
          when: trip.etd,
          per_page: 6,
        }).then(trips => setSuggestions(trips.filter(t => t.id !== trip.id)));
        setLoading(false);
      } catch (error) {
        requestCatch(error);
        history.push("/404");
        setLoading(false);
      }
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

  if (trip.driver.id === user.id) {
    NotificationManager.error(
      "We brought you back home anyway",
      "You can't unreserve your own trips"
    );
    history.push(routes.profile(user.id));
    return null;
  }

  if (trip.reserved) {
    NotificationManager.error(
      "Here, check your reservation details",
      "You didn't unreserve this tripy yet!"
    );
    history.push(routes.reservedTrip(trip.id));
    return null;
  }

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

export default connect(state => ({ user: state.user }))(Unreserved);
