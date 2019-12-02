import React, { useEffect, useState } from "react";
import {
  BrowserRouter as Router,
  Route,
  Link,
  Redirect,
  useParams
} from "react-router-dom";
import { useTranslation } from "react-i18next";
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
import Destiny from "./TripPast";
import { connect } from "react-redux";
import MDSpinner from "react-md-spinner";
import api from "../../api";

import { getProfileById } from "../../services/User";
import { getReservationsByUser } from "../../services/Reservation";
import { getHistoryByUser } from "../../services/History";
import { getReviewsByUser } from "../../services/Review";
import { getTripsByUser } from "../../services/Trip";

// function deleteTrip(id) {
//   var confirmate = confirm('Are you sure you want to delete this trip?');
//   if (confirmate) {
//     $.post('../trip/' + id + '/delete');
//     location.reload();
//   }
// }
//
// function kickout(id, tripId) {
//   var confirmate = confirm('Are you sure you want to remove this passenger from you trip?');
//   if (confirmate) {
//     $.post('../trip/' + tripId + '/unreserve/' + id);
//     location.reload();
//   }
// }
//
// function unreserve(id) {
//   var confirmate = confirm('Are you sure you want to unreserve this trip?');
//   if (confirmate) {
//     $.post('../trip/' + id + '/unreserve');
//     location.reload();
//   }
// }
const Profile = ({ token, hero_message, loggedUser, dispatch }) => {
  const { t, i18n } = useTranslation();
  const [reviews, setReviews] = useState([]);
  const [trips, setTrips] = useState([]);
  const [reservations, setReservations] = useState([]);
  const [histories, setHistories] = useState([]);
  const [user, setUser] = useState(null);
  const { userId } = useParams();
  const isLogged = !!token;
  const isLoading = !user || !reservations || !histories || !reviews || !trips;

  useEffect(() => {
    const fetchUsers = async () => {
      getProfileById(userId).then(setUser);
      getReservationsByUser(userId).then(setReservations);
      getHistoryByUser(userId).then(setHistories);
      getReviewsByUser(userId).then(setReviews);
      getTripsByUser(userId).then(setTrips);
    };
    fetchUsers();
  }, []);

  if (user) {
    window.document.title = `Carpul | ${user.first_name} ${
      user.last_name
    } is awesome`;
  }

  return (
    <React.Fragment>
      <style jsx>{poolListCss}</style>
      <style jsx>{profileCss}</style>
      <style jsx>{reviewItemCss}</style>
      <style jsx>{profileHeroCss}</style>
      {isLoading ? (
        <div className="flex-center spinner-class">
          <MDSpinner size={36} />
        </div>
      ) : (
        <React.Fragment>
          <Hero
            user={user}
            hero_message={t("user.profile.hero")}
            editable={user.id === loggedUser.id}
          />

          <section className="profile-container">
            {(reservations.length > 0 ||
              trips.length > 0 ||
              reviews.length > 0 ||
              histories.length > 0) && (
              <React.Fragment>
                <section className="reviews-container">
                  {reviews.length === 0 ? (
                    <h3>{t("user.profile.empty_review")}</h3>
                  ) : (
                    <React.Fragment>
                      <h3>{t("user.profile.reviews")}</h3>

                      <ul className="no-bullets destiny-list">
                        {reviews.map(review => (
                          <ReviewItem review={review} key={review.id} />
                        ))}
                      </ul>
                    </React.Fragment>
                  )}
                </section>
                <section className="destinys-container">
                  {histories.length === 0 ? (
                    <h3>{t("user.profile.empty_histories")}</h3>
                  ) : (
                    <React.Fragment>
                      <h3>{t("user.profile.history")}</h3>

                      <ul className="no-bullets destiny-list">
                        {histories.map(history => (
                          <HistoryItem history={history} key={history.id} />
                        ))}
                      </ul>
                    </React.Fragment>
                  )}
                </section>
                <section className="destinys-container">
                  <h3>{t("user.profile.next")}</h3>

                  <Link className="no-margin login-button" to="/">
                    {t("user.profile.find")}
                  </Link>

                  {reservations.length > 0 ? (
                    <ul className="no-bullets destiny-list">
                      {reservations.map(reservation =>
                        reservation.expired ? (
                          <TripPast
                            reservation={reservation}
                            key={reservation.id}
                          />
                        ) : (
                          <Destiny
                            reservation={reservation}
                            key={reservation.id}
                          />
                        )
                      )}
                    </ul>
                  ) : (
                    <h3 className="empty-message">
                      {t("user.profile.empty_reservations")}
                    </h3>
                  )}
                </section>
                <section className="destinys-container">
                  <h3>{t("user.profile.trips")}</h3>

                  <Link className="no-margin login-button" to="/user/trip">
                    {t("user.profile.new")}
                  </Link>

                  {trips.length > 0 ? (
                    <ul className="no-bullets destiny-list">
                      {trips.map(trip => (
                        <Trip trip={trip} key={trip.id} />
                      ))}
                    </ul>
                  ) : (
                    <h3 className="empty-message">
                      {t("user.profile.empty_trips")}
                    </h3>
                  )}
                </section>{" "}
              </React.Fragment>
            )}

            {reservations.length === 0 &&
            trips.length === 0 &&
            histories.length === 0 &&
            reviews.length === 0 ? (
              <div className="empty-profile">
                <h3 className="empty-title">{t("user.profile.empty_title")}</h3>
                <h4 className="empty-subtitle">
                  {t("user.profile.empty_subtitle")}
                </h4>
                <Link
                  className="no-margin login-button empty-button"
                  to="/user/trip"
                >
                  {t("user.profile.empty_new")}
                </Link>
                <Link className="no-margin login-button empty-button" to="/">
                  {t("user.profile.empty_find")}
                </Link>
              </div>
            ) : null}
          </section>
        </React.Fragment>
      )}
    </React.Fragment>
  );
};

export default connect(({ user, reservations, token }) => ({
  loggedUser: user,
  reservations,
  token
}))(Profile);
