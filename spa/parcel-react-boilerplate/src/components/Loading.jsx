import React from 'react';
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { connect } from 'react-redux';
import logo from '../../images/logo.png';

const Loading = () => {
	return (
		<div>
			<iframe
				src="https://giphy.com/embed/3oEjI6SIIHBdRxXI40"
				width="480"
				height="480"
				frameBorder="0"
				class="giphy-embed"
				allowFullScreen
			/>
			<p>
				<a href="https://giphy.com/gifs/mashable-3oEjI6SIIHBdRxXI40">
					via GIPHY
				</a>
			</p>{' '}
		</div>
	);
};
export default Loading;
