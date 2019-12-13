import { combineReducers } from 'redux'

const hidrateUser = () => {
  try {
    return JSON.parse(localStorage.getItem('user'))
  } catch (e) {
    return null
  }
}
const initialState = {
  token: localStorage.getItem('token'),
  user: hidrateUser(),
  reservations: null
}

export const isLoggedIn = state => !!state.token

const user = (state = initialState, action) => {
  console.log('GOT:', action)
  switch (action.type) {
    case 'LOGIN': {
      return { ...state, token: action.token }
    }
    case 'LOGOUT': {
      return { ...state, token: null, user: null, reservations: null }
    }
    case 'USER_LOADED': {
      return { ...state, user: action.user }
    }
    case 'RESERVATIONS_LOADED': {
      return { ...state, reservations: action.reservations }
    }
    default:
      return state
  }
}

export default user
