import React from 'react';
import { useTranslation } from 'react-i18next';
import Item from '../../components/Item';
import poolListCss from '../../styles/pool_list';
// <meta charset="UTF-8">
// <title><spring:message code="trip.individual.page_title"/></title>
// <link href="<c:url value='/static/css/css.css' />" rel="stylesheet" type="text/css" />
// <link href="<c:url value='/static/css/pool_list.css' />" rel="stylesheet" type="text/css" />
const Single = ({ trips }) => {
	const { t, i18n } = useTranslation();

	// TODO:
	trips = [{ id: 123, driver: { first_name: 'juan' } }];
	return (
		<div className="list-container">
			<style jsx>{poolListCss}</style>
			{trips.length > 0 && (
				<React.Fragment>
					<span className="list-subtitle">{t('trip.individual.title')}</span>
					{trips.map(trip => <Item trip={trip} key={trip.id} />)}
				</React.Fragment>
			)}
		</div>
	);
};

export default Single;
