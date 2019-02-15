import React from 'react';
import { useTranslation } from 'react-i18next';
import { format } from 'date-fns';

const HistoryItem = ({ review }) => {
	const { t, i18n } = useTranslation();
	const fmtetd = format(history.trip.etd, 'DD/MM/YYYY');
	const fmtdate = format(history.created.time, 'DD/MM/YYYY HH:mm');
	return (
		<li class="review-item-container">
			<img
				width="50"
				height="50"
				src="https://ui-avatars.com/api/?rounded=true&size=200&background=e36f4a&color=fff&name=${history.related.first_name} ${history.related.last_name}"
				alt=""
			/>
			<div class="review-item-content history-content">
				{history.type === 'RESERVE' && (
					<span class="review-message">
						{t('history.item.message', {
							'0': history.related.first_name,
							'1': t('history.item.reserved'),
							'2': history.trip.to_city,
							'3': fmtetd,
							'4': t('history.item.all_set')
						})}
					</span>
				)}
				{history.type === 'UNRESERVE' && (
					<span class="review-message">
						{t('history.item.message', {
							'0': history.related.first_name,
							'1': t('history.item.unreserved'),
							'2': history.trip.to_city,
							'3': fmtetd,
							'4': t('history.item.bummer')
						})}
					</span>
				)}
				{history.type === DELETE && (
					<span class="review-message">
						{t('history.item.deleted_message', {
							'0': history.trip.to_city,
							'1': fmtetd,
							'2': t('history.item.just_deleted')
						})}
					</span>
				)}
				{history.type === 'KICKED' && (
					<span class="review-message">
						{t('history.item.kicked_message', {
							'0': history.trip.to_city,
							'1': fmtetd,
							'2': t('history.item.just_deleted')
						})}
					</span>
				)}
				<span class="review-meta">
					{t('history.item.happened', { '0': fmtetd })}
				</span>
			</div>
		</li>
	);
};

export default HistoryItem;
