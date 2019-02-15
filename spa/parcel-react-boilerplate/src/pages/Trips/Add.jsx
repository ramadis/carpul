import React, { useState } from 'react';
import Hero from '../../components/Hero';
import { useTranslation } from 'react-i18next';
import tripFormCss from '../../styles/trip_form';
import profileCss from '../../styles/profile';
import poolListCss from '../../styles/pool_list';
//TODO

// <script src="https://maps.google.com/maps/api/js?key=AIzaSyCKIU4-Ijaeex54obPySJ7kXLwLnrV5BRA"></script>
// <script src="<c:url value='/static/js/gmaps.js' />" charset="utf-8"></script>
// <script src="<c:url value='/static/js/datetime.component.js' />" charset="utf-8"></script>

function Add({ user, reservation }) {
	//TODO:
	user = {};
	const { t, i18n } = useTranslation();
	return (
		<React.Fragment>
			<style jsx>{tripFormCss}</style>
			<style jsx>{profileCss}</style>
			<style jsx>{poolListCss}</style>
			<Hero user={user} hero_message={t('trip.add.hero')} />

			<div className="profile-form-container flex-center">
				<form method="post" className="new-trip-form" action="trip">
					<h3>{t('trip.add.title')}</h3>
					<h2>{t('trip.add.subtitle')}</h2>

					<div
						id="map"
						style={{ width: '100%', height: '400px', margin: '10px 0' }}
					/>

					<div className="field-container">
						<label path="from_city" className="field-label" htmlFor="from_city">
							{t('trip.add.from_city')}
						</label>
						<input
							required={true}
							readOnly={true}
							className="field off-field"
							path="from_city"
							type="text"
							name="from_city"
						/>
						<input
							required={true}
							readOnly={true}
							className="field hide"
							path="etd_latitude"
							type="text"
							name="etd_latitude"
						/>
						<input
							required={true}
							readOnly={true}
							className="field hide"
							path="etd_longitude"
							type="text"
							name="etd_longitude"
						/>

						<label path="to_city" className="field-label" htmlFor="to_city">
							{t('trip.add.to_city')}
						</label>
						<input
							required={true}
							readOnly={true}
							className="field off-field"
							path="to_city"
							type="text"
							name="to_city"
						/>
						<input
							required={true}
							readOnly={true}
							className="field hide"
							path="eta_latitude"
							type="text"
							name="eta_latitude"
						/>
						<input
							required={true}
							readOnly={true}
							className="field hide"
							path="eta_longitude"
							type="text"
							name="eta_longitude"
						/>

						<label path="seats" className="field-label" htmlFor="seats">
							{t('trip.add.seats')}
						</label>
						<input
							required={true}
							min="1"
							max="20"
							className="field"
							path="seats"
							type="number"
							name="seats"
						/>

						<label path="cost" className="field-label" htmlFor="cost">
							{t('trip.add.cost')}
						</label>
						<span className="cost-field">
							<input
								required={true}
								min="0"
								max="10000"
								className="field"
								path="cost"
								type="number"
								name="cost"
							/>
						</span>

						<label path="etd" className="field-label" htmlFor="etd">
							{t('trip.add.edt')}
						</label>
						<input
							required
							id="etd"
							readOnly={true}
							required={true}
							className="field"
							type="text"
							name="etd_mask"
						/>
						<input
							required={true}
							min="1"
							max="20"
							className="field hide"
							path="etd"
							type="text"
							name="etd"
						/>

						<label path="eta" className="field-label" htmlFor="eta">
							{t('trip.add.eta')}
						</label>
						<input
							required={true}
							readOnly={true}
							id="eta"
							className="field"
							type="text"
							name="eta_mask"
						/>
						<input
							required={true}
							min="1"
							max="20"
							className="field hide"
							path="eta"
							type="text"
							name="eta"
						/>
					</div>

					<div className="actions" style={{ marginBottom: 10 }}>
						<button type="submit" className="login-button">
							{t('trip.add.submit')}
						</button>
					</div>
				</form>
			</div>
		</React.Fragment>
	);
}
//TODO
// <script src="<c:url value='/static/js/map.js' />" charset="utf-8"></script>
// <script src="<c:url value='/static/js/time.js' />" charset="utf-8"></script>
export default Add;
