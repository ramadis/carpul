import React, { useState } from 'react';
// import profileCss from '../../styles/profile';
import Hero from '../components/Hero';
import { useTranslation } from 'react-i18next';
import { connect } from 'react-redux';

const Review = ({ reservation, user }) => {
	const { t, i18n } = useTranslation();

	return (
		<React.Fragment>
			<link
				href="<c:url value='/static/css/pool_list.css' />"
				rel="stylesheet"
				type="text/css"
			/>
			<link
				href="<c:url value='/static/css/profile_hero.css' />"
				rel="stylesheet"
				type="text/css"
			/>
			<link
				href="<c:url value='/static/css/profile.css' />"
				rel="stylesheet"
				type="text/css"
			/>
			<link
				href="<c:url value='/static/css/trip-form.css' />"
				rel="stylesheet"
				type="text/css"
			/>
			<link
				rel="stylesheet"
				href="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.css"
			/>

			<script
				src="https://code.jquery.com/jquery-3.2.1.min.js"
				integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
				crossorigin="anonymous"
			/>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.js" />
			<script
				src="<c:url value='/static/js/review_stars.js' />"
				charset="utf-8"
			/>
			<Hero hero_message={t('review.add.hero')} user={user} />

			<div class="profile-form-container flex-center">
				<form
					class="new-trip-form"
					modelAttribute="reviewForm"
					action="../review/${trip.id}"
				>
					<h3>{t('review.add.title')}</h3>
					<h2>
						{t('review.add.subtitle', {
							'0': user.first_name,
							'1': reviewed.first_name,
							'2': trip.from_city,
							'3': trip.to_city
						})}
					</h2>

					<div class="field-container">
						<label path="message" class="field-label" for="message">
							{t('review.add.message')}
						</label>
						<div id="stars" class="stars" />
						<input
							required="required"
							class="field hide"
							name="stars"
							value="0"
							readonly="true"
							path="stars"
							min="0"
							max="5"
							type="number"
						/>
						<textarea
							class="field"
							required="true"
							multiline="true"
							name="message"
							path="message"
							type="text"
						/>
						<errors path="message" class="form-error" element="p" />
					</div>

					<div class="actions">
						<button type="submit" class="login-button">
							{t('review.add.submit')}
						</button>
					</div>
				</form>
			</div>
		</React.Fragment>
	);
};

export default connect(state => ({ user: state.user }))(Review);
