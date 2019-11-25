import React from 'react'
import styled from 'styled-components'
import { BrowserRouter as Router, Route, Link } from 'react-router-dom'
import fetchData from '../enhancers/fetchData'
import Trip from './User/Trip'
import TripPast from './User/TripPast'
import Login from './User/Login'
import Register from './User/Register'
import Profile from './User/Profile'
import Logout from './User/Logout'

export const routes = {
  trip: userId => `/user/${userId}/trip`,
  profile: userId => `/user/profile/${userId}`,
  login: `/login`,
  signIn: `/signin`,
  logOut: `/logout`
}

export const User = ({ match }) => {
  return (
    <React.Fragment>
      <Route path={match.url + '/:userId/trip'} exact component={Trip} />
      <Route path={match.url + '/trip_past'} component={TripPast} />
      <Route path={match.url + '/login'} exact component={Login} />
      <Route path={match.url + '/register'} exact component={Register} />
      <Route path={match.url + '/profile/:userId'} exact component={Profile} />
      <Route path={match.url + '/logout'} exact component={Logout} />
    </React.Fragment>
  )
}
