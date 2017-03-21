import fetch from 'isomorphic-fetch';

export const REQUEST_FUTURE_EVENTS = 'REQUEST_FUTURE_EVENTS';
export const RECEIVE_FUTURE_EVENTS = 'RECEIVE_FUTURE_EVENTS';
export const TOGGLE_AVAILABILITY = 'TOGGLE_AVAILABILITY';
export const SYNC_START = 'SYNC_START';
export const SYNC_SUCCESS = 'SYNC_SUCCESS';
export const SYNC_ERROR = 'SYNC_ERROR';
export const SYNC_FAILURE = 'SYNC_FAILURE';
export const INVALIDATE_STATE = 'INVALIDATE_STATE';
export const DECREASE_SYNC_TIMEOUT = 'DECREASE_SYNC_TIMEOUT';

export function fetchFutureEvents() {
    return function (dispatch) {
        dispatch({
            type: REQUEST_FUTURE_EVENTS
        });

        return fetch('/api/events/future')
            .then(response => response.json())
            .then(json => dispatch({
                type: RECEIVE_FUTURE_EVENTS,
                items: json,
                receivedAt: Date.now()
            }));
    };
}

export function sync() {
    return function (dispatch, getState) {
        console.log('sync...');
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
                    fetch('/api/availabilities', {
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
            .then((response) => {
                console.log('ok', response);
                dispatch({
                    type: SYNC_SUCCESS,
                });
            })
            .catch((error) => {
                console.log('error', error);
                if (serverSync.retryCount === 0) {
                    console.log('tried max amount of times');
                    dispatch({
                        type: SYNC_FAILURE,
                    });
                } else {
                    console.log('retry in 5sec');
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
