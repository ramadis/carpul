import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import api from '../../api';

const Register = () => {
	const [first_name, setFirstName] = useState('');
	const [last_name, setLastName] = useState('');
	const [phone_number, setPhone] = useState('');
	const [username, setUsername] = useState('');
	const [password, setPassword] = useState('');
	const { t, i18n } = useTranslation();

	const register = () => {
		const url = '/users';
		api.post(url, { first_name, last_name, phone_number, username, password });
	};
	return (
		<div className="flex-center full-height">
			<div className="user-form">
				<div className="top-border" />
				<div className="text-container">
					<span>carpul</span>
					<span className="catchphrase">{t('user.register.title')}</span>
					<span className="catchphrase-description">
						{t('user.register.subtitle')}
					</span>
				</div>

				<div className="field-container">
					<label path="first_name" className="field-label" htmlFor="first_name">
						{t('user.register.first_name')}
					</label>
					<input
						required={true}
						className="field"
						path="first_name"
						type="text"
						name="first_name"
					/>

					<label path="last_name" className="field-label" htmlFor="last_name">
						{t('user.register.last_name')}
					</label>
					<input
						required={true}
						className="field"
						path="last_name"
						type="text"
						name="last_name"
					/>

					<label
						path="phone_number"
						className="field-label"
						htmlFor="phone_number"
					>
						{t('user.register.phone_number')}
					</label>
					<input
						required={true}
						className="field"
						path="phone_number"
						type="text"
						name="phone_number"
					/>

					<label path="username" className="field-label" htmlFor="username">
						{t('user.register.username')}
					</label>
					<input
						required={true}
						className="field"
						name="username"
						path="username"
						type="email"
						value={username}
						onChange={e => setUsername(e.target.value)}
					/>

					<label path="password" className="field-label" htmlFor="password">
						{t('user.register.password')}
					</label>
					<input
						required={true}
						className="field"
						pattern=".{6,100}"
						path="password"
						type="password"
						name="password"
						value={password}
						onChange={e => setPassword(e.target.value)}
					/>
				</div>

				<div className="actions">
					<Link to="/user/login" className="create-account">
						{t('user.register.login')}
					</Link>
					<button type="submit" className="login-button" onClick={register}>
						{t('user.register.submit')}
					</button>
				</div>
			</div>
		</div>
	);
};

export default Register;
