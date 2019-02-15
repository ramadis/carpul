import React, { useState } from 'react';
import styled from 'styled-components';
import {
	BrowserRouter as Router,
	Route,
	Switch,
	Link,
	Redirect
} from 'react-router-dom';
import Navbar2 from './components/Navbar2';
import Home from './pages/Home';
import { User } from './pages/User';
import Trips from './pages/Trips';
import Error from './pages/Error';

function App() {
	return (
		<Router>
			<React.Fragment>
				<Navbar2 />
				<Switch>
					<Route exact path="/" component={Home} />
					<Route path="/user" component={User} />
					<Route path="/trips" component={Trips} />
					<Route path="/error/:code" component={Error} />
					<Route component={() => <Redirect to="/error/404" />} />
				</Switch>
			</React.Fragment>
		</Router>
	);
}

export default App;
