import React, { useState, useEffect } from "react";
import { withTranslation } from "react-i18next";
import { Link, useParams, useHistory } from "react-router-dom";
import { format } from "date-fns";
import MDSpinner from "react-md-spinner";
import { confirmAlert } from "react-confirm-alert";

import { unreserveByTrip } from "../../services/Reservation";

import ConfirmationModal from "../../components/ConfirmationModal";
import { Button } from "./Trip";
import { routes } from "../../App";

import profileHeroCss from "../../styles/profile_hero";
import poolListCss from "../../styles/pool_list";
import profileCss from "../../styles/profile";
import reviewItemCss from "../../styles/review_item";

const Reservation = ({ t, trip, editable }) => {
  const fmtetddate = format(trip.etd, "DD/MM/YYYY");
  const fmtetdtime = format(trip.etd, "HH:mm");
  const fmtetadate = format(trip.eta, "DD/MM/YYYY");
  const fmtetatime = format(trip.eta, "HH:mm");
  const [requestLoading, setRequestLoading] = useState(false);
  const history = useHistory();

  const unreserve = async () => {
    setRequestLoading(true);
    await unreserveByTrip(trip.id);
    history.push(routes.unreservedTrip(trip.id));
  };

  const askUnreserve = () =>
    confirmAlert({
      customUI: ConfirmationModal([
        {
          danger: true,
          label: t("reservation.unreserve.confirmation.unreserve"),
          onClick: unreserve,
        },
        {
          label: t("reservation.unreserve.confirmation.cancel"),
          onClick: () => null,
        },
      ]),
      title: t("reservation.unreserve.confirmation.title"),
      message: t("reservation.unreserve.confirmation.subtitle"),
    });

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
                ${trip.cost}
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
                  <div className="driver-item-data flex-center">
                    <img
                      width="50"
                      height="50"
                      className="profile-image"
                      src={
                        trip.driver.image ||
                        `https://ui-avatars.com/api/?rounded=true&size=150&background=e36f4a&color=fff&name=${
                          trip.driver.first_name
                        } ${trip.driver.last_name}`
                      }
                      alt=""
                    />
                    <div className="driver-info">
                      <div className="driver-name">
                        {trip.driver.first_name} {trip.driver.last_name}
                      </div>
                      <div>{t("reservation.learn")}</div>
                    </div>
                  </div>
                </div>
              </div>
            </a>
            {editable ? (
              <Button onClick={askUnreserve}>
                {requestLoading ? <MDSpinner size={16} /> : "Unreserve"}
              </Button>
            ) : null}
          </div>
        </li>
      </React.Fragment>
    </React.Fragment>
  );
};

export default withTranslation()(Reservation);
