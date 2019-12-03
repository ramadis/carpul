import React, { useState } from "react";
import { useTranslation } from "react-i18next";
import { connect } from "react-redux";
import MDSpinner from "react-md-spinner";
import DatePicker from "react-datepicker";
import { addHours } from "date-fns";

import Hero from "../../components/Hero";
import PlacesAutocomplete from "../../components/PlacesAutocomplete";

import { formatCity, getMap } from "../../services/Places.js";

import "react-datepicker/dist/react-datepicker.css";
import profileHeroCss from "../../styles/profile_hero";
import poolListCss from "../../styles/pool_list";
import profileCss from "../../styles/profile";
import reviewItemCss from "../../styles/review_item";

//TODO

// <script src="https://maps.google.com/maps/api/js?key=AIzaSyCKIU4-Ijaeex54obPySJ7kXLwLnrV5BRA"></script>
// <script src="<c:url value='/static/js/gmaps.js' />" charset="utf-8"></script>
// <script src="<c:url value='/static/js/datetime.component.js' />" charset="utf-8"></script>

function Add({ user }) {
  const { t, i18n } = useTranslation();
  const [ETD, setETD] = useState();
  const [ETA, setETA] = useState();
  const [from, setFrom] = useState({});
  const [to, setTo] = useState({});
  const isLoading = !user;

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

      <Hero user={user} hero_message={t("trip.add.hero")} />

      <div className="profile-form-container flex-center">
        <form className="new-trip-form">
          <h3>{t("trip.add.title")}</h3>
          <h2>{t("trip.add.subtitle")}</h2>

          <img src={getMap([from, to])} />

          <div className="field-container">
            <label path="from_city" className="field-label" htmlFor="from_city">
              {t("trip.add.from_city")}
            </label>

            <PlacesAutocomplete
              value={from.city}
              handleSelect={place =>
                setFrom({
                  city: formatCity(place),
                  position: { latitude: place.lat, longitude: place.lon },
                  raw: place,
                })
              }
            >
              <input
                required={true}
                value={from.city}
                onChange={e => setFrom({ city: e.target.value })}
                className="field"
                type="text"
                name="from_city"
              />
            </PlacesAutocomplete>

            <label path="to_city" className="field-label" htmlFor="to_city">
              {t("trip.add.to_city")}
            </label>
            <PlacesAutocomplete
              value={to.city}
              handleSelect={place =>
                setTo({
                  city: formatCity(place),
                  position: { latitude: place.lat, longitude: place.lon },
                  raw: place,
                })
              }
            >
              <input
                required={true}
                value={to.city}
                onChange={e => setTo({ city: e.target.value })}
                className="field"
                type="text"
                name="from_city"
              />
            </PlacesAutocomplete>
            <label path="seats" className="field-label" htmlFor="seats">
              {t("trip.add.seats")}
            </label>
            <input
              required={true}
              min="1"
              max="20"
              className="field"
              path="seats"
              type="number"
              name="seats"
            />

            <label path="cost" className="field-label" htmlFor="cost">
              {t("trip.add.cost")}
            </label>
            <span className="cost-field">
              <input
                required={true}
                min="0"
                max="10000"
                className="field"
                path="cost"
                type="number"
                name="cost"
              />
            </span>

            <label path="etd" className="field-label" htmlFor="etd">
              {t("trip.add.etd")}
            </label>
            <DatePicker
              selected={ETD}
              onChange={date => setETD(date)}
              showTimeSelect
              timeFormat={t("trip.add.time")}
              todayButton={t("trip.add.today")}
              timeCaption="time"
              minDate={new Date()}
              timeIntervals={15}
              customInput={
                <input required={true} className="field" name="etd_mask" />
              }
              dateFormat={t("trip.add.timestamp")}
            />
            <input
              readOnly={true}
              required={true}
              value={ETD}
              className="field hide"
              type="number"
              name="etd"
            />

            <label path="eta" className="field-label" htmlFor="eta">
              {t("trip.add.eta")}
            </label>
            <DatePicker
              selected={ETA}
              onChange={date => setETA(date)}
              showTimeSelect
              timeFormat={t("trip.add.time")}
              todayButton={t("trip.add.today")}
              timeCaption="time"
              minDate={new Date()}
              timeIntervals={15}
              customInput={
                <input required={true} className="field" name="eta_mask" />
              }
              dateFormat={t("trip.add.timestamp")}
            />
            <input
              required={true}
              value={ETA}
              className="field hide"
              type="number"
              name="eta"
            />
          </div>

          <div className="actions" style={{ marginBottom: 10 }}>
            <button type="submit" className="login-button">
              {t("trip.add.submit")}
            </button>
          </div>
        </form>
      </div>
    </React.Fragment>
  );
}

export default connect(state => ({ user: state.user }))(Add);
