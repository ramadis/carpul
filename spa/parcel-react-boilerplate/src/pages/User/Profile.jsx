import React, { useEffect, useState, Fragment } from "react";
import {
  BrowserRouter as Router,
  Route,
  Link,
  Redirect,
  useParams,
} from "react-router-dom";
import styled from "styled-components";
import { useTranslation } from "react-i18next";
import { connect } from "react-redux";
import MDSpinner from "react-md-spinner";

import profileHeroCss from "../../styles/profile_hero";
import poolListCss from "../../styles/pool_list";
import profileCss from "../../styles/profile";
import reviewItemCss from "../../styles/review_item";

import Hero from "../../components/Hero";
import ReviewItem from "../../components/ReviewItem";
import HistoryItem from "../../components/HistoryItem";
import Loading from "../../components/Loading";

import TripPast from "./TripPast";
import Trip from "./Trip";
import Destiny from "./Reservation";

import { getProfileById } from "../../services/User";
import { getReservationsByUser } from "../../services/Reservation";
import { getHistoryByUser } from "../../services/History";
import { getReviewsByUser } from "../../services/Review";
import { getTripsByUser } from "../../services/Trip";

// function unreserve(id) {
//   var confirmate = confirm('Are you sure you want to unreserve this trip?');
//   if (confirmate) {
//     $.post('../trip/' + id + '/unreserve');
//     location.reload();
//   }
// }

const ProfileContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
`;

const setData = setter => data => setter({ data, loading: false });

const webDataInitial = { data: [], loading: true };

const Profile = ({ token, hero_message, loggedUser, dispatch }) => {
  const { t } = useTranslation();
  const [reviews, setReviews] = useState({ data: [], loading: true });
  const [trips, setTrips] = useState({ data: [], loading: true });
  const [reservations, setReservations] = useState({ data: [], loading: true });
  const [histories, setHistories] = useState({ data: [], loading: true });

  const [user, setUser] = useState(null);
  const { userId } = useParams();

  const isLogged = !!token;
  const isLoadingUser = user === null;

  // Yeah, `userId` is a string........
  const isOwnProfile = userId === `${loggedUser.id}`;

  const areAllEmpty =
    !reviews.loading &&
    !trips.loading &&
    !reservations.loading &&
    !histories.loading &&
    reviews.data.length === 0 &&
    trips.data.length === 0 &&
    reservations.data.length === 0 &&
    histories.data.length === 0;

  useEffect(() => {
    const fetchUsers = async () => {
      getProfileById(userId).then(setUser);
      getReservationsByUser(userId).then(setData(setReservations));
      getHistoryByUser(userId).then(setData(setHistories));
      getReviewsByUser(userId).then(setData(setReviews));
      getTripsByUser(userId).then(setData(setTrips));
    };

    setUser(null);
    setReservations(webDataInitial);
    setHistories(webDataInitial);
    setReviews(webDataInitial);
    setTrips(webDataInitial);
    fetchUsers();
  }, [userId]);

  useEffect(() => {
    if (user) {
      window.document.title = `Carpul | ${user.first_name} ${
        user.last_name
      } is awesome`;
    } else {
      window.document.title = `Carpul | Loading user...`;
    }
  }, [user]);

  return (
    <React.Fragment>
      <style jsx>{poolListCss}</style>
      <style jsx>{profileCss}</style>
      <style jsx>{reviewItemCss}</style>
      <style jsx>{profileHeroCss}</style>
      {isLoadingUser ? (
        <div className="flex-center spinner-class">
          <MDSpinner size={36} />
        </div>
      ) : (
        <React.Fragment>
          <Hero
            user={user}
            hero_message={t("user.profile.hero")}
            editable={isOwnProfile}
          />

          {areAllEmpty ? (
            <EmptyProfile />
          ) : (
            <ProfileContainer className="profile-container">
              <ReviewsSection reviews={reviews} />
              {isOwnProfile && <HistoriesSection histories={histories} />}
              {isOwnProfile && (
                <ReservationsSection reservations={reservations} />
              )}
              <TripsSection isOwnProfile={isOwnProfile} trips={trips} />
            </ProfileContainer>
          )}
        </React.Fragment>
      )}
    </React.Fragment>
  );
};

const ProfileSection = styled.section`
  box-sizing: border-box;
  min-width: 370px;
  padding: 20px;
  padding-top: 40px;
