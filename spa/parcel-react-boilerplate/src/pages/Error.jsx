import React from 'react';
import styled from 'styled-components';
import { BrowserRouter as Router, Route, Link } from 'react-router-dom';
import fetchData from '../enhancers/fetchData';
import errorCss from '../styles/error';
import { useTranslation } from 'react-i18next';

const Error = ({ match }) => {
	const { t, i18n } = useTranslation();

	return (
		<div className="flex-center full-height is-col">
			<style jsx>{errorCss}</style>
			<h1 className="error-title">{t('error.base.title')}</h1>
			<h1 className="error-subtitle">
				{t('error.base.subtitle', { '0': match.params.code || 404 })}
			</h1>
		</div>
	);
};

export default Error;
