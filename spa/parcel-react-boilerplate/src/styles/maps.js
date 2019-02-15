import React from 'react';
import css from 'styled-jsx/css';

export default css`
	.pac-container {
		width: 250px !important;
		margin-top: 20px;
		margin-left: -20px;
		border-radius: 5px;
		border: 2px solid #c5c5c5;
		box-shadow: none;
	}

	.pac-container:after {
		display: none;
	}

	.pac-item {
		padding: 2px 10px;
	}

	.pac-item .pac-icon {
		display: none;
	}
`;
