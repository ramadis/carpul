import React, { useState } from "react";
import styled from "styled-components";
import { connect } from "react-redux";
import { NotificationContainer } from "react-notifications";
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Link,
  Redirect,
} from "react-router-dom";
import Navbar2 from "./components/Navbar2";
import Home from "./pages/Home";
// import { Trips } from "./pages/Trips";
import Review from "./pages/Review";
import Error from "./pages/Error";
import Search from "./pages/Search";
import Trip from "./pages/User/Trip";
import TripPast from "./pages/User/TripPast";
import Login from "./pages/User/Login";
import Register from "./pages/User/Register";
import Profile from "./pages/User/Profile";
import Logout from "./pages/User/Logout";
import Add from "./pages/Trips/Add";
import Single from "./pages/Trips/Single";
import Unreserved from "./pages/Trips/Unreserved";
import Reserved from "./pages/Trips/Reserved";

import "react-notifications/lib/notifications.css";

import { getProfile } from "./services/User.js";

export const routes = {
  reservedTrip: tripId => `/trips/${tripId}/reserved`,
  trip: userId => `/user/${userId}/trip`,
  profile: userId => `/user/${userId}`,
  login: `/login`,
  register: `/register`,
  logOut: `/logout`,
};

function App({ token, user, dispatch }) {
  const loadSession = async () => {
    if (token && !user) {
      const user = await getProfile();
      dispatch({ type: "USER_LOADED", user });
    }
  };

  loadSession();
  return (
    <Router>
      <React.Fragment>
        <Navbar2 />
        <Switch>
          <Route exact path="/" component={Home} />
          {/* <Route path="/trips" component={Trips} /> */}
          <Route path="/review/:id" component={Review} />
          <Route path="/error/:code" component={Error} />
          <Route path="/search" component={Search} />
          <Route path="/login" exact component={Login} />
          <Route path="/logout" exact component={Logout} />
          <Route path="/register" exact component={Register} />
          <Route path="/user/trip_past" component={TripPast} />
          <Route path="/user/:userId/trip" exact component={Trip} />
          <Route path="/user/:userId" exact component={Profile} />
          <Route path="/trips/add" exact component={Add} />
          <Route path="/trips/:id" exact component={Single} />
          <Route path="/trips/:tripId/reserved" exact component={Reserved} />
          <Route
            path="/trips/:tripId/unreserved"
            exact
            component={Unreserved}
          />
          <Route component={() => <Redirect to="/error/404" />} />
        </Switch>
        <NotificationContainer />
      </React.Fragment>
    </Router>
  );
}

export default connect(state => ({ token: state.token, user: state.user }))(
  App
);
