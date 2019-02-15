import { combineReducers } from 'redux';

const initialState = {
	token: null,
	user: null,
	reservations: null
};

export const isLoggedIn = state => !!state.token;

const user = (state = initialState, action) => {
	console.log('GOT:', action);
	switch (action.type) {
		case 'LOGIN': {
			return { ...state, token: action.token };
		}
		case 'USER_LOADED': {
			return { ...state, user: action.user };
		}
		case 'RESERVATIONS_LOADED': {
			return { ...state, reservations: action.reservations };
		}
		default:
			return state;
	}
};

export default user;
