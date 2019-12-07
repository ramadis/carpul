import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { connect } from "react-redux";
import { format, parse } from "date-fns";
import Rating from "react-rating";
import DatePicker from "react-datepicker";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { debounce } from "lodash";
import { faStar } from "@fortawesome/free-solid-svg-icons";
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Link,
  Redirect,
} from "react-router-dom";
import { useTranslation } from "react-i18next";
import { useLocation } from "react-router-dom";

import { search } from "../services/search";
import { reserveByTrip, unreserveByTrip } from "../services/Reservation";
import { getCity } from "../services/Places.js";

import PlacesAutocomplete from "../components/PlacesAutocomplete";

import "react-datepicker/dist/react-datepicker.css";
import poolListCss from "../styles/pool_list";
import imgSeats from "../../images/seats.png";

function useQuery() {
  return [...new URLSearchParams(useLocation().search).entries()].reduce(
    (ac, [key, val]) => ({ ...ac, [key]: val }),
    {}
  );
}

const SearchInput = styled.input`
  border: 0;
  border-bottom: solid 1px #858585;
  font-size: 15px;
  width: 200px;
  padding: 5px;
`;

const HeaderContainer = styled.div`
  background: white;
  height: 45px;
  border: 1px solid darkgray;
  border-right: 0;
  border-left: 0;
  padding: 0 30px;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const SearchContainer = styled.div`
  display: flex;
`;

const Search = ({ user }) => {
  const { t, i18n } = useTranslation();
  const [trips, setTrips] = useState([]);
  const rawParams = useQuery();
  const [params, setParams] = useState(rawParams);
  const { to, from, when } = params;

  const handleSearch = debounce(setParams, 1000);

  useEffect(() => {
    // const exRep = [
    //   {
    //     id: 58,
    //     etd: 1576101600000,
    //     eta: 1576706400000,
    //     from_city: "Buenos aires",
    //     to_city: "Pinamar",
    //     cost: 100.0,
    //     seats: 4,
    //     departure: { latitude: -36.3789925, longitude: -60.3855889 },
    //     arrival: { latitude: -37.1099492, longitude: -56.8539007 },
    //     occupied_seats: 0,
    //     driver: {
    //       username: "juli",
    //       first_name: "Julian",
    //       last_name: "Antonielli",
    //       created: 1507678063362,
    //       id: 2,
    //     },
    //     passengers: [],
    //     expired: false,
    //   },
    // ];
    search({ to, from, when }).then(setTrips);
  }, [params]);

  const searchDate = format(new Date(Number(when)), "DD/MM/YYYY HH:mm");

  return (
    <div>
      <style jsx>{poolListCss}</style>

      <HeaderContainer>
        <SearchBar onSearch={handleSearch} />
        <Link to="/trips/add" className="login-button inverted hard-edges">
          {t("search.search.create")}
        </Link>
      </HeaderContainer>

      <div className="list-container">
        {trips.length > 0 && (
          <React.Fragment>
            <span className="list-subtitle">
              {to
                ? t("search.search.trips", { to, when: searchDate })
                : t("search.search.trips_no_to", {
                    origin: from,
                    when: searchDate,
                  })}
            </span>
            {trips.map(trip => (
              <Trip trip={trip} key={trip.id} />
            ))}
          </React.Fragment>
        )}

        {trips.length === 0 && (
          <span className="list-subtitle">
            {to
              ? t("search.search.no_trips", { to })
              : t("search.search.no_trips_no_to", { origin: from })}
          </span>
        )}
      </div>
    </div>
  );
};

const encodeQueryParams = function(obj) {
  var str = [];
  for (var p in obj)
    if (obj.hasOwnProperty(p)) {
      str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
    }
  return str.join("&");
};

const SearchBar = ({ onSearch = () => null }) => {
  const { t, i18n } = useTranslation();
  const { to, from, when } = useQuery();

  const [origin, setOrigin] = useState({ city: from });
  const [destination, setDestination] = useState({ city: to });
  const [datetime, setDatetime] = useState(new Date(Number(when)));

  const searchDate = format(new Date(Number(when)), "DD/MM/YYYY HH:mm");

  const pushSearch = value => {
    onSearch({
      from: origin.city,
      to: destination.city,
      when: datetime.getTime(),
      ...value,
    });
  };

  const { protocol, host, pathname } = window.location;
  if (history.pushState) {
    const query = `?${encodeQueryParams({
      from: origin.city,
      to: destination.city,
      when: datetime.getTime(),
    })}`;
    var newurl = `${protocol}//${host + pathname + query}`;
    window.history.pushState({ path: newurl }, "", newurl);
  }

  return (
    <SearchContainer>
      <div className="destination-container">
        <span className="bold m-r-5">{t("search.search.from")}</span>
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
            pushSearch({ from: getCity(place) });
          }}
        >
          <SearchInput
            value={origin.city}
            onChange={e => {
              setOrigin({ city: e.target.value });
              pushSearch({ from: e.target.value });
            }}
            className="clear"
            type="text"
            tabIndex="1"
          />
        </PlacesAutocomplete>
      </div>
      <div className="destination-container">
        <span className="bold m-r-5">{t("search.search.to")}</span>
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
            pushSearch({ to: getCity(place) });
          }}
        >
          <SearchInput
            value={destination.city}
            onChange={e => {
              setDestination({ city: e.target.value });
              pushSearch({ to: e.target.value });
            }}
            className="clear"
            type="text"
            tabIndex="1"
          />
        </PlacesAutocomplete>
      </div>
      <div className="destination-container">
        <span className="bold m-r-5">{t("search.search.on")}</span>
        <DatePicker
          selected={datetime}
          onChange={date => {
            setDatetime(date);
            pushSearch({ when: date.getTime() });
          }}
          showTimeSelect
          timeFormat={t("trip.add.time")}
          todayButton={t("trip.add.today")}
          timeCaption="time"
          minDate={new Date()}
          placeholderText="Time range"
          timeIntervals={15}
          customInput={
            <SearchInput
              className="clear"
              style={{ width: 130 }}
              value={searchDate}
              type="text"
            />
          }
          dateFormat={t("trip.add.timestamp")}
        />
      </div>
    </SearchContainer>
  );
};

