import React from 'react';
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { connect } from 'react-redux';
import logo from '../../images/logo.png';

const Navbar2 = ({ user }) => {
	const { t, i18n } = useTranslation();
	const isLogged = !!user;
	return (
		<div className="navbar orange-border">
			<div className="top-section flex align-center">
				<Link to="/">
					<img width="100" src={logo} alt="" />
				</Link>
				<div className="actions">
					{(!user || !user.id) && (
						<Link to="/user/register" className="create-account bold m-r-10">
							{t('common.navbar.create')}
						</Link>
					)}
					{user &&
						user.id && (
							<Link to="/logout" className="create-account bold m-r-10">
								{t('common.navbar.logout')}
							</Link>
						)}
					{(!user || !user.id) && (
						<Link to="/user/login" className="login-button">
							{t('common.navbar.login')}
						</Link>
					)}
					{user &&
						user.id && (
							<Link to={`/user/profile/${user.id}`} className="login-button">
								{t('common.navbar.profile')}
							</Link>
						)}
				</div>
			</div>
		</div>
	);
};

export default connect(({ user }) => ({ user }))(Navbar2);
