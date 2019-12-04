import React, { useState } from "react";
import styled from "styled-components";
import { useTranslation } from "react-i18next";
import { isEmpty } from "lodash";
import DatePicker from "react-datepicker";

import { formatCity } from "../services/Places.js";

import SmallItem from "../components/SmallItem";
import PlacesAutocomplete from "../components/PlacesAutocomplete";

import "react-datepicker/dist/react-datepicker.css";
import poolListCss from "../styles/pool_list";
import mapsCss from "../styles/maps";
import homeCss from "../styles/home";

const Home = ({ match, trips }) => {
  const { t, i18n } = useTranslation();
  const [origin, setOrigin] = useState({});
  const [destination, setDestination] = useState({});
  const [datetime, setDatetime] = useState();
  trips = [];

  return (
    <React.Fragment>
      <style jsx>{poolListCss}</style>
      <style jsx>{mapsCss}</style>
      <style jsx>{homeCss}</style>

      <div className="home-hero-container flex-center">
        <div className="home-content-container">
          <div className="titles">
            <h1 className="title">{t("home.index.title1")}</h1>
            <h1 className="title">{t("home.index.title2")}</h1>
          </div>

          <h2 className="subtitle">{t("home.index.subtitle")}</h2>

          <form method="post" action="search">
            <div className="searchbar">
              <label className="searchbar-label" htmlFor="from">
                {t("home.index.from")}
              </label>
              <PlacesAutocomplete
                style={{ display: "inline" }}
                value={origin.city}
                handleSelect={place => {
                  setOrigin({
                    city: formatCity(place),
                    position: { latitude: place.lat, longitude: place.lon },
                    selected: formatCity(place),
                    raw: place,
                  });
                }}
              >
                <input
                  value={origin.city}
                  onChange={e => setOrigin({ city: e.target.value })}
                  className={`searchbar-input`}
                  type="text"
                  placeholder="Origin"
                  tabIndex="1"
                  name="from"
                />
              </PlacesAutocomplete>
              <label path="to" className="searchbar-label" htmlFor="to">
                {t("home.index.to")}
              </label>
              <PlacesAutocomplete
                style={{ display: "inline" }}
                value={destination.city}
                handleSelect={place => {
                  setDestination({
                    city: formatCity(place),
                    position: { latitude: place.lat, longitude: place.lon },
                    selected: formatCity(place),
                    raw: place,
                  });
                }}
              >
                <input
                  value={destination.city}
                  onChange={e => setDestination({ city: e.target.value })}
                  className={`searchbar-input`}
                  type="text"
                  placeholder="Destination"
                  tabIndex="2"
                  name="to"
                />
              </PlacesAutocomplete>
              <label path="when" className="searchbar-label" htmlFor="when">
                {t("home.index.on")}
              </label>
              <DatePicker
                selected={datetime}
                onChange={date => setDatetime(date)}
                showTimeSelect
                timeFormat={t("trip.add.time")}
                todayButton={t("trip.add.today")}
                timeCaption="time"
                minDate={new Date()}
                placeholderText="Time range"
                timeIntervals={15}
                customInput={
                  <input
                    className="searchbar-input"
                    name="when"
                    type="text"
                    tabIndex="3"
                  />
                }
                dateFormat={t("trip.add.timestamp")}
              />
              <button
                type="submit"
                disabled={
                  !datetime && !isEmpty(origin) && !isEmpty(destination)
                }
                className="login-button searchbar-button"
                name="button"
                tabIndex="4"
              >
                {t("home.index.submit")}
              </button>
            </div>
          </form>
        </div>
      </div>
      <div className="trips-recommendations-container">
        {trips.length === 0 ? (
          <h1>{t("home.index.no_trips")}</h1>
        ) : (
          <React.Fragment>
            <h1>{t("home.index.suggestions")}</h1>
            <div className="trip-recommendation-list">
              {trips.map(trip => (
                <SmallItem trip={trip} />
              ))}
            </div>
          </React.Fragment>
        )}
      </div>
    </React.Fragment>
  );
};

// TODO
// <script src="<c:url value='/static/js/search_time.js' />" charset="utf-8"></script>

export default Home;
