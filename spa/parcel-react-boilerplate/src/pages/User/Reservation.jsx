import React, { useState, useEffect } from "react";
import { withTranslation } from "react-i18next";
import { Link, useParams } from "react-router-dom";
import { format } from "date-fns";

import { cancelTrip } from "../../services/Trip";
import { cancelReservation } from "../../services/Reservation";

import profileHeroCss from "../../styles/profile_hero";
import poolListCss from "../../styles/pool_list";
import profileCss from "../../styles/profile";
import reviewItemCss from "../../styles/review_item";

const Reservation = ({ t, trip }) => {
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
        <li className="destiny-item trip-item" style={{ width: "auto" }}>
          <div className="inline-block no-margin">
            <span className="destiny-cost">
              {t("reservation.cost")}
              <span className="bold" style={{ display: "inline" }}>
                ${trip.cost * trip.occupied_seats}
              </span>
            </span>
            <div className="destiny-name">{trip.to_city}</div>
            <div className="destiny-time">From {trip.from_city}</div>
            <div className="destiny-timetable">
              <div className="destiny-timerow">
                <div className="destiny-time-titlespan">
                  {t("user.trip.depart_single")}
                </div>
                <div>{fmtetddate}</div>
                <div className="destiny-time-span">{fmtetdtime}</div>
              </div>
              <div className="destiny-timerow">
                <div className="destiny-time-titlespan">
                  {t("user.trip.arrive_single")}
                </div>
                <div>{fmtetadate}</div>
                <div className="destiny-time-span">{fmtetatime}</div>
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
            <hr />
            <a href={`/user/${trip.driver.id}`}>
              <div href={`/user/${trip.driver.id}`}>
                <div className="driver">
                  <div className="driver-item-data">
                    <img
                      width="50"
                      height="50"
                      src={`https://ui-avatars.com/api/?rounded=true&size=150&background=e36f4a&color=fff&name=${
                        trip.driver.first_name
                      } ${trip.driver.last_name}`}
                      alt=""
                    />
                    <div className="driver-info">
                      <span className="driver-name">
                        {trip.driver.first_name} {trip.driver.last_name}
                      </span>
                      <span>{t("reservation.learn")}</span>
                    </div>
                  </div>
                </div>
              </div>
            </a>
          </div>
        </li>
      </React.Fragment>
    </React.Fragment>
  );
};

export default withTranslation()(Reservation);
