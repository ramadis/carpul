import React, { useState, useEffect } from "react";
import { withTranslation } from "react-i18next";
import { Link, useParams, useHistory } from "react-router-dom";
import { format } from "date-fns";
import { useTranslation } from "react-i18next";
import styled from "styled-components";
import { confirmAlert } from "react-confirm-alert";
import MDSpinner from "react-md-spinner";
import { NotificationManager } from "react-notifications";

import ConfirmationModal from "../../components/ConfirmationModal";
import { routes } from "../../App";

import { cancelTrip } from "../../services/Trip";
import { cancelReservation } from "../../services/Reservation";
import { reserveByTrip, unreserveByTrip } from "../../services/Reservation";

import { requestCatch } from "../../utils/fetch";

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

const DisabledOverlay = styled.div`
  content: "";
  position: absolute;
  z-index: 9999;
  height: 100%;
  top: 0;
  width: 100%;
  background: rgba(204, 204, 204, 0.6);
  left: 0;
  border-radius: 5px;
`;

const DeleteTripButton = ({ tripId, onUpdate }) => {
  const { t } = useTranslation();
  const [isLoading, setLoading] = useState(false);

  const askDelete = () =>
    confirmAlert({
      customUI: ConfirmationModal([
        {
          danger: true,
          label: t("user.trip.confirmation.delete"),
          onClick: () => {
            setLoading(true);
            cancelTrip(tripId)
              .then(() => {
                NotificationManager.success(
                  "All passengers have received an email letting them know",
                  "Trip cancelled successfully"
                );
                onUpdate(tripId, "delete");
              })
              .catch(requestCatch)
              .finally(() => setLoading(false));
          },
        },
        {
          label: t("user.trip.confirmation.cancel"),
          onClick: () => null,
        },
      ]),
      title: t("user.trip.confirmation.title"),
      message: t("user.trip.confirmation.subtitle"),
    });

  return (
    <Button disabled={isLoading} onClick={askDelete}>
      {isLoading ? <MDSpinner size={16} /> : t("user.trip.delete")}
    </Button>
  );
};

const PassengerList = ({ trip, onUpdate }) => {
  const { t } = useTranslation();
  const [requestLoading, setLoading] = useState(false);

  const askCancel = passenger => () =>
    confirmAlert({
      customUI: ConfirmationModal([
        {
          danger: true,
          label: t("reservation.cancel.confirmation.unreserve"),
          onClick: () => {
            setLoading(true);
            cancelReservation(passenger.id, trip.id)
              .then(() => {
                NotificationManager.success(
                  `${passenger.first_name} reservation was cancelled`,
                  "Cancellation confirmed"
                );
                const updatedTrip = {
                  ...trip,
                  passengers:
                    trip.passengers && trip.passengers.length
                      ? trip.passengers.filter(p => p.id !== passenger.id)
                      : trip.passengers,
                };
                onUpdate(updatedTrip, "unreserve");
              })
              .catch(requestCatch)
              .finally(() => setLoading(false));
          },
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

  return !trip.passengers || trip.passengers.length === 0 ? null : (
    <React.Fragment>
      <style jsx>{profileCss}</style>
      <hr />
      {trip.passengers.map(passenger => (
        <div key={passenger.id}>
          <div className="driver">
            <div className="driver-item-data">
              <Link to={routes.profile(passenger.id)}>
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
              </Link>
            </div>
            <DeleteButton
              onClick={askCancel(passenger)}
              type="button"
              disabled={requestLoading}
              className="kick-hitchhiker"
            >
              {requestLoading ? (
                <MDSpinner size={16} />
              ) : (
                <img src={imgDelete} height="20px" width="20px" alt="" />
              )}
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
      history.push(routes.unreservedTrip(trip.id));
    } catch (error) {
      requestCatch(error);
      setRequestLoading(false);
    }
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
const Trip = ({ trip, isOwner, onUpdate = () => null }) => {
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
          {trip.etd < new Date() ? <DisabledOverlay /> : null}
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
            <div>
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
              <div className="flex-center destiny-time map-trigger">
                <Link to={routes.trip(trip.id)}>Share</Link>
              </div>
            </div>
            {isOwner && (
              <DeleteTripButton onUpdate={onUpdate} tripId={trip.id} />
            )}
            {isOwner && <PassengerList onUpdate={onUpdate} trip={trip} />}
            {!isOwner && <ReserveButton trip={trip} />}
          </div>
        </li>
      </React.Fragment>
    </React.Fragment>
  );
};

export default withTranslation()(Trip);
