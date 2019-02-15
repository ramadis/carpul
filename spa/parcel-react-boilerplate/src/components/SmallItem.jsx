import React from 'react';
import { useTranslation } from 'react-i18next';
import { format } from 'date-fns';

// TODO
// <c:set var="url" value="" />

const SmallItem = ({ user, trip, hero_message }) => {
	const { t, i18n } = useTranslation();

	return (
		<div class="pool-item flex-center">
			<div class="pool-info small-pool-info">
				<div class="header-container">
					<span class="bold header">
						{trip.from_city} {t('home.index.small_to')} {trip.to_city}
					</span>
				</div>

				<div>
					{t('home.index.leave_on')}
					<span class="bold">{format(trip.etd, 'DD/MM/YYYY')}</span>
					{t('search.item.at')}
					<span class="bold">{format(trip.etd, 'HH:mm')}</span>
				</div>

				<div class="">
					{t('home.index.just_price')}
					<span class="bold">${trip.cost}</span>
				</div>
				{trip.reserved ? (
					<form
						class="inline-block CTA"
						method="post"
						action="${url}trip/${trip.id}/unreserve"
					>
						<button class="login-button main-color">
							{t('search.item.unreserve')}
						</button>
					</form>
				) : (
					<form
						class="inline-block CTA"
						method="post"
						action="${url}trip/${trip.id}/reserve"
					>
						<button class="login-button">{t('search.item.reserve')}</button>
					</form>
				)}
			</div>
		</div>
	);
};

export default SmallItem;
