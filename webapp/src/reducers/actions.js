import fetch from 'isomorphic-fetch';

export const REQUEST_FUTURE_EVENTS = 'REQUEST_FUTURE_EVENTS';
export const RECEIVE_FUTURE_EVENTS = 'RECEIVE_FUTURE_EVENTS';
export const TOGGLE_AVAILABILITY   = 'TOGGLE_AVAILABILITY';
export const RESET = 'RESET';

function requestFutureEvents() {
    return {
        type: REQUEST_FUTURE_EVENTS
    };
}

function receiveFutureEvents(items) {
    return {
        type: RECEIVE_FUTURE_EVENTS,
        items: items,
        receivedAt: Date.now()
    };
}

export function fetchFutureEvents() {
    return function (dispatch) {
        dispatch(requestFutureEvents());

        return fetch('/api/events/future')
            .then(response => response.json())
            .then(json => dispatch(receiveFutureEvents(json)));
    };
}

export function resetEvents() {
    return {
        type: RESET
    };
}

export function toggleAvailability(eventId) {
    return {
        type: TOGGLE_AVAILABILITY,
        eventId: eventId
    };
}
