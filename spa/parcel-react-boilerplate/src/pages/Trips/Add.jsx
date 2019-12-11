import React, { useState } from "react";
import { useTranslation } from "react-i18next";
import { connect } from "react-redux";
import { addHours } from "date-fns";
import MDSpinner from "react-md-spinner";
import DatePicker from "react-datepicker";
import useForm from "react-hook-form";
import { useHistory } from "react-router-dom";
import { isEmpty } from "lodash";
import { NotificationManager } from "react-notifications";

import Hero from "../../components/Hero";
import PlacesAutocomplete from "../../components/PlacesAutocomplete";

import { getCity, getMap } from "../../services/Places.js";
import { createTrip } from "../../services/Trip.js";

import "react-datepicker/dist/react-datepicker.css";
import profileHeroCss from "../../styles/profile_hero";
import poolListCss from "../../styles/pool_list";
import profileCss from "../../styles/profile";
import reviewItemCss from "../../styles/review_item";

function Add({ user }) {
  const { t, i18n } = useTranslation();
  const [ETD, setETD] = useState();
  const [ETA, setETA] = useState();
  const [ETAdirty, setETAdirty] = useState(false);
  const [ETDdirty, setETDdirty] = useState(false);
  const [from, setFrom] = useState({});
  const [to, setTo] = useState({});
  const history = useHistory();
  const { handleSubmit, register, errors, triggerValidation } = useForm({
    mode: "onChange",
  });

  const onSubmit = async values => {
    if (!isEmpty(checkErrors(errors))) {
      return console.error("There are errors on the form");
    }

    const payload = {
      cost: values.cost,
      seats: values.seats,
      to_city: getCity(to.raw),
      etd: new Date(ETD).getTime(),
      eta: new Date(ETA).getTime(),
      from_city: getCity(from.raw),
      etd_latitude: Number(from.position.latitude),
      etd_longitude: Number(from.position.longitude),
      eta_latitude: Number(to.position.latitude),
      eta_longitude: Number(to.position.longitude),
    };

    try {
      await createTrip(payload);
      history.push(`/user/${user.id}`);
      setTimeout(
        () =>
          NotificationManager.success(
            "Pack your bags and get ready to meet some cool people.",
            "Your trip is ready!"
          ),
        500
      );
    } catch (error) {
      console.error(error);
      NotificationManager.error(error.message.subtitle, error.message.title);
    }
  };

  const checkErrors = err => {
    const errors = { ...err };

    if (ETDdirty && !ETD) errors.etd = { type: "required" };
    if (ETAdirty && !ETA) errors.eta = { type: "required" };
    if (ETD >= ETA) errors.eta = { type: "invalid" };
    if (ETD < new Date()) errors.etd = { type: "invalid" };

    return errors;
  };

  const formErrors = checkErrors(errors);

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
              handleSelect={place => {
                setFrom({
                  city: place.display_name,
                  position: { latitude: place.lat, longitude: place.lon },
                  selected: place.display_name,
                  raw: place,
                });
                setTimeout(() => triggerValidation({ name: "from" }), 300);
              }}
            >
              <input
                value={from.city}
                onChange={e => setFrom({ city: e.target.value })}
                className={`field ${errors.from && "error"}`}
                type="text"
                ref={register({
                  required: "error",
                  validate: { invalid: value => value === from.selected },
                })}
                name="from"
              />
            </PlacesAutocomplete>
            {errors.from && errors.from.type === "invalid" ? (
              <label className="label-error">
                {t("trip.add.errors.place.invalid")}
              </label>
            ) : null}
            <label className="field-label" htmlFor="to">
              {t("trip.add.to_city")}
            </label>
            <PlacesAutocomplete
              value={to.city}
              handleSelect={place => {
                setTo({
                  city: place.display_name,
                  position: { latitude: place.lat, longitude: place.lon },
                  raw: place,
                  selected: place.display_name,
                });
                setTimeout(() => triggerValidation({ name: "to" }), 300);
              }}
            >
              <input
                value={to.city}
                onChange={e => setTo({ city: e.target.value })}
                className={`field ${errors.to && "error"}`}
                ref={register({
                  required: "error",
                  validate: { invalid: value => value === to.selected },
                })}
                type="text"
                name="to"
              />
            </PlacesAutocomplete>
            {errors.to && errors.to.type === "invalid" ? (
              <label className="label-error">
                {t("trip.add.errors.place.invalid")}
              </label>
            ) : null}
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
                {t("trip.add.errors.seats.max")}
              </label>
            ) : null}
            {errors.seats && errors.seats.type === "min" ? (
              <label className="label-error">
                {t("trip.add.errors.seats.min")}
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
                {t("trip.add.errors.cost.max")}
              </label>
            ) : null}
            {errors.cost && errors.cost.type === "min" ? (
              <label className="label-error">
                {t("trip.add.errors.cost.min")}
              </label>
            ) : null}

            <label className="field-label" htmlFor="etd">
              {t("trip.add.etd")}
            </label>
            <DatePicker
              selected={ETD}
              onChange={date => setETD(date)}
              onBlur={() => setETDdirty(true)}
              showTimeSelect
              timeFormat={t("trip.add.time")}
              todayButton={t("trip.add.today")}
              timeCaption="time"
              minDate={new Date()}
              timeIntervals={15}
              customInput={
                <input
                  className={`field ${formErrors.etd && "error"}`}
                  name="etd"
                />
              }
              dateFormat={t("trip.add.timestamp")}
            />
            {formErrors.etd && formErrors.etd.type === "invalid" ? (
              <label className="label-error">
                {t("trip.add.errors.etd.invalid")}
              </label>
            ) : null}

            <label className="field-label" htmlFor="eta">
              {t("trip.add.eta")}
            </label>
            <DatePicker
              selected={ETA}
              onChange={date => setETA(date)}
              onBlur={() => setETAdirty(true)}
              showTimeSelect
              timeFormat={t("trip.add.time")}
              todayButton={t("trip.add.today")}
              timeCaption="time"
              minDate={new Date()}
              timeIntervals={15}
              customInput={
                <input
                  required={true}
                  className={`field ${formErrors.eta && "error"}`}
                  name="eta"
                />
              }
              dateFormat={t("trip.add.timestamp")}
            />
            {formErrors.eta && formErrors.eta.type === "invalid" ? (
              <label className="label-error">
                {t("trip.add.errors.date.invalid")}
              </label>
            ) : null}
          </div>

          <div className="actions" style={{ marginBottom: 10 }}>
            <button
              disabled={!(isEmpty(checkErrors(errors)) && ETAdirty)}
              type="submit"
              className="login-button"
            >
              {t("trip.add.submit")}
            </button>
          </div>
        </form>
      </div>
    </React.Fragment>
  );
}

export default connect(state => ({ user: state.user }))(Add);
