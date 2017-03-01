import fetch from 'isomorphic-fetch';

export const REQUEST_FUTURE_EVENTS = 'REQUEST_FUTURE_EVENTS';
export const RECEIVE_FUTURE_EVENTS = 'RECEIVE_FUTURE_EVENTS';
export const TOGGLE_AVAILABILITY = 'TOGGLE_AVAILABILITY';
export const SYNC_START = 'SYNC_START';
export const SYNC_SUCCESS = 'SYNC_SUCCESS';
export const SYNC_ERROR = 'SYNC_ERROR';
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

        const {futureEvents, availabilities, auth} = getState();

        // delete availabilities
        const deletePromises = futureEvents.items
            .map((e) => e.eventId)
            .filter((e) => availabilities.indexOf(e) < 0)
            .map((e) => ({
                eventId: e,
                personId: auth.personId
            }))
            .map((e) =>
                new Promise(function (resolve, reject) {
                    fetch('/api/availabilities', {
                        method: 'DELETE',
                        body: JSON.stringify(e),
                        headers: {
                            'Content-Type': 'application/json',
                        },
                    }).then((response) => {
                        if (response.ok) {
                            resolve(Object.assign({'action': 'delete'}, e));
                        } else {
                            response.json().then((msg) => {
                                reject(msg);
                            });
                        }
                    }).catch((ex) => {
                        reject(ex);
                    });
                })
            );

        const createPromises = availabilities
            .map((e) => ({
                eventId: e,
                personId: auth.personId
            }))
            .map((e) =>
                new Promise(function (resolve, reject) {
                    fetch('/api/availabilities', {
                        method: 'PUT',
                        body: JSON.stringify(e),
                        headers: {
                            'Content-Type': 'application/json',
                        },
                    }).then((response) => {
                        if (response.ok) {
                            resolve(Object.assign({'action': 'create'}, e));
                        } else {
                            response.json().then((msg) => {
                                reject(msg);
                            });
                        }
                    }).catch((ex) => {
                        reject(ex);
                    });
                })
            );

        Promise.all([].concat(deletePromises, createPromises))
            .then((response) => {
                console.log('ok', response);
                dispatch({
                    type: SYNC_SUCCESS,
                });
            })
            .catch((error) => {
                console.log('error, retry in 10sec', error);
                setTimeout(() => dispatch({
                    type: SYNC_ERROR,
                }), 10 * 1000);
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
