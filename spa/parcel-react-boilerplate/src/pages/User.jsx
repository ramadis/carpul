import React from 'react';
import styled from 'styled-components';
import { BrowserRouter as Router, Route, Link } from 'react-router-dom';
import fetchData from '../enhancers/fetchData';
import Trip from './User/Trip';
import TripPast from './User/TripPast';
import Login from './User/Login';
import Register from './User/Register';
import Profile from './User/Profile';

export const User = ({ match }) => {
	return (
		<React.Fragment>
			<Route exact path={match.url + '/trip'} component={Trip} />
			<Route path="/user/trip_past" component={TripPast} />
			<Route exact path="/user/login" component={Login} />
			<Route exact path="/user/register" component={Register} />
			<Route exact path="/user/profile/:id" component={Profile} />
		</React.Fragment>
	);
};
