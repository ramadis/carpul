import React, { useState, useEffect } from "react";
import styled from "styled-components";
import { connect } from "react-redux";
import { useTranslation } from "react-i18next";
import { isEmpty } from "lodash";
import DatePicker from "react-datepicker";
import MDSpinner from "react-md-spinner";
import Masonry from "react-masonry-css";

import { getCity, getLocation } from "../services/Places.js";
import { getSuggestions } from "../services/Search.js";
import { routes } from "../services/Routes.js";

import SmallItem from "../components/SmallItem";
import PlacesAutocomplete from "../components/PlacesAutocomplete";

import "react-datepicker/dist/react-datepicker.css";
import poolListCss from "../styles/pool_list";
import mapsCss from "../styles/maps";
import homeCss from "../styles/home";
import masonryCss from "../styles/masonry";

const Footer = styled.footer`
  width: 100%;
  height: 40px;
  display: flex;
  justify-content: center;
  color: grey;
  align-items: center;
  margin-bottom: 20px;
`;

const Home = ({ user }) => {
  const { t } = useTranslation();
  const [origin, setOrigin] = useState({ city: "" });
  const [destination, setDestination] = useState({ city: "" });
  const [datetime, setDatetime] = useState();
  const [trips, setTrips] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    setLoading(true);
    const fetches = async () => {
      const city = await getLocation().catch(e => console.error(e));
      if (!origin.city && city) setOrigin({ city });
      await getSuggestions(city).then(setTrips);
      setLoading(false);
    };
    fetches();
  }, []);

  return (
    <React.Fragment>
      <style jsx>{poolListCss}</style>
      <style jsx>{mapsCss}</style>
      <style jsx>{homeCss}</style>
      <style jsx>{masonryCss}</style>

      <div className="home-hero-container flex-center">
        <div className="home-content-container">
          <div className="titles">
            <h1 className="title">{t("home.index.title1")}</h1>
            <h1 className="title">{t("home.index.title2")}</h1>
          </div>

          <h2 className="subtitle">{t("home.index.subtitle")}</h2>

          <div>
            <div className="searchbar">
              <label className="searchbar-label" htmlFor="from">
                {t("home.index.from")}
              </label>
              <PlacesAutocomplete
                style={{ display: "inline", paddingRight: 10 }}
                value={origin.city}
                handleSelect={place => {
                  setOrigin({
                    city: getCity(place),
                    position: { latitude: place.lat, longitude: place.lon },
                    selected: getCity(place),
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
                style={{ display: "inline", paddingRight: 10 }}
                value={destination.city}
                handleSelect={place => {
                  setDestination({
                    city: getCity(place),
                    position: { latitude: place.lat, longitude: place.lon },
                    selected: getCity(place),
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
              <a
                disabled={
                  !datetime && !isEmpty(origin) && !isEmpty(destination)
                }
                className="login-button searchbar-button"
                name="button"
                tabIndex="4"
                href={routes.search({
                  from: origin,
                  to: destination,
                  date: datetime,
                })}
              >
                {t("home.index.submit")}
              </a>
            </div>
          </div>
        </div>
      </div>
      <div
        className="trips-recommendations-container"
        style={{ marginBottom: 20, paddingTop: 20 }}
      >
        {loading ? (
          <MDSpinner />
        ) : trips.length === 0 ? (
          <h1>{t("home.index.no_trips")}</h1>
        ) : (
          <React.Fragment>
            <h1>{t("home.index.suggestions")}</h1>
            <div className="trip-recommendation-list">
              <Masonry
                breakpointCols={3}
                className="my-masonry-grid"
                columnClassName="my-masonry-grid_column"
              >
                {trips.map(trip => (
                  <SmallItem key={trip.id} trip={trip} />
                ))}
              </Masonry>
            </div>
          </React.Fragment>
        )}
      </div>
      <Footer>
        <p>Copyright &copy; 2019 to Carpul&trade;</p>
      </Footer>
    </React.Fragment>
  );
};

export default connect(state => ({ user: state.user }))(Home);
