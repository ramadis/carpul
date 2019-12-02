import React, { useState, useEffect } from "react";
import { withTranslation } from "react-i18next";
import { Link, useParams } from "react-router-dom";
import { format } from "date-fns";

import profileHeroCss from "../../styles/profile_hero";
import poolListCss from "../../styles/pool_list";
import profileCss from "../../styles/profile";
import reviewItemCss from "../../styles/review_item";

// TODO:
const deleteTrip = () => {};
const kickPassenger = () => {};

const Trip = ({ t, trip }) => {
  const fmtetddate = format(trip.etd, "DD/MM/YYYY");
  const fmtetdtime = format(trip.etd, "HH:mm");
  const fmtetadate = format(trip.eta, "DD/MM/YYYY");
  const fmtetatime = format(trip.eta, "HH:mm");

  return (
    <React.Fragment>
      <style jsx>{poolListCss}</style>
      <style jsx>{profileCss}</style>
      <style jsx>{reviewItemCss}</style>
      <style jsx>{profileHeroCss}</style>
      <React.Fragment>
        <li className="destiny-item trip-item" data-id="${trip.id}">
          <div className="inline-block no-margin">
            {trip.occupied_seats === 0 ? (
              <span className="destiny-cost">
                {t("user.trip.earning")}
                <span className="bold" style={{ display: "inline" }}>
                  {t("user.trip.nil")}
                </span>
              </span>
            ) : (
              <span className="destiny-cost">
                {t("user.trip.earning")}
                <span className="bold" style={{ display: "inline" }}>
                  ${trip.cost * trip.occupied_seats}
                </span>
              </span>
            )}
            <span className="destiny-name">{trip.to_city}</span>
            <span className="destiny-time">{trip.from_city}</span>
            <div className="destiny-timetable">
              <div className="destiny-timerow">
                <span className="destiny-time-titlespan">
                  {t("user.trip.depart_single")}
                </span>
                <span>{fmtetddate}</span>
                <span className="destiny-time-span">{fmtetdtime}</span>
              </div>
              <div className="destiny-timerow">
                <span className="destiny-time-titlespan">
                  {t("user.trip.arrive_single")}
                </span>
                <span>{fmtetadate}</span>
                <span className="destiny-time-span">{fmtetatime}</span>
              </div>
            </div>
            <a
              className="destiny-time map-trigger"
              target="iframe"
              href={`https://www.google.com/maps/embed/v1/directions?key=AIzaSyCNS1Xx_AGiNgyperC3ovLBiTdsMlwnuZU&origin=${
                trip.departure.latitude
              }, ${trip.departure.longitude}&destination=${
                trip.arrival.latitude
              }, ${trip.arrival.longitude}`}
            >
              {t("user.trip.map")}
            </a>
            <button
              className="destiny-unreserve-button"
              onClick={() => deleteTrip(trip.id)}
            >
              {t("user.trip.delete")}
            </button>
            {trip.passengers.length > 0 ? <hr /> : null}
            {trip.passengers.map(passenger => (
              <a href={`/user/${passenger.id}`}>
                <div className="driver">
                  <div className="driver-item-data">
                    <img
                      width="50"
                      height="50"
                      src={`https://ui-avatars.com/api/?rounded=true&size=150&background=e36f4a&color=fff&name=${
                        passenger.first_name
                      } ${passenger.last_name}`}
                      alt=""
                    />
                    <div className="driver-info">
                      <span className="driver-name">
                        {passenger.first_name} {passenger.last_name}
                      </span>
                      <span>{passenger.phone_number}</span>
                    </div>
                  </div>
                  <a
                    onClick={kickPassenger}
                    type="button"
                    className="kick-hitchhiker"
                    href="#"
                  >
                    <img
                      src="/static/images/delete.png"
                      height="20px"
                      width="20px"
                      alt=""
                    />
                  </a>
                </div>
              </a>
            ))}
          </div>
        </li>
      </React.Fragment>
    </React.Fragment>
  );
};

export default withTranslation()(Trip);
