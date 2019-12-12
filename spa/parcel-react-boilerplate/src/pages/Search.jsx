import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { connect } from "react-redux";
import { format, parse, isValid } from "date-fns";
import MDSpinner from "react-md-spinner";
import Rating from "react-rating";
import DatePicker from "react-datepicker";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { debounce } from "lodash";
import { faStar } from "@fortawesome/free-solid-svg-icons";
import { confirmAlert } from "react-confirm-alert";
import { NotificationManager } from "react-notifications";
import {
  BrowserRouter as Router,
  Route,
  Switch,
  useHistory,
  Link,
  Redirect,
} from "react-router-dom";
import { useTranslation } from "react-i18next";
import { useLocation } from "react-router-dom";

import { search } from "../services/search";
import { reserveByTrip, unreserveByTrip } from "../services/Reservation";
import { getCity } from "../services/Places";

import { requestCatch } from "../utils/fetch";

import ConfirmationModal from "../components/ConfirmationModal";
import PlacesAutocomplete from "../components/PlacesAutocomplete";
import { routes } from "../App";

import "react-datepicker/dist/react-datepicker.css";
import poolListCss from "../styles/pool_list";
import profileCss from "../styles/profile";
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

const SpinnerContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 80px 0;
`;

const Spinner = () => {
  return (
    <SpinnerContainer>
      <MDSpinner size={36} />
    </SpinnerContainer>
  );
};

const TripInfoContainer = styled.div`
  width: 700px;
  background: #f4f4f4;
  border-radius: 5px;
  overflow: hidden;
`;

const Search = ({ user }) => {
  const { t, i18n } = useTranslation();
  const [trips, setTrips] = useState([]);
  const rawParams = useQuery();
  const [params, setParams] = useState(rawParams);
  const [loading, setLoading] = useState(true);
  const history = useHistory();
  const { to, from, when, page = 0 } = params;

  const handleSearch = debounce(setParams, 1000);

  useEffect(() => {
    setLoading(true);
    search({ to, from, when, page })
      .then(setTrips)
      .finally(() => setLoading(false));
  }, [params]);

  const whenDate = new Date(Number(when));

  const searchDate = format(whenDate, "DD/MM/YYYY HH:mm");
  const dateDayMonthYear = format(whenDate, "DD/MM/YYYY");
  const whenTime = format(whenDate, "HH:mm");

  if (!isValid(whenDate)) {
    history.push("/404");
    return null;
  }

  return (
    <div>
      <style jsx>{poolListCss}</style>

      <HeaderContainer>
        <SearchBar onSearch={handleSearch} />
        <Link to={routes.addTrip} className="login-button inverted hard-edges">
          {t("search.search.create")}
        </Link>
      </HeaderContainer>

      <div className="list-container" style={{ marginBottom: 20 }}>
        {loading && <Spinner />}
        {!loading && trips.length > 0 && (
          <React.Fragment>
            <TripsSubtitle
              to={to}
              from={from}
              when={dateDayMonthYear}
              time={whenTime}
            />
            {trips.map(trip => (
              <Trip trip={trip} key={trip.id} />
            ))}
          </React.Fragment>
        )}
        {!loading && trips.length === 0 && (
          <NoTripsSubtitle to={to} from={from} />
        )}
      </div>
    </div>
  );
};

const Subtitle = styled.span`
  color: #868686;
  text-align: center;
  display: block;
  margin: 20px 0;
  font-size: 20px;
`;

const tripsMessage = (t, { to, from, when, time }) => {
  if (!to && !from) {
    return t("search.search.trips_no_to_no_from", { when, time });
  }

  if (!to) return t("search.search.trips_no_to", { from, when, time });

  if (!from) return t("search.search.trips_no_from", { to, when, time });

  return t("search.search.trips", { to, from, when, time });
};

const TripsSubtitle = ({ to, from, when, time }) => {
  const { t } = useTranslation();

  return <Subtitle>{tripsMessage(t, { to, from, when, time })}</Subtitle>;
};

const noTripsMessage = (t, { to, from }) => {
  if (!to && !from) return t("search.search.no_trips_no_to_no_from");

  if (!to) return t("search.search.no_trips_no_to", { from });

  if (!from) return t("search.search.no_trips_no_from", { to });

  return t("search.search.no_trips", { to, from });
};

const NoTripsSubtitle = ({ to, from }) => {
  const { t } = useTranslation();

  return <Subtitle>{noTripsMessage(t, { to, from })}</Subtitle>;
};

const encodeQueryParams = function(obj) {
  var str = [];
  for (var p in obj) {
    if (obj.hasOwnProperty(p)) {
      str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
    }
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

const MapView = styled.iframe`
  position: absolute;
  left: 0;
  top: 0;
  height: 100%;
  width: 100%;