const ImageContainer = styled.div`
  display: flex;
  justify-content: flex-end;
  align-items: flex-end;
  position: relative;
  width: 100%;
  background-size: cover;
  height: 100%;
`;

const PathLink = styled.a`
  position: absolute;
  bottom: 10px;
  right: 10px;
`;

const Trip = ({ trip }) => {
  const { t } = useTranslation();

  const getStyle = () => {
    const seed = `${trip.to_city}`.replace(/\s/g, "");
    return {
      backgroundImage: `url(https://picsum.photos/seed/${seed}/600/300)`,
    };
  };

  return (
    <React.Fragment>
      <style jsx>{poolListCss}</style>

      <div className="pool-item flex-center">
        <div className="user-info flex space-around align-center column h-150">
          <div className="user-image">
            <img
              src={`https://ui-avatars.com/api/?rounded=true&size=85&background=e36f4a&color=fff&name=${
                trip.driver.first_name
              } ${trip.driver.last_name}`}
              alt=""
            />
          </div>
          <div className="user-name">{trip.driver.first_name}</div>
          <span className="user-rating">
            <Rating
              initialRating={trip.driver.rating}
              readonly={true}
              emptySymbol={
                <FontAwesomeIcon icon={faStar} size="xs" color="#e2e2e2" />
              }
              fullSymbol={
                <FontAwesomeIcon icon={faStar} size="xs" color="#f39c12" />
              }
            />
          </span>
        </div>

        <div className="pool-info">
          <div className="map-container">
            <ImageContainer style={getStyle()}>
              <PathLink
                className="destiny-time map-trigger login-button"
                target="iframe"
                href={`https://www.google.com/maps/embed/v1/directions?key=AIzaSyCNS1Xx_AGiNgyperC3ovLBiTdsMlwnuZU&origin=${
                  trip.departure.latitude
                }, ${trip.departure.longitude}&destination=${
                  trip.arrival.latitude
                }, ${trip.arrival.longitude}`}
              >
                {t("user.trip.map")}
              </PathLink>
            </ImageContainer>
          </div>
          <div className="bg-white">
            <div className="price-container flex space-between align-center">
              <span className="clear gray sz-13">
                {t("search.item.from")}
                <span className="bold black"> {trip.from_city}</span>
                {t("search.item.on_low")}
                <span className="bold black">
                  {format(trip.etd, "DD/MM/YYYY")}
                </span>
                {t("search.item.at")}
                <span className="bold black">{format(trip.etd, "HH:mm")}</span>
                <br />
                {t("search.item.arrive")}
                <span className="bold black"> {trip.to_city}</span>
                {t("search.item.on_low")}
                <span className="bold black">
                  {format(trip.eta, "DD/MM/YYYY")}
                </span>
                {t("search.item.at")}
                <span className="bold black">{format(trip.eta, "HH:mm")}</span>
              </span>
              <div>
                <span className="price gray">
                  <span className="bold black">
                    ${trip.cost}/{t("search.item.each")}
                  </span>
                </span>

                {!trip.reserved && (
                  <button
                    className="login-button inline-block"
                    onClick={() => reserveByTrip(trip.id)}
                  >
                    {t("search.item.reserve")}
                  </button>
                )}
                {trip.reserved && (
                  <button
                    className="inline-block login-button main-color"
                    onClick={() => unreserveByTrip(trip.id)}
                  >
                    {t("search.item.unreserve")}
                  </button>
                )}
              </div>
            </div>

            <div className="pool-features flex space-between align-center">
              <div className="features-container" />
              <div className="seats-container">
                <span className="seats bold gray">
                  <img className="seats-icon" src={imgSeats} />
                  {trip.available_seats} {t("search.item.available")}
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </React.Fragment>
  );
};
export default connect(state => ({ user: state.user }))(Search);
