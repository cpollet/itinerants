import fetch from 'isomorphic-fetch';
import {
    LOGIN_ERROR,
    LOGIN_EXPIRED,
    LOGIN_INVALID,
    LOGIN_SUCCESS,
    LOGOUT,
    RECEIVE_PEOPLE,
    RESET_LOGIN_FORM
} from '../actions';
import {authenticatedFetch, guardedFetch} from '../helpers';

export function login(username, password) {
    return function (dispatch/* , getState */) {
        dispatch({
            type: RESET_LOGIN_FORM
        });
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
                            type: LOGIN_INVALID,
                            username: username,
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

        if (roles.indexOf('admin') > -1) {
            guardedFetch(dispatch, authenticatedFetch('/api/people', dispatch, {app: {auth: {token: token}}}))
                .then(response => {
                    if (!response.ok) {
                        throw 'Received HTTP status code ' + response.status;
                    }
                    return response.json();
                })
                .then(json => {
                    dispatch({
                        type: RECEIVE_PEOPLE,
                        people: json,
                    });
                });
        }
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
