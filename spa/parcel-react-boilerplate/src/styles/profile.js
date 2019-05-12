import css from 'styled-jsx/css';

export default css`
	.profile-container {
		width: 100%;
		display: flex;
		justify-content: center;
	}

	.reviews-container {
		flex-grow: 2;
	}

	.new-trip-form {
		width: 500px;
		margin-top: 20px;
	}

	.new-trip-form .actions {
		margin-top: 20px;
	}

	.new-trip-form h3 {
		font-size: 25px;
		font-weight: 100;
		margin: 0;
		color: rgba(0, 0, 0, 0.8);
		margin-bottom: 20px;
	}

	.destinys-container .empty-message,
	.review-container .empty-message {
		margin-top: 30px;
		font-size: 20px;
		color: #a0a0a0;
	}

	.new-trip-form h2 {
		font-size: 16px;
		font-weight: 100;
		margin: 0;
		color: rgba(0, 0, 0, 0.8);
		margin-bottom: 20px;
	}

	.destinys-container {
		box-sizing: border-box;
		min-width: 370px;
		padding: 20px;
		padding-top: 40px;
	}

	.reviews-container {
		padding: 20px;
		padding-top: 40px;
		padding-left: 30px;
	}

	.destinys-container h3,
	.reviews-container h3 {
		font-size: 25px;
		font-weight: 100;
		margin: 0;
		color: rgba(0, 0, 0, 0.8);
		margin-bottom: 30px;
	}

	.destiny-list {
		padding: 0;
		margin-top: 40px;
	}

	.destiny-list li span {
		display: block;
		margin-top: 2px;
	}

	.destiny-unreserve-button {
		position: absolute;
		top: 10;
		right: 10;
		padding: 5px 10px;
		border-radius: 5px;
		font-weight: 900;
		color: white;
		background: transparent;
		transition: 0.1s ease-out background;
		border: none;
		outline: none;
		font-size: 16px;
		cursor: pointer;
	}

	.destiny-unreserve-button:hover {
		background-color: #e36f4a;
	}

	.destiny-item {
		position: relative;
		display: flex;
		flex-direction: column;
		justify-content: flex-end;
		color: white;
		margin-top: 20px;
		border-radius: 10px;
		box-sizing: border-box;
		padding: 20px;
		padding-top: 40px;
		width: 20vw;
		background: url('https://static-lswnspabnh.now.sh/images/cabin.jpg');
		background-size: cover;
		background-color: rgba(0, 0, 0, 0.2);
		background-blend-mode: darken;
	}

	.past-item {
		justify-content: center;
		align-items: center;
		text-align: center;
	}

	.review-button {
		margin-top: 20px;
		display: block;
		max-width: 100%;
		cursor: pointer;
	}

	.review-button:hover {
		background-color: #e36f4a;
	}

	.trip-item {
		height: auto;
	}

	.trip-item .driver {
		margin-top: 10px;
	}

	.destiny-cost {
		font-size: 17px;
		font-weight: 100;
	}

	.destiny-time {
		font-size: 12px;
	}

	.destiny-name {
		font-size: 28px;
		font-weight: 900;
	}

	.destiny-item .driver {
		display: flex;
		align-items: center;
		justify-content: space-between;
		width: 100%;
		font-size: 13px;
		transition: 0.1s ease-in background;
	}

	.destiny-item .driver:hover {
		background: #e36f4a;
		border-radius: 5px;
	}

	.destiny-item .driver:hover .kick-hitchhiker {
		opacity: 1;
	}

	.destiny-item .driver img {
		display: inline-block;
		vertical-align: middle;
	}
	.destiny-item .driver .driver-info {
		display: inline-block;
		vertical-align: middle;
		margin-left: 10px;
	}

	.destiny-item .driver .driver-name {
		font-weight: 900;
	}

	.empty-profile {
		text-align: center;
		width: 100%;
		margin-top: 20px;
	}

	.empty-title,
	.empty-subtitle {
		font-size: 25px;
		font-weight: 100;
		margin: 0;
		color: rgba(0, 0, 0, 0.8);
	}

	.empty-subtitle {
		margin: 10px 0;
		font-size: 20px;
	}

	.empty-button {
		display: inline-block;
		margin-top: 10px;
		width: auto;
	}

	.map-trigger {
		font-weight: 900;
		font-size: 14px;
		display: inherit;
		padding: 5px 10px;
		padding-left: 0;
		margin-top: 5px;
		margin-bottom: -5px;
		text-align: center;
		border-radius: 2px;
		transition: 0.1s ease-in background;
	}

	.map-trigger:hover {
		background: #e36f4a;
	}

	.destiny-timetable {
		display: flex;
		justify-content: space-around;
		background: rgba(0, 0, 0, 0.5);
		margin: 10 0;
		margin-bottom: 0;
		box-sizing: border-box;
		padding: 10px 0;
		border-radius: 5px;
	}

	.destiny-timerow {
		text-align: center;
	}

	.destiny-timerow .destiny-time-span {
		font-size: 32px;
	}

	.destiny-timerow .destiny-time-titlespan {
		font-size: 12px;
		padding-bottom: 10px;
		border-bottom: 1px solid white;
		margin-bottom: 10px;
	}

	.kick-hitchhiker {
		opacity: 0;
		margin-right: 10px;
		transition: 0.1s ease-in opacity;
	}
	.spinner-class {
		width: 400px;
		height: 200px;
		margin: 100px auto 0;
		background: white;
	}
`;
