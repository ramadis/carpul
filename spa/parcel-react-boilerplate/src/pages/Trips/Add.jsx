import React, { useState } from "react";
import { useTranslation } from "react-i18next";
import { connect } from "react-redux";
import { addHours } from "date-fns";
import MDSpinner from "react-md-spinner";
import DatePicker from "react-datepicker";
import useForm from "react-hook-form";

import Hero from "../../components/Hero";
import PlacesAutocomplete from "../../components/PlacesAutocomplete";

import { formatCity, getMap } from "../../services/Places.js";

import "react-datepicker/dist/react-datepicker.css";
import profileHeroCss from "../../styles/profile_hero";
import poolListCss from "../../styles/pool_list";
import profileCss from "../../styles/profile";
import reviewItemCss from "../../styles/review_item";

function Add({ user }) {
  const { t, i18n } = useTranslation();
  const [ETD, setETD] = useState();
  const [ETA, setETA] = useState();
  const [from, setFrom] = useState({});
  const { handleSubmit, register, errors } = useForm({ mode: "onChange" });
  const [to, setTo] = useState({});

  const onSubmit = values => {
    console.log(values);
  };

  console.log(errors);

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
        <form onSubmit={handleSubmit(onSubmit)} className="new-trip-form">
          <h3>{t("trip.add.title")}</h3>
          <h2>{t("trip.add.subtitle")}</h2>

          <img src={getMap([from, to])} />

          <div className="field-container">
            <label className="field-label" htmlFor="from">
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
                value={from.city}
                onChange={e => setFrom({ city: e.target.value })}
                className={`field ${errors.from && "error"}`}
                type="text"
                ref={register({ required: "error" })}
                name="from"
              />
            </PlacesAutocomplete>
            <label className="field-label" htmlFor="to">
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
                className={`field ${errors.to && "error"}`}
                ref={register({ required: "error" })}
                type="text"
                name="to"
              />
            </PlacesAutocomplete>
            <label className="field-label" htmlFor="seats">
              {t("trip.add.seats")}
            </label>
            <input
              className={`field ${errors.seats && "error"}`}
              ref={register({ required: "error", min: 1, max: 20 })}
              type="number"
              name="seats"
            />
            {errors.seats && errors.seats.type === "max" ? (
              <label className="label-error">
                The amount of seats should be smaller than 20
              </label>
            ) : null}
            {errors.seats && errors.seats.type === "min" ? (
              <label className="label-error">
                The amount of seats should be greater than 1
              </label>
            ) : null}

            <label className="field-label" htmlFor="cost">
              {t("trip.add.cost")}
            </label>
            <input
              ref={register({ required: "error", min: 0, max: 10000 })}
              className={`field ${errors.cost && "error"}`}
              type="number"
              name="cost"
            />
            {errors.cost && errors.cost.type === "max" ? (
              <label className="label-error">
                The cost of your trip should be smaller than $10.000
              </label>
            ) : null}
            {errors.cost && errors.cost.type === "min" ? (
              <label className="label-error">
                The cost of your trip should be greater than $0
              </label>
            ) : null}

            <label className="field-label" htmlFor="etd">
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
                <input
                  className={`field ${errors.etd && "error"}`}
                  ref={register({ required: "error" })}
                  name="etd"
                />
              }
              dateFormat={t("trip.add.timestamp")}
            />

            <label className="field-label" htmlFor="eta">
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
                <input
                  required={true}
                  ref={register({ required: "error" })}
                  className={`field ${errors.eta && "error"}`}
                  name="eta"
                />
              }
              dateFormat={t("trip.add.timestamp")}
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
