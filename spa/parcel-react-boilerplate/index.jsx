import 'babel-polyfill';
import React from 'react';
import ReactDOM from 'react-dom';
//import 'reboot.css';
import './css/css.css';
import './css/profile_hero.css';
import './css/datetime.component.css';
import App from './src/App';
import './i18n';
import { Provider } from 'react-redux';
import store from './src/state/store';

ReactDOM.render(
	<Provider store={store}>
		<App />
	</Provider>,
	document.getElementById('app')
);
