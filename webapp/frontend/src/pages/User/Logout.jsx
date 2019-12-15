import React, { useEffect } from 'react'
import { Redirect } from 'react-router-dom'
import { connect } from 'react-redux'

const Logout = ({ dispatch }) => {
  useEffect(() => {
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    dispatch({ type: 'LOGOUT' })
  }, [])

  return <Redirect to='/' />
}

export default connect()(Logout)
