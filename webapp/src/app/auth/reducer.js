import {LOGIN_EXPIRED, LOGIN_INVALID, LOGIN_SUCCESS} from '../actions';

import constants from './constants';

const initialState = {
    personId: null,
    token: null,
    username: null,
    roles: [],
    error: constants.notAuthenticated,
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
        case LOGIN_INVALID:
            return Object.assign({}, state, {
                personId: null,
                username: null,
                token: null,
                roles: [],
                error: constants.invalidCredentials
            });
        case LOGIN_EXPIRED:
            return Object.assign({}, state, {
                token: null,
                error: constants.sessionExpired
            });
    }
    return state;
}