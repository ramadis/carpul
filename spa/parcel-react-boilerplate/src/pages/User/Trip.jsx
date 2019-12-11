import React, { useState, useEffect } from "react";
import { withTranslation } from "react-i18next";
import { Link, useParams, useHistory } from "react-router-dom";
import { format } from "date-fns";
import { useTranslation } from "react-i18next";
import styled from "styled-components";
import { confirmAlert } from "react-confirm-alert";
import MDSpinner from "react-md-spinner";

import ConfirmationModal from "../../components/ConfirmationModal";
import { routes } from "../../App";

import { cancelTrip } from "../../services/Trip";
import { cancelReservation } from "../../services/Reservation";
import { reserveByTrip, unreserveByTrip } from "../../services/Reservation";

import imgDelete from "../../../images/delete.png";
import profileHeroCss from "../../styles/profile_hero";
import poolListCss from "../../styles/pool_list";
import profileCss from "../../styles/profile";
import reviewItemCss from "../../styles/review_item";

const EarningSection = ({ trip }) => {
  const { t } = useTranslation();

  return trip.occupied_seats === 0 ? (
    <span className="destiny-cost">
      <span>{t("user.trip.earning")} </span>
      <span className="bold" style={{ display: "inline" }}>
        {t("user.trip.nil")}
      </span>
    </span>
  ) : (
    <span className="destiny-cost">
      <span>{t("user.trip.earning")} </span>
      <span className="bold" style={{ display: "inline" }}>
        ${trip.cost * trip.occupied_seats}
      </span>
    </span>
  );
};

export const Button = styled.button`
  position: absolute;
  top: 10;
  right: 10;
  padding: 5px 10px;
  border-radius: 5px;
  font-weight: 900;
  color: white;
  background: transparent;
  transition: 0.1s ease-out background;
  border: none;
  outline: none;
  font-size: 16px;
  cursor: pointer;

  &:hover {
    background-color: #e36f4a;
  }
`;

const DeleteButton = styled.button`
  background: transparent;
  border: none;
  cursor: pointer;
`;

const DeleteTripButton = ({ tripId }) => {
  const { t } = useTranslation();

  return (
    <Button onClick={() => cancelTrip(tripId)}>{t("user.trip.delete")}</Button>
  );
};

const PassengerList = ({ trip }) => {
  const { t } = useTranslation();

  const askCancel = passenger => () =>
    confirmAlert({
      customUI: ConfirmationModal([
        {
          danger: true,
          label: t("reservation.cancel.confirmation.unreserve"),
          onClick: () => cancelReservation(passenger.id, trip.id),
        },
        {
          label: t("reservation.cancel.confirmation.cancel"),
          onClick: () => null,
        },
      ]),
      title: t("reservation.cancel.confirmation.title", {
        passenger: passenger.first_name,
      }),
      message: t("reservation.cancel.confirmation.subtitle"),
    });

  return trip.passengers.length === 0 ? null : (
    <React.Fragment>
      <style jsx>{profileCss}</style>
      <hr />
      {trip.passengers.map(passenger => (
        <div key={passenger.id} href={`/user/${passenger.id}`}>
          <div className="driver">
            <div className="driver-item-data">
              <a href={`/user/${passenger.id}`}>
                <img
                  width="50"
                  height="50"
                  className="profile-image"
                  src={
                    passenger.image ||
                    `https://ui-avatars.com/api/?rounded=true&size=150&background=e36f4a&color=fff&name=${
                      passenger.first_name
                    } ${passenger.last_name}`
                  }
                  alt=""
                />
                <div className="driver-info">
                  <span className="driver-name">
                    {passenger.first_name} {passenger.last_name}
                  </span>
                  <span>{passenger.phone_number}</span>
                </div>
              </a>
            </div>
            <DeleteButton
              onClick={askCancel(passenger)}
              type="button"
              className="kick-hitchhiker"
            >
              <img src={imgDelete} height="20px" width="20px" alt="" />
            </DeleteButton>
          </div>
        </div>
      ))}
    </React.Fragment>
  );
};

const CostInfoContainer = styled.div`
  margin-top: 15px;
`;
const CostInfo = ({ trip }) => {
  const { t } = useTranslation();

  return (
    <span className="destiny-cost">
      <span>{t("reservation.cost")} </span>
      <span className="bold" style={{ display: "inline" }}>
        ${trip.cost}
      </span>
    </span>
  );
};
const ReserveButtonStyle = styled.button`
  width: 120px;
  padding: 10px 0;
  font-size: 16px;
  background: #6cd298;
  text-decoration: none;
  cursor: pointer;
  border: 0;
  border-radius: 3px;
  transition: 0.2s ease-out background;
  color: white;

  display: block;
  margin: 10px auto 0;

  &:hover {
    background: #e36f4a;
  }

  ${({ unreserve }) => unreserve && `background: #e36f4a`};
`;

const ReserveButton = ({ trip }) => {
  const { t } = useTranslation();
  const [requestLoading, setRequestLoading] = useState(false);
  const history = useHistory();

  const reserve = async () => {
    setRequestLoading(true);
    await reserveByTrip(trip.id);
    history.push(routes.reservedTrip(trip.id));
  };

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

  return trip.reserved ? (
    <ReserveButtonStyle
      disabled={requestLoading}
      onClick={askUnreserve}
      unreserve
    >
      {requestLoading ? <MDSpinner size={16} /> : t("search.item.unreserve")}
    </ReserveButtonStyle>
  ) : (
    <ReserveButtonStyle disabled={requestLoading} onClick={reserve}>
      {requestLoading ? <MDSpinner size={16} /> : t("search.item.reserve")}
    </ReserveButtonStyle>
  );
};
const Trip = ({ trip, isOwner }) => {
  const { t } = useTranslation();
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
        <li className="destiny-item trip-item">
          <div className="inline-block no-margin">
            {isOwner ? (
              <EarningSection trip={trip} />
            ) : (
              <CostInfo trip={trip} />
            )}
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
            {isOwner && <DeleteTripButton tripId={trip.id} />}
            {isOwner && <PassengerList trip={trip} />}
            {!isOwner && <ReserveButton trip={trip} />}
          </div>
        </li>
      </React.Fragment>
    </React.Fragment>
  );
};

export default withTranslation()(Trip);
