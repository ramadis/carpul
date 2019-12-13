import React, { useState } from "react";
import styled from "styled-components";
import { connect } from "react-redux";
import { NotificationContainer } from "react-notifications";
import { BrowserRouter, Route, Switch, Link, Redirect } from "react-router-dom";
import Navbar2 from "./components/Navbar2";
import Home from "./pages/Home";
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

import history, { initializeHistory } from "./utils/routes";

export const routes = {
  unreservedTrip: tripId => `/trips/${tripId}/unreserved`,
  reservedTrip: tripId => `/trips/${tripId}/reserved`,
  trip: tripId => `/trips/${tripId}`,
  profile: userId => `/user/${userId}`,
  login: `/login`,
  register: `/register`,
  logOut: `/logout`,
  addTrip: "/trips/add",
};

// initialize history object and routes storage
initializeHistory(window.location);

const RedirectingRoute = ({ to = "/login" }) => {
  return <Redirect to={to} />;
};

function App({ token, user, dispatch }) {
  const loadSession = async () => {
    if (token && !user) {
      const user = await getProfile();
      dispatch({ type: "USER_LOADED", user });
    }
  };

  const SecureRoute = user ? Route : RedirectingRoute;

  loadSession();
  return (
    <BrowserRouter basename="/webapp">
      <React.Fragment>
        <Navbar2 />
        <Switch>
          <Route exact path="/" component={Home} />
          <SecureRoute path="/review/:id" component={Review} />
          <Route path="/error/:code" component={Error} />
          <Route path="/search" component={Search} />
          <Route path="/login" exact component={Login} />
          <Route path="/logout" exact component={Logout} />
          <Route path="/register" exact component={Register} />
          <Route path="/user/trip_past" component={TripPast} />
          <Route path="/user/:userId/trip" exact component={Trip} />
          <Route path="/user/:userId" exact component={Profile} />
          <SecureRoute path="/trips/add" exact component={Add} />
          <Route path="/trips/:tripId" exact component={Single} />
          <SecureRoute
            path="/trips/:tripId/reserved"
            exact
            component={Reserved}
          />
          <SecureRoute
            path="/trips/:tripId/unreserved"
            exact
            component={Unreserved}
          />
          <Route component={() => <Redirect to="/error/404" />} />
        </Switch>
        <NotificationContainer leaveTimeout={1000} />
      </React.Fragment>
    </BrowserRouter>
  );
}

export default connect(state => ({ token: state.token, user: state.user }))(
  App
);
