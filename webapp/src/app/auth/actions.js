import fetch from 'isomorphic-fetch';
import {LOGIN_ERROR, LOGIN_EXPIRED, LOGIN_INVALID, LOGIN_SUCCESS, LOGOUT} from '../actions';

export function login(username, password) {
    return function (dispatch/* , getState */) {
        fetch('/api/sessions', {
            method: 'PUT',
            body: JSON.stringify({
                username,
                password
            }),
            headers: {
                'Content-Type': 'application/json',
            },
        }).then((response) => {
            response.json().then((msg) => {
                switch (msg.result) {
                    case 'INVALID_CREDENTIALS':
                        dispatch({
                            type: LOGIN_INVALID
                        });
                        break;
                    case 'SUCCESS':
                        loginSuccess(username, msg.personId, msg.token, msg.roles)(dispatch);
                        break;
                }
            });
        }).catch((/* ex */) => {
            dispatch({
                type: LOGIN_ERROR
            });
        });
    };
}

export function loginSuccess(username, personId, token, roles) {
    return function (dispatch) {
        dispatch({
            type: LOGIN_SUCCESS,
            username: username,
            personId: personId,
            token: token,
            roles: roles
        });
    };
}

export function loginExpired() {
    return function (dispatch) {
        dispatch({
            type: LOGIN_EXPIRED
        });
    };
}

export function logout() {
    return {
        type: LOGOUT
    };
}
