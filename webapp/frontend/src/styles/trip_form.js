import css from 'styled-jsx/css';

export default css`
	.cost-field::before {
		position: absolute;
		content: '$';
		color: #545454;
		top: 16px;
		font-size: 18px;
		left: 10px;
	}

	.cost-field {
		position: relative;
	}

	.cost-field .field {
		padding-left: 25px;
	}

	:global(div#stars),
	div#stars {
		margin: 10px 0;
		align-self: flex-end;
		padding: 0;
	}
`;
