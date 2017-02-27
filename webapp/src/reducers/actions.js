import fetch from 'isomorphic-fetch';

export const REQUEST_FUTURE_EVENTS = 'REQUEST_FUTURE_EVENTS';
export const RECEIVE_FUTURE_EVENTS = 'RECEIVE_FUTURE_EVENTS';
export const TOGGLE_AVAILABILITY = 'TOGGLE_AVAILABILITY';
export const SYNCHRONIZE_STATE = 'SYNCHRONIZE_STATE';
export const INVALIDATE_STATE = 'INVALIDATE_STATE';

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
    return {
        type: SYNCHRONIZE_STATE
    };
}

export function toggleAvailability(eventId) {
    return function (dispatch) {
        dispatch({
            type: TOGGLE_AVAILABILITY,
            eventId: eventId
        });
        dispatch({
            type: INVALIDATE_STATE
        });
    };
}
