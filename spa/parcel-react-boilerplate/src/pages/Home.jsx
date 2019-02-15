// <link href="<c:url value='/static/css/pool_list.css' />" rel="stylesheet" type="text/css" />
//
// <script src="https://maps.google.com/maps/api/js?key=AIzaSyCKIU4-Ijaeex54obPySJ7kXLwLnrV5BRA&libraries=places"></script>
// <script src="https://code.jquery.com/jquery-3.2.1.min.js" integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4=" crossorigin="anonymous"></script>
// <script src="<c:url value='/static/js/datetime.component.js' />" charset="utf-8"></script>

import React from 'react';
import styled from 'styled-components';
import { BrowserRouter as Router, Route, Link } from 'react-router-dom';
import fetchData from '../enhancers/fetchData';
import Add from './Trips/Add';
import SmallItem from '../components/SmallItem';
import { useTranslation } from 'react-i18next';
import css from 'styled-jsx/css';
import poolListCss from '../styles/pool_list';
import mapsCss from '../styles/maps';
import homeCss from '../styles/home';

const Home = ({ match, trips }) => {
	const { t, i18n } = useTranslation();
	trips = [];
	return (
		<React.Fragment>
			<style jsx>{poolListCss}</style>
			<style jsx>{mapsCss}</style>
			<style jsx>{homeCss}</style>
			<div className="home-hero-container flex-center">
				<div className="home-content-container">
					<div className="titles">
						<h1 className="title">{t('home.index.title1')}</h1>
						<h1 className="title">{t('home.index.title2')}</h1>
					</div>

					<h2 className="subtitle">{t('home.index.subtitle')}</h2>

					<form method="post" action="search">
						<div className="searchbar">
							<label path="from" className="searchbar-label" htmlFor="from">
								{t('home.index.from')}
							</label>
							<input
								className="searchbar-input"
								placeholder="Origin"
								path="from"
								type="text"
								name="from"
								tabIndex="1"
							/>

							<label path="to" className="searchbar-label" htmlFor="to">
								{t('home.index.to')}
							</label>
							<input
								className="searchbar-input"
								placeholder="Destination"
								path="to"
								type="text"
								name="to"
								tabIndex="2"
							/>

							<label path="when" className="searchbar-label" htmlFor="when">
								{t('home.index.on')}
							</label>
							<input
								readOnly
								className="searchbar-input"
								id="when"
								placeholder="Time range"
								type="text"
								tabIndex="3"
							/>
							<input
								className="searchbar-input hide"
								path="when"
								type="text"
								name="when"
							/>

							<button
								type="submit"
								disabled
								className="login-button searchbar-button"
								name="button"
								tabIndex="4"
							>
								{t('home.index.submit')}
							</button>
						</div>
					</form>
				</div>
			</div>
			<div className="trips-recommendations-container">
				{trips.length === 0 ? (
					<h1>{t('home.index.no_trips')}</h1>
				) : (
					<React.Fragment>
						<h1>{t('home.index.suggestions')}</h1>
						<div className="trip-recommendation-list">
							{trips.map(trip => <SmallItem trip={trip} />)}
						</div>
					</React.Fragment>
				)}
			</div>
		</React.Fragment>
	);
};

// TODO
// <script src="<c:url value='/static/js/search_time.js' />" charset="utf-8"></script>

export default Home;
