import {LOGIN_ERROR_EXPIRED, LOGIN_ERROR_INVALID, LOGIN_SUCCESS, LOGIN_RESET_FORM, RESET_PASSWORD_TOKEN_SENT} from '../actions';
import constants from './constants';

const initialState = {
    personId: null,
    token: null,
    username: null,
    roles: [],
    error: constants.notAuthenticated,
    resetPasswordTokenSent: false,
};

export default function (state = initialState, action) {
    switch (action.type) {
        case LOGIN_SUCCESS:
            return Object.assign({}, state, {
                personId: action.personId,
                username: action.username,
                token: action.token,
                roles: action.roles,
                error: null
            });
        case LOGIN_ERROR_INVALID:
            return Object.assign({}, state, {
                personId: null,
                username: action.username,
                token: null,
                roles: [],
                error: constants.invalidCredentials
            });
        case LOGIN_ERROR_EXPIRED:
            return Object.assign({}, state, {
                token: null,
                error: constants.sessionExpired
            });
        case RESET_PASSWORD_TOKEN_SENT:
            return Object.assign({}, state, {
                error: null,
                resetPasswordTokenSent: true,
            });
        case LOGIN_RESET_FORM:
            return Object.assign({}, state, initialState);
    }
    return state;
}