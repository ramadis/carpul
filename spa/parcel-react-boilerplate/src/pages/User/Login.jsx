import React, { useState } from 'react';
import { Link, Redirect } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { connect } from 'react-redux';
import api from '../../api';

//create your forceUpdate hook
function useForceUpdate() {
	const [value, set] = useState(true); //boolean state
	return () => set(!value); // toggle the state to force render
}

const urlEncode = ({ username, password }) => {
	const params = new URLSearchParams();
	params.append('username', username);
	params.append('password', password);
	return params;
};

const Login = ({ dispatch, user }) => {
	const forceUpdate = useForceUpdate();
	const [t, i18n] = useTranslation();
	const [username, setUsername] = useState('rolivera+carpul@itba.edu.ar');
	const [password, setPassword] = useState('carpulcarpul');

	const login = () => {
		const url = '/login';

		api({
			url,
			method: 'POST',
			headers: { 'content-type': 'application/x-www-form-urlencoded' },
			data: urlEncode({ username, password })
		})
			.then(res => {
				const token = res.headers.authorization;
				dispatch({ type: 'LOGIN', token });

				localStorage.setItem('token', token);
				return api.get('/users/1');
			})
			.then(res => {
				dispatch({ type: 'USER_LOADED', user: res.data });
				forceUpdate();
			});
	};

	console.log('User:', user);
	const isLogged = !!user;
	return isLogged ? (
		<Redirect to={`/user/profile/${user.id}`} />
	) : (
		<div className="flex-center full-height">
			<div className="user-form">
				<div className="top-border" />
				<div className="text-container">
					<span>carpul</span>
					<span className="catchphrase">{t('user.login.title')}</span>
					<span className="catchphrase-description">
						{t('user.login.subtitle1')}
					</span>
					<span className="catchphrase-description">
						{t('user.login.subtitle2')}
					</span>
				</div>
				<div className="field-container">
					<label path="username" className="field-label" htmlFor="username">
						{t('user.login.username')}
					</label>
					<input
						required={true}
						className="field"
						path="username"
						type="text"
						name="username"
						value={username}
						onChange={e => setUsername(e.target.value)}
					/>

					<label path="password" className="field-label" htmlFor="password">
						{t('user.login.password')}
					</label>
					<input
						required={true}
						className="field"
						path="password"
						type="password"
						name="password"
						value={password}
						onChange={e => setPassword(e.target.value)}
					/>
				</div>
				<div className="actions">
					<Link to="/user/register" className="create-account">
						{t('user.login.create')}
					</Link>
					<button type="submit" className="login-button" onClick={login}>
						{t('user.login.submit')}
					</button>
				</div>
			</div>
		</div>
	);
};

export default connect(state => ({ user: state.user }))(Login);