`;

const SectionHeader = styled.h3`
  font-size: 25px;
  font-weight: 100;
  margin: 0;
  color: rgba(0, 0, 0, 0.8);
  margin-bottom: 30px;

  ${({ empty }) =>
    empty &&
    `margin-top: 30px;
	font-size: 20px;
  color: #a0a0a0;`}
`;

const List = styled.ul`
  list-style-type: none;
  padding: 0;
  margin-top: 40px;
`;
const ReviewsSection = ({ reviews }) => {
  const { t } = useTranslation();
  const { data, loading } = reviews;

  return (
    <ProfileSection>
      {loading ? (
        <MDSpinner size={36} />
      ) : data.length === 0 ? (
        <SectionHeader empty>{t("user.profile.empty_review")}</SectionHeader>
      ) : (
        <React.Fragment>
          <SectionHeader>{t("user.profile.reviews")}</SectionHeader>

          <List>
            {data.map(review => (
              <ReviewItem review={review} key={review.id} />
            ))}
          </List>
        </React.Fragment>
      )}
    </ProfileSection>
  );
};

const HistoriesSection = ({ histories }) => {
  const { t } = useTranslation();

  return (
    <ProfileSection>
      {histories.loading ? (
        <MDSpinner size={36} />
      ) : histories.data.length === 0 ? (
        <SectionHeader>{t("user.profile.empty_histories")}</SectionHeader>
      ) : (
        <React.Fragment>
          <SectionHeader>{t("user.profile.history")}</SectionHeader>
          <List>
            {histories.data.map(history => (
              <HistoryItem history={history} key={history.id} />
            ))}
          </List>
        </React.Fragment>
      )}
    </ProfileSection>
  );
};

const ReservationsSection = ({ reservations }) => {
  const { t } = useTranslation();
  const { data, loading } = reservations;

  return (
    <ProfileSection>
      <SectionHeader>{t("user.profile.next")}</SectionHeader>

      <Link className="no-margin login-button" to="/">
        {t("user.profile.find")}
      </Link>

      {loading ? (
        <MDSpinner size={36} />
      ) : data.length === 0 ? (
        <SectionHeader empty>
          {t("user.profile.empty_reservations")}
        </SectionHeader>
      ) : (
        <List>
          {data.map(reservation =>
            reservation.expired ? (
              <TripPast reservation={reservation} key={reservation.id} />
            ) : (
              <Destiny trip={reservation} key={reservation.id} />
            )
          )}
        </List>
      )}
    </ProfileSection>
  );
};

const TripsSection = ({ trips, isOwnProfile }) => {
  const { t } = useTranslation();
  const { data, loading } = trips;

  return (
    <ProfileSection>
      <SectionHeader>{t("user.profile.trips")}</SectionHeader>

      {isOwnProfile && (
        <Link className="no-margin login-button" to="/trips/add">
          {t("user.profile.new")}
        </Link>
      )}

      {loading ? (
        <MDSpinner size={36} />
      ) : data.length > 0 ? (
        <List>
          {data.map(trip => (
            <Trip trip={trip} key={trip.id} />
          ))}
        </List>
      ) : (
        <SectionHeader empty>{t("user.profile.empty_trips")}</SectionHeader>
      )}
    </ProfileSection>
  );
};

const EmptyProfileContainer = styled.div`
  text-align: center;
  width: 100%;
  margin-top: 20px;
`;

const EmptyProfile = () => {
  const { t } = useTranslation();

  return (
    <ProfileContainer>
      <EmptyProfileContainer>
        <SectionHeader empty>{t("user.profile.empty_title")}</SectionHeader>
        <h4 className="empty-subtitle">{t("user.profile.empty_subtitle")}</h4>
        <Link className="login-button empty-button" to="/trips/add">
          {t("user.profile.empty_new")}
        </Link>
        <Link className="login-button empty-button" to="/">
          {t("user.profile.empty_find")}
        </Link>
      </EmptyProfileContainer>
    </ProfileContainer>
  );
};
export default connect(({ user, reservations, token }) => ({
  loggedUser: user,
  reservations,
  token,
}))(Profile);
