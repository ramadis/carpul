import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { connect } from "react-redux";
import { format, parse } from "date-fns";
import Rating from "react-rating";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
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

import poolListCss from "../styles/pool_list";

function useQuery() {
  return [...new URLSearchParams(useLocation().search).entries()].reduce(
    (ac, [key, val]) => ({ ...ac, [key]: val }),
    {}
  );
}

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
  const { to, from, when } = useQuery();

  useEffect(() => {
    const exRep = [
      {
        id: 58,
        etd: 1576101600000,
        eta: 1576706400000,
        from_city: "Buenos aires",
        to_city: "Pinamar",
        cost: 100.0,
        seats: 4,
        departure: { latitude: -36.3789925, longitude: -60.3855889 },
        arrival: { latitude: -37.1099492, longitude: -56.8539007 },
        occupied_seats: 0,
        driver: {
          username: "juli",
          first_name: "Julian",
          last_name: "Antonielli",
          created: 1507678063362,
          id: 2,
        },
        passengers: [],
        expired: false,
      },
    ];
    search({ to, from, when }).finally(res => setTrips(res || exRep));
  }, []);

  const searchDate = format(new Date(Number(when)), "DD/MM/YYYY");

  return (
    <div>
      <style jsx>{poolListCss}</style>

      <HeaderContainer>
        <SearchContainer>
          <div className="destination-container">
            <span className="bold m-r-5">{t("search.search.from")}</span>
            <span className="clear">{from}</span>
          </div>
          <div className="destination-container">
            <span className="bold m-r-5">{t("search.search.to")}</span>
            <span className="clear">{to}</span>
          </div>
          <div className="destination-container">
            <span className="bold m-r-5">{t("search.search.on")}</span>
            <span className="clear">{searchDate}</span>
          </div>
        </SearchContainer>
        <Link to="/trips/add" className="login-button inverted hard-edges">
          {t("search.search.create")}
        </Link>
      </HeaderContainer>

      <div className="list-container">
        {trips.length > 0 && (
          <React.Fragment>
            <span className="list-subtitle">
              {t("search.search.trips", { to, when: searchDate })}
            </span>
            {trips.map(trip => (
              <Trip trip={trip} key={trip.id} />
            ))}
          </React.Fragment>
        )}

        {trips.length === 0 && (
          <span className="list-subtitle">
            {t("search.search.no_trips", { to })}
          </span>
        )}
      </div>
    </div>
  );
};

const Trip = ({ trip }) => {
  const { t } = useTranslation();
  return (
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
              <FontAwesomeIcon icon={faStar} size="xs" color="#f4f4f4" />
            }
            fullSymbol={
              <FontAwesomeIcon icon={faStar} size="xs" color="#f39c12" />
            }
          />
        </span>
      </div>

      <div className="pool-info">
        <div className="map-container">
          <img
            src={`https://maps.googleapis.com/maps/api/staticmap?key=AIzaSyCKIU4-Ijaeex54obPySJ7kXLwLnrV5BRA&size=1200x200&markers=color:green|label:A|${
              trip.departure.latitude
            }, ${trip.departure.longitude}&markers=color:blue|label:B|${
              trip.arrival.latitude
            }, ${trip.arrival.longitude}&path=color:0x0000ff80|weight:1|${
              trip.arrival.latitude
            }, ${trip.arrival.longitude}|${trip.departure.latitude}, ${
              trip.departure.longitude
            }`}
            style={{ width: "100%", height: "100%" }}
          />
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
                <img
                  className="seats-icon"
                  src="<c:url value='/static/images/seats.png' />"
                />
                {trip.available_seats} {t("search.item.available")}
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default connect(state => ({ user: state.user }))(Search);
