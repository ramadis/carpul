import css from 'styled-jsx/css';

export default css`
	.profile-hero-border {
		background: linear-gradient(to right, #77de5f 0%, #61c8ca 100%);
		width: 100%;
		height: 3px;
	}

	.profile-user-created {
		margin-top: 5px;
	}

	.profile-hero-alignment {
		display: flex;
		align-items: flex-start;
	}

	.profile-hero-container {
		width: 100%;
		height: 150px;
		display: flex;
		align-items: center;
		background-image: url('../images/hero-profile.jpg');
		background-size: cover;
		padding-left: 25px;
	}

	.profile-user-container {
		color: white;
		margin-top: 10px;
		margin-left: 30px;
		display: flex;
		flex-direction: column;
	}

	.profile-user-name {
		font-weight: 900;
		font-size: 20px;
	}

	.profile-hero-catchphrase {
		width: 100%;
		padding: 15px;
		background: white;
		padding-left: 30px;
		font-size: 20px;
		color: #949494;
	}
`;
