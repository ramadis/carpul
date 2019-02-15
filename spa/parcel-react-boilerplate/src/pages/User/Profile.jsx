import React, { useEffect, useState } from 'react';
import {
	BrowserRouter as Router,
	Route,
	Link,
	Redirect
} from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import profileHeroCss from '../../styles/profile_hero';
import poolListCss from '../../styles/pool_list';
import profileCss from '../../styles/profile';
import reviewItemCss from '../../styles/review_item';
import Hero from '../../components/Hero';
import ReviewItem from '../../components/ReviewItem';
import HistoryItem from '../../components/HistoryItem';
import Loading from '../../components/Loading';
import TripPast from './TripPast';
import { connect } from 'react-redux';
import MDSpinner from 'react-md-spinner';
import api from '../../api';
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
const Profile = ({
	user = { first_name: 'Pepe', rating: 4 },
	reservations = [],
	trips = [],
	reviews = [],
	histories = [],
	hero_message,
	dispatch
}) => {
	const { t, i18n } = useTranslation();

	const isLogged = !!user;
	const isLoading = !user || !reservations;

	useEffect(() => {
		if (isLogged) {
			api
				.get(`/users/${user.id}/reservations`)
				.then(res =>
					dispatch({ type: 'RESERVATIONS_LOADED', reservations: res.data })
				);
		}
	});

	if (!isLogged) {
		return <Redirect to="/user/login" />;
	}
	return isLoading ? (
		<MDSpinner />
	) : (
		<React.Fragment>
			<style jsx>{poolListCss}</style>
			<style jsx>{profileCss}</style>
			<style jsx>{reviewItemCss}</style>
			<style jsx>{profileHeroCss}</style>
			<Hero user={user} hero_message={t('user.profile.hero')} />

			<section className="profile-container">
				{(reservations.length > 0 ||
					trips.length > 0 ||
					reviews.length > 0 ||
					histories.length > 0) && (
					<React.Fragment>
						<section className="reviews-container">
							{reviews.length === 0 ? (
								<h3>{t('user.profile.empty_review')}</h3>
							) : (
								<React.Fragment>
									<h3>{t('user.profile.reviews')}</h3>

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
								<h3>{t('user.profile.empty_histories')}</h3>
							) : (
								<React.Fragment>
									<h3>{t('user.profile.history')}</h3>

									<ul className="no-bullets destiny-list">
										{histories.map(history => (
											<HistoryItem history={history} />
										))}
									</ul>
								</React.Fragment>
							)}
						</section>
						<section className="destinys-container">
							<h3>{t('user.profile.next')}</h3>

							<Link className="no-margin login-button" to="/">
								{t('user.profile.find')}
							</Link>

							{reservations.length > 0 ? (
								<ul className="no-bullets destiny-list">
									{reservations.map(
										reservation =>
											reservation.expired ? (
												<TripPast />
											) : (
												<Destiny reservation={reservation} />
											)
									)}
								</ul>
							) : (
								<h3 className="empty-message">
									{t('user.profile.empty_reservations')}
								</h3>
							)}
						</section>
						<section className="destinys-container">
							<h3>{t('user.profile.trips')}</h3>

							<Link className="no-margin login-button" to="/user/trip">
								{t('user.profile.new')}
							</Link>

							{trips.length > 0 ? (
								<ul className="no-bullets destiny-list">
									{trips.map(trip => <Trip trip={trip} key={trip.id} />)}
								</ul>
							) : (
								<h3 className="empty-message">
									{t('user.profile.empty_trips')}
								</h3>
							)}
						</section>{' '}
					</React.Fragment>
				)}

				{reservations.length === 0 &&
				trips.length === 0 &&
				histories.length === 0 &&
				reviews.length === 0 ? (
					<div className="empty-profile">
						<h3 className="empty-title">{t('user.profile.empty_title')}</h3>
						<h4 className="empty-subtitle">
							{t('user.profile.empty_subtitle')}
						</h4>
						<Link
							className="no-margin login-button empty-button"
							to="/user/trip"
						>
							{t('user.profile.empty_new')}
						</Link>
						<Link className="no-margin login-button empty-button" to="/">
							{t('user.profile.empty_find')}
						</Link>
					</div>
				) : null}
			</section>
		</React.Fragment>
	);
};

export default connect(({ user, reservations }) => ({ user, reservations }))(
	Profile
);
