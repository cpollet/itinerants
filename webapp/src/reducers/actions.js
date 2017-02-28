import fetch from 'isomorphic-fetch';

export const REQUEST_FUTURE_EVENTS = 'REQUEST_FUTURE_EVENTS';
export const RECEIVE_FUTURE_EVENTS = 'RECEIVE_FUTURE_EVENTS';
export const TOGGLE_AVAILABILITY = 'TOGGLE_AVAILABILITY';
export const SYNCHRONIZE_STATE = 'SYNCHRONIZE_STATE';
export const SYNCHRONIZED_STATE_SUCCESS = 'SYNCHRONIZED_STATE_SUCCESS';
export const SYNCHRONIZED_STATE_ERROR = 'SYNCHRONIZED_STATE_ERROR';
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

export function saveEvents() {
    return function (dispatch) {
        console.log('sync...');
        dispatch({
            type: SYNCHRONIZE_STATE
        });

        setTimeout(()=> {
            if (Math.random() > 0.5) {
                console.log('sync done');
                dispatch({
                    type: SYNCHRONIZED_STATE_SUCCESS,
                });
            } else {
                console.log('sync error');
                dispatch({
                    type: SYNCHRONIZED_STATE_ERROR,
                });
            }
        }, 1000);
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
