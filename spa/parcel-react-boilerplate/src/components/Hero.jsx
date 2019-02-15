import React from 'react';
import { useTranslation } from 'react-i18next';
import reviewItemCss from '../styles/review_item';
const Hero = ({ user, hero_message }) => {
	const { t, i18n } = useTranslation();

	return (
		<React.Fragment>
			<style jsx>{reviewItemCss}</style>
			<div className="profile-hero-container">
				<div className="profile-hero-alignment">
					<img
						width="100"
						height="100"
						src={`https://ui-avatars.com/api/?rounded=true&size=200&background=e36f4a&color=fff&name=${
							user.first_name
						} ${user.last_name}`}
						alt=""
					/>
					<div className="profile-user-container">
						<span className="profile-user-name">{user.first_name}</span>
						{user.rating >= 0 && (
							<span className={`stars-${user.rating}-white`} />
						)}
						<span className="profile-user-created">
							{t('common.hero.title', { '0': user.days_since_creation })}
						</span>
					</div>
				</div>
			</div>
			<div className="profile-hero-catchphrase">
				<span>{hero_message}</span>
			</div>
			<div className="profile-hero-border" />
		</React.Fragment>
	);
};

export default Hero;
