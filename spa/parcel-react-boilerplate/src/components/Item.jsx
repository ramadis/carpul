import React from 'react';
import { useTranslation } from 'react-i18next';
import { format } from 'date-fns';

// <span className="user-rating">
//   <img src="<c:url value='/static/images/star.png' />"/>
//   <img src="<c:url value='/static/images/star.png' />"/>
//   <img src="<c:url value='/static/images/star.png' />"/>
//   <img src="<c:url value='/static/images/star.png' />"/>
//   <img src="<c:url value='/static/images/star.png' />"/>
// </span>
const Item = ({ user, trip, hero_message, is_searching }) => {
	const { t, i18n } = useTranslation();
	// TODO:
	const url = is_searching ? '../' : '';
	return (
		<React.Fragment>
			<div className="pool-item flex-center">
				<div className="user-info flex space-around align-center column h-150">
					<div className="user-image">
						<img
							src="https://ui-avatars.com/api/?rounded=true&size=85&background=e36f4a&color=fff&name=${trip.driver.first_name} ${trip.driver.last_name}"
							alt=""
						/>
					</div>
					<div className="user-name"> {trip.driver.first_name} </div>
				</div>

				<div className="pool-info">
					<div className="map-container">
						<img
							src="https://maps.googleapis.com/maps/api/staticmap?key=AIzaSyCKIU4-Ijaeex54obPySJ7kXLwLnrV5BRA&size=1200x200&markers=color:green|label:A|${trip.departure_lat}, ${trip.departure_lon}&markers=color:blue|label:B|${trip.arrival_lat}, ${trip.arrival_lon}&path=color:0x0000ff80|weight:1|${trip.arrival_lat}, ${trip.arrival_lon}|${trip.departure_lat}, ${trip.departure_lon}"
							style={{ width: '100%', height: '100%' }}
						/>
					</div>
					<div className="bg-white">
						<div className="price-container flex space-between align-center">
							<span className="clear gray sz-13">
								{t('search.item.from')}
								<span className="bold black">{trip.from_city}</span>
								{t('search.item.on_low')}
								<span className="bold black">
									{format(trip.etd, 'DD/MM/YYYY')}{' '}
								</span>
								{t('search.item.at')}
								<span className="bold black">{format(trip.etd, 'HH:mm')}</span>
								<br />
								{t('search.item.arrive')}
								<span className="bold black"> ${trip.to_city}</span>
								{t('search.item.on_low')}
								<span className="bold black">{format(trip.eta, 'HH:mm')}</span>
								{t('search.item.at')}
								<span className="bold black">{format(trip.eta, 'HH:mm')}</span>
							</span>
							<div>
								<span className="price gray">
									<span className="bold black">${trip.cost}</span>
									{t('search.item.each')}
								</span>
								{trip.reserved ? (
									<form
										className="inline-block"
										method="post"
										action="${url}trip/${trip.id}/unreserve"
									>
										<button className="login-button main-color">
											{t('search.item.unreserve')}
										</button>
									</form>
								) : (
									<form
										className="inline-block"
										method="post"
										action="${url}trip/${trip.id}/reserve"
									>
										<button className="login-button">
											{t('search.item.reserve')}
										</button>
									</form>
								)}
							</div>
						</div>
						<div className="pool-features flex space-between align-center">
							<div className="features-container"> </div>
							<div className="seats-container">
								<span className="seats bold gray">
									<img
										className="seats-icon"
										src="<c:url value='/static/images/seats.png' />"
									/>
									{trip.available_seats} {t('search.item.available')}
								</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</React.Fragment>
	);
};

export default Item;
