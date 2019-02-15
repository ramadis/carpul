import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';

function TripPast({ user, reservation }) {
	const { t, i18n } = useTranslation();
	// TODO:
	user = { trip_past: { message: 'hola' } };
	reservation = { to_city: 'Buenos Aires', id: 123 };
	return (
		<li className="destiny-item past-item" data-id="${reservation.id}">
			<div className="inline-block no-margin">
				<span className="destiny-cost">{t('user.trip_past.message')}</span>
				<span className="destiny-name">{reservation.to_city}</span>
				<a
					href={`/review/${reservation.id}`}
					className="login-button review-button"
					name="button"
				>
					{t('user.trip_past.review')}
				</a>
			</div>
		</li>
	);
}

export default TripPast;
