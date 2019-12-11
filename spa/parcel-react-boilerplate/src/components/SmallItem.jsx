import React, { Fragment, useState } from "react";
import { useTranslation } from "react-i18next";
import { format } from "date-fns";
import styled from "styled-components";
import MDSpinner from "react-md-spinner";
import { useHistory, Link } from "react-router-dom";
import { reserveByTrip, unreserveByTrip } from "../services/Reservation";
import { confirmAlert } from "react-confirm-alert";
import { NotificationManager } from "react-notifications";

import ConfirmationModal from "../components/ConfirmationModal";

import { routes } from "../App";

import { requestCatch } from "../utils/fetch";

import "react-confirm-alert/src/react-confirm-alert.css";
import profileHeroCss from "../styles/profile_hero";
import poolListCss from "../styles/pool_list";
import profileCss from "../styles/profile";
import reviewItemCss from "../styles/review_item";

const ItemContainer = styled.div`
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  flex-direction: column;
  width: 300px;
  min-height: 275px;
  text-align: center;
  color: white;
  padding: 20px;
  background: url("../images/cabin.jpg");
  background-size: cover;
  background-color: rgba(0, 0, 0, 0.2);
  background-blend-mode: darken;
  background-position: center;
  border-radius: 5px;
`;

const HeaderContainer = styled.div`
  margin-bottom: 10px;
`;

const Header = styled.h4`
  font-weight: 700;
  font-size: 30px;
  margin: 0;
`;

const SmallItem = ({ user, trip, hero_message }) => {
  const { t, i18n } = useTranslation();
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
      backgroundImage: `url(https://picsum.photos/seed/${seed}/200/300)`,
    };
  };

  return (
    <Fragment>
      <style jsx>{poolListCss}</style>
      <style jsx>{profileCss}</style>
      <style jsx>{reviewItemCss}</style>
      <style jsx>{profileHeroCss}</style>

      <div className="pool-item flex-center" style={getStyle()}>
        <ItemContainer>
          <HeaderContainer>
            <Header>
              {trip.from_city} {t("home.index.small_to")} {trip.to_city}
            </Header>
            <p style={{ margin: 0 }}>
              Driven by{" "}
              <Link
                to={routes.profile(trip.driver.id)}
                style={{ fontWeight: 600 }}
              >
                {trip.driver.first_name}
              </Link>
            </p>
          </HeaderContainer>

          <div>
            {t("home.index.leave_on")}
            <span className="bold"> {format(trip.etd, "DD/MM/YYYY")} </span>
            {t("search.item.at")}
            <span className="bold"> {format(trip.etd, "HH:mm")} </span>
          </div>

          <div className="">
            {t("home.index.just_price")}
            <span className="bold"> ${trip.cost}</span>
          </div>
          {trip.reserved ? (
            <button
              className="inline-block CTA login-button main-color"
              style={{ marginLeft: 0, marginTop: 10, marginBottom: 5 }}
              disabled={requestLoading}
              onClick={() => {
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
              }}
            >
              {requestLoading ? (
                <MDSpinner size={16} />
              ) : (
                t("search.item.unreserve")
              )}
            </button>
          ) : (
            <button
              className="inline-block CTA login-button"
              onClick={reserve}
              style={{ marginLeft: 0, marginTop: 10, marginBottom: 5 }}
              disabled={requestLoading}
            >
              {requestLoading ? (
                <MDSpinner size={16} />
              ) : (
                t("search.item.reserve")
              )}
            </button>
          )}
          <Link style={{ fontWeight: 600 }} to={routes.trip(trip.id)}>
            Share
          </Link>
        </ItemContainer>
      </div>
    </Fragment>
  );
};

export default SmallItem;
