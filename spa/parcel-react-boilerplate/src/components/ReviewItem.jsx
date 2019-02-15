import React from 'react';
import { useTranslation } from 'react-i18next';
import { format } from 'date-fns';

const Review = ({ review }) => {
	const { t, i18n } = useTranslation();
	return (
		<li class="review-item-container">
			<img
				width="75"
				height="75"
				src="https://ui-avatars.com/api/?rounded=true&size=200&background=e36f4a&color=fff&name=${review.owner.first_name} ${review.owner.last_name}"
				alt=""
			/>
			<div class="review-item-content">
				{review.stars === 0 ? (
					<span class="review-meta">{t('review.item.no_stars')}</span>
				) : (
					<span class={`stars-${review.stars}`} />
				)}
				<span class="review-message">${review.message}</span>
				<span class="review-meta">
					{t('review.item.from')}{' '}
					<span class="bold inline">${review.trip.from_city}</span>{' '}
					{t('review.item.to')}{' '}
					<span class="bold inline">${review.trip.to_city}</span>
				</span>
			</div>
		</li>
	);
};

export default Review;
