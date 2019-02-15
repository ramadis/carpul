import React from 'react';
import { withTranslation } from 'react-i18next';
// <fmt:formatDate value="${trip.etd}" var="fmtetddate" pattern="dd/MM/yyyy"/>
// <fmt:formatDate value="${trip.etd}" var="fmtetdtime" pattern="HH:mm"/>
// <fmt:formatDate value="${trip.eta}" var="fmtetadate" pattern="dd/MM/yyyy"/>
// <fmt:formatDate value="${trip.eta}" var="fmtetatime" pattern="HH:mm"/>

// TODO:
const deleteTrip = () => {};
const Trip = ({ t, trip }) => {
	// TODO:
	const fmtetddate = '1/1/1';
	const fmtetdtime = '10:22';
	const fmtetadate = '2/2/2';
	const fmtetatime = '11:11';
	trip = {
		passengers: [],
		occupied_seats: 2,
		cost: 1000
	};
	return (
		<li className="destiny-item trip-item" data-id="${trip.id}">
			<div className="inline-block no-margin">
				{trip.occupied_seats === 0 ? (
					<span className="destiny-cost">
						{t('user.trip.earning')}
						<span className="bold" style={{ display: 'inline' }}>
							{t('user.trip.nil')}
						</span>
					</span>
				) : (
					<span className="destiny-cost">
						{t('user.trip.earning')}
						<span className="bold" style={{ display: 'inline' }}>
							${trip.cost * trip.occupied_seats}
						</span>
					</span>
				)}
				<span className="destiny-name">{trip.to_city}</span>
				<span className="destiny-time">{trip.from_city}</span>
				<div className="destiny-timetable">
					<div className="destiny-timerow">
						<span className="destiny-time-titlespan">
							{t('user.trip.depart_single')}
						</span>
						<span>${fmtetddate}</span>
						<span className="destiny-time-span">${fmtetdtime}</span>
					</div>
					<div className="destiny-timerow">
						<span className="destiny-time-titlespan">
							{t('user.trip.arrive_single')}
						</span>
						<span>${fmtetadate}</span>
						<span className="destiny-time-span">${fmtetatime}</span>
					</div>
				</div>

				<a
					className="destiny-time map-trigger"
					target="iframe"
					href="https://www.google.com/maps/embed/v1/directions?key=AIzaSyCNS1Xx_AGiNgyperC3ovLBiTdsMlwnuZU&origin=${trip.departure_lat}, ${trip.departure_lon}&destination=${trip.arrival_lat}, ${trip.arrival_lon}"
				>
					{t('user.trip.map')}
				</a>
				<button
					className="destiny-unreserve-button"
					onClick={() => deleteTrip(trip.id)}
				>
					{t('user.trip.delete')}
				</button>
				{trip.passengers.length > 0 ? <hr /> : null}
				{trip.passengers.map(passenger => (
					<a href={`/user/${passenger.id}`}>
						<div className="driver">
							<div className="driver-item-data">
								<img
									width="50"
									height="50"
									src={`https://ui-avatars.com/api/?rounded=true&size=150&background=e36f4a&color=fff&name=${
										passenger.first_name
									} ${passenger.last_name}`}
									alt=""
								/>
								<div className="driver-info">
									<span className="driver-name">
										{passenger.first_name} {passenger.last_name}
									</span>
									<span>{passenger.phone_number}</span>
								</div>
							</div>
							<a
								onclick="kickout(${passenger.id}, ${trip.id})"
								type="button"
								class="kick-hitchhiker"
								href="#"
							>
								<img
									src="/static/images/delete.png"
									height="20px"
									width="20px"
									alt=""
								/>
							</a>
						</div>
					</a>
				))}
			</div>
		</li>
	);
};

export default withTranslation()(Trip);
