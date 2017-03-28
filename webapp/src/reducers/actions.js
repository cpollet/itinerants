import fetch from 'isomorphic-fetch';
import {push} from 'react-router-redux';

export const REQUEST_FUTURE_EVENTS = 'REQUEST_FUTURE_EVENTS';
export const RECEIVE_FUTURE_EVENTS = 'RECEIVE_FUTURE_EVENTS';
export const FAIL_FUTURE_EVENTS = 'FAIL_FUTURE_EVENTS';
export const TOGGLE_AVAILABILITY = 'TOGGLE_AVAILABILITY';
export const SYNC_START = 'SYNC_START';
export const SYNC_SUCCESS = 'SYNC_SUCCESS';
export const SYNC_ERROR = 'SYNC_ERROR';
export const SYNC_FAILURE = 'SYNC_FAILURE';
export const INVALIDATE_STATE = 'INVALIDATE_STATE';
export const DECREASE_SYNC_TIMEOUT = 'DECREASE_SYNC_TIMEOUT';
export const LOGIN_SUCCESS = 'LOGIN_SUCCESS';
export const LOGIN_EXPIRED = 'LOGIN_EXPIRED';
export const LOGIN_INVALID = 'LOGIN_INVALID';
export const LOGIN_ERROR = 'LOGIN_ERROR';
export const LOGOUT = 'LOGOUT';

function authenticatedFetch(url, dispatch, state, options = {}) {
    function extractOr(object, key, defaultValue) {
        if (typeof object[key] !== 'undefined') {
            return object[key];
        }

        return defaultValue;
    }

    if (state.app.auth.token !== null) {
        options = Object.assign({}, options, {
            headers: Object.assign({}, extractOr(options, 'headers', {}), {
                'X-Auth-Token': state.app.auth.token
            })
        });
    }

    return fetch(url, options)
        .then(response => {
            if (response.status === 401) {
                dispatch({
                    type: LOGIN_EXPIRED
                });
                dispatch(push('/login'));
            }
            return response;
        });
}

export function fetchFutureEvents() {
    return function (dispatch, getState) {
        dispatch({
            type: REQUEST_FUTURE_EVENTS
        });

        return authenticatedFetch('/api/events/future', dispatch, getState())
            .then(response => {
                if (response.status === 200) {
                    return response.json();
                }
                return null;
            })
            .then(json => {
                if (json !== null) {
                    return dispatch({
                        type: RECEIVE_FUTURE_EVENTS,
                        items: json,
                        receivedAt: Date.now()
                    });
                } else {
                    return dispatch({
                        type: FAIL_FUTURE_EVENTS
                    });
                }
            });
    };
}

export function sync() {
    return function (dispatch, getState) {
        // console.log('sync...');
        dispatch({
            type: SYNC_START,
        });

        const {futureEvents, availabilities, auth, serverSync} = getState().app;

        Promise.all([]
            .concat(futureEvents.items
                .map((e) => e.eventId)
                .filter((eventId) => availabilities.indexOf(eventId) < 0)
                .map((eventId) => ({
                    action: 'DELETE',
                    data: {
                        eventId: eventId,
                        personId: auth.personId,
                    }
                })))
            .concat(availabilities
                .map((eventId) => ({
                    action: 'PUT',
                    data: {
                        eventId: eventId,
                        personId: auth.personId,
                    }
                })))
            .map((action) =>
                new Promise(function (resolve, reject) {
                    authenticatedFetch('/api/availabilities', getState(), {
                        method: action.action,
                        body: JSON.stringify(action.data),
                        headers: {
                            'Content-Type': 'application/json',
                        },
                    }).then((response) => {
                        if (response.ok) {
                            resolve(action);
                        } else {
                            response.json().then((msg) => reject(msg));
                        }
                    }).catch((ex) => reject(ex));
                })
            ))
            .then((/* response */) => {
                // console.log('ok', response);
                dispatch({
                    type: SYNC_SUCCESS,
                });
            })
            .catch((/* error */) => {
                // console.log('error', error);
                if (serverSync.retryCount === 0) {
                    // console.log('tried max amount of times');
                    dispatch({
                        type: SYNC_FAILURE,
                    });
                } else {
                    // console.log('retry in 5sec');
                    setTimeout(() => dispatch({
                        type: SYNC_ERROR,
                    }), 5 * 1000);
                }
            });
    };
}

export function decreaseSyncTimeout() {
    return {
        type: DECREASE_SYNC_TIMEOUT,
    };
}

export function toggleAvailability(eventId) {
    return function (dispatch) {
        dispatch({
            type: TOGGLE_AVAILABILITY,
            eventId: eventId,
        });
        dispatch({
            type: INVALIDATE_STATE,
        });
    };
}

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
                        dispatch({
                            type: LOGIN_SUCCESS,
                            username: username,
                            token: msg.token,
                            roles: msg.roles
                        });
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

export function logout() {
    return {
        type: LOGOUT
    };
}