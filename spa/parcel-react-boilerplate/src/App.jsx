import React, { useState } from "react";
import styled from "styled-components";
import { connect } from "react-redux";
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Link,
  Redirect
} from "react-router-dom";
import Navbar2 from "./components/Navbar2";
import Home from "./pages/Home";
import { User } from "./pages/User";
import Trips from "./pages/Trips";
import Review from "./pages/Review";
import Error from "./pages/Error";

import { getProfile } from "./services/User.js";

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
          <Route path="/user" component={User} />
          <Route path="/trips" component={Trips} />
          <Route path="/review/:id" component={Review} />
          <Route path="/error/:code" component={Error} />
          <Route component={() => <Redirect to="/error/404" />} />
        </Switch>
      </React.Fragment>
    </Router>
  );
}

export default connect(state => ({ token: state.token, user: state.user }))(
  App
);