`;

export const Trip = ({ trip, hideShare }) => {
  const { t } = useTranslation();
  const [reserved, setReserved] = useState(false);
  const [requestLoading, setRequestLoading] = useState(false);
  const history = useHistory();

  const defaultProfile = `https://ui-avatars.com/api/?rounded=true&size=85&background=e36f4a&color=fff&name=${
    trip.driver.first_name
  } ${trip.driver.last_name}`;

  const reserve = async () => {
    setRequestLoading(true);
    try {
      await reserveByTrip(trip.id);
      history.push(routes.reservedTrip(trip.id));
    } catch (error) {
      requestCatch(error);
      setRequestLoading(false);
    }
  };

  const unreserve = async () => {
    setRequestLoading(true);
    try {
      await unreserveByTrip(trip.id);
      NotificationManager.success(
        "Continúa buscando las mejores aventuras en Carpul",
        "Viaje desreservado con éxito"
      );
      history.push(routes.unreservedTrip(trip.id));
    } catch (error) {
      requestCatch(error);
      setRequestLoading(false);
    }
  };

  const getStyle = () => {
    const seed = `${trip.to_city}`.replace(/\s/g, "");
    return {
      backgroundImage: `url(https://picsum.photos/seed/${seed}/600/300)`,
    };
  };

  return (
    <React.Fragment>
      <style jsx>{poolListCss}</style>
      <style jsx>{profileCss}</style>

      <div className="pool-item flex-center">
        <Link to={routes.profile(trip.driver.id)}>
          <div className="user-info flex space-around align-center column h-150">
            <div className="user-image">
              <img
                width="85"
                height="85"
                className="profile-image"
                src={trip.driver.image || defaultProfile}
                alt=""
              />
            </div>
            <div className="user-name" style={{ color: "black" }}>
              {trip.driver.first_name}
            </div>
            <span className="user-rating">
              <Rating
                initialRating={trip.driver.rating}
                readonly
                emptySymbol={
                  <FontAwesomeIcon icon={faStar} size="xs" color="#e2e2e2" />
                }
                fullSymbol={
                  <FontAwesomeIcon icon={faStar} size="xs" color="#f39c12" />
                }
              />
            </span>
          </div>
        </Link>

        <TripInfoContainer>
          <div className="map-container">
            <ImageContainer style={getStyle()}>
              <MapView
                src={`https://www.google.com/maps/embed/v1/directions?key=AIzaSyCNS1Xx_AGiNgyperC3ovLBiTdsMlwnuZU&origin=${
                  trip.departure.latitude
                }, ${trip.departure.longitude}&destination=${
                  trip.arrival.latitude
                }, ${trip.arrival.longitude}`}
              />
            </ImageContainer>
          </div>
          <div className="bg-white">
            <div className="price-container flex space-between align-center">
              <span className="clear gray sz-13">
                {t("search.item.from")}
                <span className="bold black"> {trip.from_city}</span>
                <span> {t("search.item.on_low")} </span>
                <span className="bold black">
                  {format(trip.etd, "DD/MM/YYYY")}
                </span>
                <span> {t("search.item.at")} </span>
                <span className="bold black">{format(trip.etd, "HH:mm")}</span>
                <span>.</span>
                <br />
                <span>{t("search.item.arrive")}</span>
                <span className="bold black"> {trip.to_city}</span>
                <span> {t("search.item.on_low")} </span>
                <span className="bold black">
                  {format(trip.eta, "DD/MM/YYYY")}
                </span>
                <span> {t("search.item.at")} </span>
                <span className="bold black">{format(trip.eta, "HH:mm")}</span>
                <span>.</span>
              </span>
              <div>
                <span className="price gray">
                  <span className="bold black">
                    ${trip.cost}/{t("search.item.each")}
                  </span>
                </span>

                {!trip.reserved && (
                  <button
                    disabled={requestLoading}
                    className="login-button inline-block"
                    onClick={reserve}
                  >
                    {requestLoading ? (
                      <MDSpinner size={16} />
                    ) : (
                      t("search.item.reserve")
                    )}
                  </button>
                )}
                {trip.reserved && (
                  <button
                    className="inline-block login-button main-color"
                    disabled={requestLoading}
                    onClick={() =>
                      confirmAlert({
                        customUI: ConfirmationModal([
                          {
                            danger: true,
                            label: t(
                              "reservation.unreserve.confirmation.unreserve"
                            ),
                            onClick: unreserve,
                          },
                          {
                            label: t(
                              "reservation.unreserve.confirmation.cancel"
                            ),
                            onClick: () => null,
                          },
                        ]),
                        title: t("reservation.unreserve.confirmation.title"),
                        message: t(
                          "reservation.unreserve.confirmation.subtitle"
                        ),
                      })
                    }
                  >
                    {requestLoading ? (
                      <MDSpinner size={16} />
                    ) : (
                      t("search.item.unreserve")
                    )}
                  </button>
                )}
              </div>
            </div>

            <div className="pool-features flex space-between align-center">
              <div className="features-container">
                {!hideShare && (
                  <Link
                    to={routes.trip(trip.id)}
                    style={{ color: "grey", fontWeight: 600 }}
                  >
                    Share
                  </Link>
                )}
              </div>
              <div className="seats-container">
                <span className="seats bold gray">
                  <img className="seats-icon" src={imgSeats} />
                  {trip.available_seats} {t("search.item.available")}
                </span>
              </div>
            </div>
          </div>
        </TripInfoContainer>
      </div>
    </React.Fragment>
  );
};
export default connect(state => ({ user: state.user }))(Search);
