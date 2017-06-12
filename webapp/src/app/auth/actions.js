import fetch from 'isomorphic-fetch';
import {
    LOGIN_ERROR_EXPIRED,
    LOGIN_ERROR_INVALID,
    LOGIN_RESET_FORM,
    LOGIN_SUCCESS,
    LOGOUT,
    PEOPLE_FETCH_SUCCESS
} from '../actions';
import {authenticatedFetch, guardedFetch} from '../helpers';

export function login(username, password) {
    return function (dispatch/* , getState */) {
        dispatch({
            type: LOGIN_RESET_FORM
        });
        guardedFetch(dispatch, fetch('/api/sessions', {
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
                            type: LOGIN_ERROR_INVALID,
                            username: username,
                        });
                        break;
                    case 'SUCCESS':
                        loginSuccess(username, msg.personId, msg.token, msg.roles)(dispatch);
                        break;
                }
            });
        }));
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
            guardedFetch(dispatch, authenticatedFetch('/api/people', dispatch, {app: {auth: {token: token}}})
                .then(response => {
                    console.log('do shit');
                    return response.json();
                })
                .then(json => {
                    dispatch({
                        type: PEOPLE_FETCH_SUCCESS,
                        people: json,
                    });
                }));
        }
    };
}

export function loginExpired() {
    return function (dispatch) {
        dispatch({
            type: LOGIN_ERROR_EXPIRED
        });
    };
}

export function logout() {
    return {
        type: LOGOUT
    };
}
