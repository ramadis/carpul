import React from "react";
import styled from "styled-components";
import { BrowserRouter as Router, Route, Link, Switch } from "react-router-dom";
import fetchData from "../enhancers/fetchData";
import Add from "./Trips/Add";
import Single from "./Trips/Single";

export const Trips = ({ match }) => {
  return (
    <React.Fragment>
      <Switch>
        <Route path={match.url + "/add"} exact component={Add} />
        <Route path={match.url + "/:id"} component={Single} />
      </Switch>
    </React.Fragment>
  );
};
